package com.example.unitconverter.data

import kotlinx.coroutines.flow.Flow

class ConversionRepositoryImpl(private  val converterDAO: ConverterDAO) : ConverterRepository {
    override suspend fun insertResult(result: ConversionResult) {
         converterDAO.insertResult(result)
    }

    override suspend fun deleteResult(result: ConversionResult) {
        converterDAO.deleteResult(result)
    }

    override suspend fun deleteAllResult() {
        converterDAO.deleteAllResult()
    }

    override fun getSavedResults(): Flow<List<ConversionResult>> {
        return converterDAO.getSavedResults()
    }
}