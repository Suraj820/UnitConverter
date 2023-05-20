package com.example.unitconverter.data

import kotlinx.coroutines.flow.Flow

interface ConverterRepository {

    suspend fun insertResult(result: ConversionResult)
    suspend fun deleteResult(result: ConversionResult)
    suspend fun deleteAllResult()
    fun getSavedResults():Flow<List<ConversionResult>>
}