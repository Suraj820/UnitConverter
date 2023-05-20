package com.example.unitconverter.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConverterDAO {

    @Insert
   suspend fun insertResult(result : ConversionResult)

   @Delete
   suspend fun deleteResult(result: ConversionResult)

   @Query("DELETE FROM result_table")
   suspend fun deleteAllResult()

   @Query("SELECT * FROM result_table")
   fun getSavedResults():Flow<List<ConversionResult>>
}