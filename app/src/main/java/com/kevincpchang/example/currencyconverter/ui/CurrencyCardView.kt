package com.kevincpchang.example.currencyconverter.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.kevincpchang.example.currencyconverter.R
import com.kevincpchang.example.currencyconverter.data.CurrencyInfo
import java.text.DecimalFormat

@SuppressLint("ViewConstructor")
class CurrencyCardView @JvmOverloads constructor(
    context: Context,
    val data: CurrencyInfo,
    private val selectRate: Double = 1.0,
    private val targetPrice: Double = 1.0,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        initView(context)
    }

    private var source: TextView? = null
    private var currency: TextView? = null

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.cardview_currency, this)
        source = view.findViewById(R.id.source)
        source?.text = data.source
        currency = view.findViewById(R.id.currency)
        val dec = DecimalFormat("#,###.##")
        currency?.text = "$ ${dec.format(calculatePrice())}"
    }

    private fun calculatePrice(): Double {
        return data.rate * targetPrice / selectRate
    }
}