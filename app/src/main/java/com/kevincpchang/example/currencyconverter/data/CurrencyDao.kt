package com.kevincpchang.example.currencyconverter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {
    @Query("SELECT * from collect_table")
    suspend fun getAllList(): List<CurrencyInfo>

    @Query("SELECT * from collect_table WHERE source = :typeSource")
    suspend fun findRate(typeSource: String): CurrencyInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRate(rateData: CurrencyInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAllRate(rateList: List<CurrencyInfo>)
}