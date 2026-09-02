package com.example.dcsg1_githubtwogetherapp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A single review row. Same idea as ContactRow in Practical 5:
 * takes one piece of data, lays it out, and adds a divider at the bottom so it
 * lines up nicely when repeated inside a LazyColumn.
 */
@Composable
fun ReviewRow(review: Review, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().padding(vertical = 10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                review.reviewerName,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Row {
                // repeat() is basic Kotlin syntax, loops to draw the matching number of stars
                repeat(review.rating) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFF5A623),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            review.comment,
            fontSize = 13.sp,
            color = Color.Gray,
            lineHeight = 18.sp
        )
        Spacer(Modifier.height(10.dp))
        HorizontalDivider(color = Color(0xFFE8DFD3))
    }
}

/**
 * Lets the user fill in and submit a new review.
 * reviewerName is passed in from outside (the logged-in user's name) - the user can't edit it.
 * The rating/comment state belongs only to this form - same as Practical 6's AddItemScreen,
 * the form manages its own inputs internally and only hands the values up to the caller
 * on Submit (the onSubmit lambda is "the event going up"). The form clears itself afterward
 * so it's ready for the next entry.
 */
@Composable
fun AddReviewForm(
    reviewerName: String,
    onSubmit: (rating: Int, comment: String) -> Unit,
    submitError: String? = null,
    modifier: Modifier = Modifier
) {
    var comment by remember { mutableStateOf("") }
    var rating by remember { mutableIntStateOf(5) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text("Write a review", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
        Spacer(Modifier.height(4.dp))
        Text("Posting as $reviewerName", fontSize = 12.sp, color = Color.Gray)
        Spacer(Modifier.height(10.dp))

        Text("Rating", fontSize = 13.sp, color = Color.Gray)
        // Same exact pattern as selecting Priority in Practical 6's AddItemScreen:
        // each star is a Row using selectable + Role.RadioButton to make the whole row clickable,
        // the RadioButton itself has onClick = null, and the outer Row handles the click
        Row(verticalAlignment = Alignment.CenterVertically) {
            (1..5).forEach { star ->
                Row(
                    modifier = Modifier.selectable(
                        selected = (star == rating),
                        onClick = { rating = star },
                        role = Role.RadioButton
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = (star == rating), onClick = null)
                    Text("$star", fontSize = 13.sp)
                }
            }
        }
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Your review") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(10.dp))

        if (submitError != null) {
            Text(submitError, fontSize = 12.sp, color = Color(0xFFC0392B))
            Spacer(Modifier.height(6.dp))
        }

        Button(
            onClick = {
                onSubmit(rating, comment.trim())
                comment = ""
                rating = 5
            },
            enabled = comment.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit review")
        }

        Spacer(Modifier.height(4.dp))
        HorizontalDivider(color = Color(0xFFE8DFD3))
    }
}