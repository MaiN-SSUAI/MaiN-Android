package com.MaiN.main_android.View.Reserv.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Timeline(
    startTime: Int,
    endTime: Int,
) {
    Column(
        modifier = Modifier.background(color = Color.White)
    ) {
        for (i in startTime..endTime) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .padding(horizontal = 5.dp)
            ) {
                Text(
                    text = "${i.toString().padStart(2, '0')}:00",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(name = "Timeline")
@Composable
private fun PreviewTimeline() {
    Timeline(startTime = 0, endTime = 23)
}