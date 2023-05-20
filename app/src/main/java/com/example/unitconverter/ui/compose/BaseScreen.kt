package com.example.unitconverter.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconverter.data.Conversion
import com.example.unitconverter.ConverterViewModel
import com.example.unitconverter.ConverterViewModelFactory
import com.example.unitconverter.data.ConversionResult
import com.example.unitconverter.ui.compose.history.HistoryList
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun BaseScreen(
    factory: ConverterViewModelFactory,
    modifier: Modifier = Modifier,
    converterViewModel: ConverterViewModel = viewModel(factory = factory)
) {
    val list = converterViewModel.getConversions()
    val historyList = converterViewModel.resultList.collectAsState(initial = emptyList())
    val configuration = LocalConfiguration.current
    var isLandscape by remember {
        mutableStateOf(false)
    }

    when(configuration.orientation){
        Configuration.ORIENTATION_LANDSCAPE ->{

            isLandscape = true
            Row(modifier = modifier
                .padding(30.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TopScreen(list,
                    converterViewModel.selectedConversion,
                    converterViewModel.inputText,
                    converterViewModel.typedValue,
                    isLandscape
                ){msg1,msg2->
                    converterViewModel.addResult(msg1, msg2)
                }
                Spacer(modifier = modifier.width(10.dp))
                HistoryScreen(historyList,{
                    converterViewModel.removeResult(it)
                },{
                    converterViewModel.clearAll()
                })

            }

        }else ->{
        isLandscape = false
        Column(modifier = modifier.padding(30.dp)) {
            TopScreen(list,
                converterViewModel.selectedConversion,
                converterViewModel.inputText,
                converterViewModel.typedValue,
                isLandscape
            ){msg1,msg2->
                converterViewModel.addResult(msg1, msg2)
            }
            Spacer(modifier = modifier.height(20.dp))
            HistoryScreen(historyList,{
                converterViewModel.removeResult(it)
            },{
                converterViewModel.clearAll()
            })

        }
        }
    }


}

@Composable
fun HistoryScreen(
    list : State<List<ConversionResult>>,
    onCloseTask:(ConversionResult)->Unit,
    onClearAllTask : ()-> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        if ((list.value.isNotEmpty())) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "History", color = Color.Gray)
                OutlinedButton(onClick = { onClearAllTask() }) {
                    Text(text = "Clear All", color = Color.Gray)
                }

            }
        }
        HistoryList(list = list, onCloseTask ={
            onCloseTask(it)
        } )
    }


}

@Composable
fun TopScreen(
    list: List<Conversion>,
    selectedConversion : MutableState<Conversion?>,
    inputText:MutableState<String>,
    typedValue :MutableState<String>,
    isLandscape : Boolean,
    save:(String,String) -> Unit
) {
    var toSave by remember { mutableStateOf(false) }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())){
        ConversionMenu(list, isLandscape) {
            selectedConversion.value = it
            typedValue.value = "0.0"
        }
        selectedConversion.value?.let {
            InputBlock(conversion = it, inputText = inputText, isLandscape) { input ->
                typedValue.value = input
                toSave = true
            }
        }
        if (typedValue.value != "0.0") {
            val input = typedValue.value.toDouble()
            val multiply = selectedConversion.value!!.multiplyBy
            val result = input * multiply

            val df = DecimalFormat("#.####")
            df.roundingMode = RoundingMode.DOWN
            val roundedResult = df.format(result)
            val msg1 = "${typedValue.value} ${selectedConversion.value!!.convertFrom} is equal to"
            val msg2 = "$roundedResult ${selectedConversion.value!!.convertTo}"
            if (toSave) {
                save(msg1, msg2)
                toSave = false
            }
            ResultBlock(msg1 = msg1, msg2 = msg2)
        }
    }


}

