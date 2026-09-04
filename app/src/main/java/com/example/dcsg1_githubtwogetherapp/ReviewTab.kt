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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
        Text(stringResource(R.string.write_a_review), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
        Spacer(Modifier.height(4.dp))
        Text(stringResource(R.string.posting_as, reviewerName), fontSize = 12.sp, color = Color.Gray)
        Spacer(Modifier.height(10.dp))

        Text(stringResource(R.string.rating_label), fontSize = 13.sp, color = Color.Gray)
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
            label = { Text(stringResource(R.string.your_review_label)) },
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
            Text(stringResource(R.string.submit_review))
        }

        Spacer(Modifier.height(4.dp))
        HorizontalDivider(color = Color(0xFFE8DFD3))
    }
}