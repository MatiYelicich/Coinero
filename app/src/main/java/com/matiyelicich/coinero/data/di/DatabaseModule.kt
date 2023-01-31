package com.matiyelicich.coinero.data.di

import android.content.Context
import androidx.room.Room
import com.matiyelicich.coinero.data.CoineroDatabase
import com.matiyelicich.coinero.data.FinanceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideFinanceDao(coineroDatabase: CoineroDatabase): FinanceDao {
        return coineroDatabase.financeDao()
    }

    @Provides
    @Singleton
    fun provideCoineroDatabase(@ApplicationContext appContext: Context): CoineroDatabase {
        return Room.databaseBuilder(appContext, CoineroDatabase::class.java, "FinanceDatabase").build()
    }
}