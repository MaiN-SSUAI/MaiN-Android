package com.MaiN.main_android.View.Reserv.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.MaiN.main_android.View.Reserv.data.BottomSheetData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(
    showBottomSheet: Boolean,
    onDismissRequest: () -> Unit = {},
    bottomSheetData: BottomSheetData,
    onDeleteClick: (String) -> Unit = {},
) {
//    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismissRequest()
            },
            sheetState = sheetState
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("세부정보")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = bottomSheetData.summary)
                        Text(text = bottomSheetData.koreaStyleTime)
                        Text(text = bottomSheetData.commonTimeStyle)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column {
                        Button(
                            colors = buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Red,
                                disabledContentColor = Color.White,
                            ),
                            onClick = {
                                onDeleteClick(bottomSheetData.eventId)
                                onDismissRequest()
                            },
                        ) {
                            Text(text = "삭제")
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Spacer(modifier = Modifier.height(50.dp))
            }

//            // Sheet content
//            Button(onClick = {
//                scope.launch { sheetState.hide() }.invokeOnCompletion {
//                    if (!sheetState.isVisible) {
////                        showBottomSheet = false
//                        onDismissRequest()
//                    }
//                }
//            }
//            ) {
//
//            }
        }
    }
}