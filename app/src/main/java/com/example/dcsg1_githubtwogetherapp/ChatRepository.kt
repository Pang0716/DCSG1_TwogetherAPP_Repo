package com.example.dcsg1_githubtwogetherapp

import android.content.Context
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageRow(
    val id: Long? = null,
    @SerialName("sender_id") val senderId: String,
    @SerialName("sender_name") val senderName: String,
    @SerialName("receiver_id") val receiverId: String,
    @SerialName("receiver_name") val receiverName: String,
    @SerialName("vendor_name") val vendorName: String,
    val content: String,
    @SerialName("created_at") val createdAt: String? = null
)

fun parseTimestamp(raw: String?): Long {
    return try {
        raw?.let { java.time.Instant.parse(it).toEpochMilli() } ?: System.currentTimeMillis()
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
}

@Serializable
data class VendorAccountRow(
    @SerialName("vendor_name") val vendorName: String,
    @SerialName("vendor_user_id") val vendorUserId: String
)

data class ChatMessage(val content: String, val isMine: Boolean, val createdAt: Long)

data class ChatConversation(
    val otherPartyId: String,
    val otherPartyName: String,
    val vendorName: String,
    val lastMessage: String,
    val lastMessageTime: Long,
    val lastSenderIsMe: Boolean,
    val isUnread: Boolean
)

object ChatRepository {

    /** Looks up which real account represents a given vendor card, if any. */
    suspend fun resolveVendorAccount(vendorName: String): String? {
        return try {
            val specific = supabase.postgrest["vendor_accounts"]
                .select { filter { eq("vendor_name", vendorName) } }
                .decodeSingleOrNull<VendorAccountRow>()
                ?.vendorUserId

            specific ?: supabase.postgrest["vendor_accounts"]
                .select { filter { eq("vendor_name", "_default_vendor") } }
                .decodeSingleOrNull<VendorAccountRow>()
                ?.vendorUserId
        } catch (e: Exception) {
            null
        }
    }

    suspend fun sendMessage(
        context: Context,
        myId: String,
        myName: String,
        otherPartyId: String,
        otherPartyName: String,
        vendorName: String,
        content: String
    ) {
        try {
            supabase.postgrest["chat_messages"].insert(
                ChatMessageRow(
                    senderId = myId, senderName = myName,
                    receiverId = otherPartyId, receiverName = otherPartyName,
                    vendorName = vendorName, content = content
                )
            )
        } catch (e: Exception) { /* offline — Room copy below still saves */ }

        AppDatabase.getInstance(context).chatDao().insertMessage(
            ChatEntity(0, myId, otherPartyId, otherPartyName, vendorName, true, content, System.currentTimeMillis())
        )
    }

    suspend fun loadMessages(context: Context, myId: String, otherPartyId: String, vendorName: String): List<ChatMessage> {
        return try {
            val remote = supabase.postgrest["chat_messages"]
                .select {
                    filter {
                        eq("vendor_name", vendorName)
                        or {
                            and { eq("sender_id", myId); eq("receiver_id", otherPartyId) }
                            and { eq("sender_id", otherPartyId); eq("receiver_id", myId) }
                        }
                    }
                    order("created_at", io.github.jan.supabase.postgrest.query.Order.ASCENDING)
                }
                .decodeList<ChatMessageRow>()

            val dao = AppDatabase.getInstance(context).chatDao()
            remote.forEach { row ->
                val isMine = row.senderId == myId
                dao.insertMessage(
                    ChatEntity(
                        0, myId, otherPartyId,
                        if (isMine) row.receiverName else row.senderName,
                        row.vendorName, isMine, row.content, parseTimestamp(row.createdAt)
                    )
                )
            }
            remote.map { ChatMessage(it.content, it.senderId == myId, parseTimestamp(it.createdAt)) }
        } catch (e: Exception) {
            AppDatabase.getInstance(context).chatDao().getMessages(myId, otherPartyId, vendorName)
                .map { ChatMessage(it.content, it.isMine, it.createdAt) }
        }
    }

    suspend fun loadConversations(context: Context, myId: String): List<ChatConversation> {
        val rows: List<ChatEntity> = try {
            val remote = supabase.postgrest["chat_messages"]
                .select { filter { or { eq("sender_id", myId); eq("receiver_id", myId) } } }
                .decodeList<ChatMessageRow>()

            val dao = AppDatabase.getInstance(context).chatDao()
            val entities = remote.map { row ->
                val isMine = row.senderId == myId
                val otherId = if (isMine) row.receiverId else row.senderId
                val otherName = if (isMine) row.receiverName else row.senderName
                ChatEntity(0, myId, otherId, otherName, row.vendorName, isMine, row.content, parseTimestamp(row.createdAt))
            }
            entities.forEach { dao.insertMessage(it) }
            entities
        } catch (e: Exception) {
            AppDatabase.getInstance(context).chatDao().getAllMessagesForUser(myId)
        }

        val readDao = AppDatabase.getInstance(context).chatReadDao()
        return rows
            .groupBy { it.otherPartyId to it.vendorName }
            .map { (_, msgs) ->
                val last = msgs.maxByOrNull { it.createdAt } ?: msgs.last()
                val lastRead = readDao.getLastReadAt(myId, last.otherPartyId, last.vendorName) ?: 0L
                val unread = !last.isMine && last.createdAt > lastRead
                ChatConversation(last.otherPartyId, last.otherPartyName, last.vendorName, last.content, last.createdAt, last.isMine, unread)
            }
            .sortedByDescending { it.lastMessageTime }
    }

    suspend fun subscribeToMessages(myId: String, otherPartyId: String, vendorName: String) =
        supabase.realtime.channel("chat_$myId").also { it.subscribe() }
            .postgresChangeFlow<PostgresAction.Insert>(schema = "public") {
                table = "chat_messages"
            }
            .map { it.decodeRecord<ChatMessageRow>() }
            .filter {
                it.vendorName == vendorName &&
                        ((it.senderId == myId && it.receiverId == otherPartyId) ||
                                (it.senderId == otherPartyId && it.receiverId == myId))
            }

    suspend fun markConversationRead(context: Context, myId: String, otherPartyId: String, vendorName: String) {
        AppDatabase.getInstance(context).chatReadDao()
            .markRead(ChatReadEntity(myId, otherPartyId, vendorName, System.currentTimeMillis()))
    }

    suspend fun hasUnreadMessages(context: Context, myId: String): Boolean {
        return loadConversations(context, myId).any { it.isUnread }
    }

    suspend fun subscribeToAllIncoming(myId: String) =
        supabase.realtime.channel("chat_incoming_$myId").also { it.subscribe() }
            .postgresChangeFlow<PostgresAction.Insert>(schema = "public") {
                table = "chat_messages"
            }
            .map { it.decodeRecord<ChatMessageRow>() }
            .filter { it.receiverId == myId }
}