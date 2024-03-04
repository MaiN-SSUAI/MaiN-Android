package com.MaiN.main_android.View.Reserv.state

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

data class CellUiState(
    val startHour: Int,
    val startMin: Int,
    val endHour: Int,
    val endMin: Int,
    val summary: String = "",
    val koreaStyleTime: String = "",
    val eventId: String = ""
) {
    private val oneHourHeight = 40.dp
    private val oneMinHeight = oneHourHeight / 60

    fun offset() = calculateOffset(startHour, startMin)

    fun height() = calculateOffset(
        endHour, endMin
    ) - offset()

    private fun calculateOffset(hour: Int, min: Int): Dp {
        return hour * oneHourHeight + min * oneMinHeight
    }

    fun toCommonTimeStyle(): String =
        "${this.startHour.fillTwoZero()}:${this.startMin.fillTwoZero()} ~ ${this.endHour.fillTwoZero()}:${this.endMin.fillTwoZero()}"

}

fun Int.fillTwoZero(): String {
    return this.toString().padStart(2, '0')
}
