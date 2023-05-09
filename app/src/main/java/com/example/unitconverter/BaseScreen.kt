package com.example.unitconverter

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    converterViewModel: ConverterViewModel = viewModel()
) {
    val list = converterViewModel.getConversions()
    Column(modifier = modifier.padding(30.dp)) {

        TopScreen(list)
        Spacer(modifier = modifier.height(20.dp))
//        HistoryScreen()

    }

}

@Composable
fun HistoryScreen() {
}

@Composable
fun TopScreen(list: List<Conversion>) {
    ConversionMenu(list){

        Log.e("TOP SCREEN", "TopScreen: $it")
    }

}

