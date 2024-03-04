package com.MaiN.main_android.View.Reserv.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.MaiN.main_android.View.Reserv.data.BottomSheetData
import com.MaiN.main_android.View.Reserv.state.CellUiState

@Composable
fun CellColumn(
    cells: List<CellUiState> = listOf(),
    color: Color,
    onCellClick: (BottomSheetData) -> Unit,
) {
    cells
        .sortedBy { it.startHour }
        .forEach {
            Cell(
                cellUiState = CellUiState(
                    startHour = it.startHour,
                    startMin = it.startMin,
                    endHour = it.endHour,
                    endMin = it.endMin,
                    summary = it.summary,
                    koreaStyleTime = it.koreaStyleTime,
                    eventId = it.eventId
                ),
                color = color,
                onClick = onCellClick
            )
        }
}


@Preview(name = "CellColumn")
@Composable
private fun PreviewCellColumn() {
    val exampleCellUiState = listOf(
        CellUiState(
            startHour = 0,
            startMin = 0,
            endHour = 1,
            endMin = 0,
        ),
        CellUiState(
            startHour = 2,
            startMin = 0,
            endHour = 4,
            endMin = 0,
        ),
        CellUiState(
            startHour = 5,
            startMin = 30,
            endHour = 7,
            endMin = 30,
        ),
        CellUiState(
            startHour = 8,
            startMin = 30,
            endHour = 10,
            endMin = 30,
        ),
        CellUiState(
            startHour = 13,
            startMin = 21,
            endHour = 14,
            endMin = 45,
        ),
        CellUiState(
            startHour = 20,
            startMin = 30,
            endHour = 23,
            endMin = 0,
        ),
    )

    CellColumn(
        cells = exampleCellUiState,
        color = Color.Red,
        onCellClick = { }
    )
}
