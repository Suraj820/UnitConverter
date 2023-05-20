package com.example.unitconverter.ui.compose.history

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.example.unitconverter.data.ConversionResult

@Composable
fun HistoryList(
    list: State<List<ConversionResult>>,
    onCloseTask : (ConversionResult) -> Unit,
    modifier: Modifier = Modifier,
){
    LazyColumn{
        items(
            items = list.value,
            key = {item -> item.id }
        ){
            HistoryItem(msg1 = it.msg1, msg2 =it.msg2 ) { onCloseTask(it) }
        }
    }


}