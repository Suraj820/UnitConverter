package com.example.unitconverter.di

import android.app.Application
import androidx.room.Room
import com.example.unitconverter.data.ConversionDatabase
import com.example.unitconverter.data.ConversionRepositoryImpl
import com.example.unitconverter.data.ConverterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConverterDatabase(app:Application): ConversionDatabase{
        return Room.databaseBuilder(app,
            ConversionDatabase::class.java,"ConversionDataBase")
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterRepository(db : ConversionDatabase):ConverterRepository{
        return ConversionRepositoryImpl(db.converterDAO)
    }



}