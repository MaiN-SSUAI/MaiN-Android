package com.MaiN.main_android.View.Reserv.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.MaiN.main_android.View.Reserv.data.BottomSheetData
import com.MaiN.main_android.View.Reserv.state.CellUiState

@Composable
fun Cell(
    modifier: Modifier = Modifier,
    cellUiState: CellUiState,
    color: Color,
    onClick: (BottomSheetData) -> Unit,
) {
    Column(
        modifier = modifier
            .offset(
                y = cellUiState.offset()
            )
            .height(height = cellUiState.height())
            .fillMaxWidth()
            .background(color)
            .clickable {
                onClick(
                    BottomSheetData(
                        cellUiState.summary,
                        cellUiState.koreaStyleTime,
                        cellUiState.toCommonTimeStyle(),
                        cellUiState.eventId
                    )
                )
            }
    ) {
        Text(
            text = cellUiState.toCommonTimeStyle(),
            fontSize = 10.sp
        )
        Text(text = cellUiState.summary, fontSize = 10.sp)
    }
}


@Preview
@Composable
fun Cell2() {
    Cell(
        cellUiState = CellUiState(
            startHour = 0,
            startMin = 0,
            endHour = 2,
            endMin = 0,
            summary = "asdasdasd",
            koreaStyleTime = "asdasdasd"
        ),
        color = Color.Cyan,
        onClick = { }
    )
}
//
//@Preview
//@Composable
//fun Cell4() {
//    Cell(
//        onCellClick = {},
//        startTime = "03:00",
//        endTime = "04:00",
//        studentID = "20220000",
//        roomName = "세미나실1",
//        width = 90,
//        height = 150,
//    )
//}