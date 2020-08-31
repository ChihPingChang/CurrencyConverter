package com.kevincpchang.example.currencyconverter.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.kevincpchang.example.currencyconverter.data.CurrencyInfo

class CurrencyGridAdapter constructor(
    var context: Context,
    private var rateList: List<CurrencyInfo>,
    private var targetPrice: Double,
    private var selectedRate: Double
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rateItem = rateList[position]
        return CurrencyCardView(context, rateItem, selectedRate, targetPrice)
    }

    override fun getItem(position: Int): Any {
        return rateList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return rateList.size
    }

    fun updateSelectedRate(newRate: Double) {
        this.selectedRate = newRate
        notifyDataSetChanged()
    }

    fun updateTargetPrice(newPrice: Double) {
        this.targetPrice = newPrice
        notifyDataSetChanged()
    }
}