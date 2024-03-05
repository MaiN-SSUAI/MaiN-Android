package com.MaiN.main_android.View.Reserv.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DateItem(
    isSelected: Boolean,
    dayOfTheWeek: String,
    day: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(
                width = 40.dp,
                height = 60.dp
            )
            .background(
                color = if (isSelected) Color(0xC73DC2EC) else Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayOfTheWeek.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = day.toString().padStart(2, '0'),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun PreviewDateItem() {
    val isSelected = remember { true }
    DateItem(dayOfTheWeek = "일", day = 1, isSelected = isSelected) {}
}

@Preview
@Composable
fun PreviewDateItemByLazyRow() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val exampleList = listOf(
        Pair("일", 1),
        Pair("월", 2),
        Pair("화", 3),
        Pair("수", 4),
        Pair("목", 5),
        Pair("금", 6),
        Pair("토", 7),
        Pair("일", 8),
        Pair("월", 9),
        Pair("화", 10),
        Pair("수", 11),
        Pair("목", 12),
        Pair("금", 13),
        Pair("토", 14),
        Pair("일", 15),
        Pair("월", 16),
        Pair("화", 17),
        Pair("수", 18),
        Pair("목", 19),
        Pair("금", 20),
    )

    LazyRow(
        modifier = Modifier
            .background(Color.White),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        itemsIndexed(exampleList) { index, item ->
            DateItem(
                dayOfTheWeek = item.first,
                day = item.second,
                isSelected = selectedIndex == index
            ) {
                selectedIndex = index
            }
        }
    }
}