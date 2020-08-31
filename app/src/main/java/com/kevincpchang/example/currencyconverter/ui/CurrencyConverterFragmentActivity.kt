package com.kevincpchang.example.currencyconverter.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kevincpchang.example.currencyconverter.R
import com.kevincpchang.example.currencyconverter.data.CurrencyTransfer
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyConverterFragmentActivity : Fragment() {
    companion object {
        fun newInstance() =
            CurrencyConverterFragmentActivity()
    }

    private val mainViewModel: CurrencyViewModel by viewModel()
    private var inputPrice: EditText? = null
    private var currencyMenu: AppCompatSpinner? = null
    private var gridList: GridView? = null
    private var gridAdapter: CurrencyGridAdapter? = null
    private var scanHandler: Handler? = null
    private var emptyState: TextView? = null

    // Runnable task for run the data fetch every 30 minutes.
    private val scanTask = object : Runnable {
        override fun run() {
            mainViewModel.fetchData()
            scanHandler?.postDelayed(this, 1810000)
        }
    }

    override fun onResume() {
        super.onResume()
        scanHandler?.post(scanTask)
    }

    override fun onPause() {
        super.onPause()
        scanHandler?.removeCallbacks(scanTask)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scanHandler = Handler(Looper.getMainLooper())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_currency, container, false)
        inputPrice = root.findViewById(R.id.input_price)
        currencyMenu = root.findViewById(R.id.currency_menu)
        gridList = root.findViewById(R.id.grid_list)
        emptyState = root.findViewById(R.id.empty_state)
        inputPrice?.doOnTextChanged { text, _, _, _ ->
            val getString = text?.toString() ?: "1"
            val getNumber: Double = parseInputPrice(getString)
            mainViewModel.updatePrice(getNumber)
        }
        mainViewModel.readyData().observe(viewLifecycleOwner, ratesObserver)
        mainViewModel.getTargetPrice().observe(viewLifecycleOwner, inputPriceObserver)
        mainViewModel.getStandRate().observe(viewLifecycleOwner, selectCurrencyObserver)
        return root
    }

    private fun parseInputPrice(text: String): Double {
        val parseVal: Double =
            if (text.isEmpty()) 1.0
            else text.toDoubleOrNull() ?: 1.0
        return if (parseVal > 0.0) parseVal else 1.0
    }

    //Observer for update the input price to the grid adapter
    private val inputPriceObserver = Observer<Double> {
        gridAdapter?.updateTargetPrice(it)
    }
    //Observer for update the selected rate to the grid adapter
    private val selectCurrencyObserver = Observer<Double> {
        gridAdapter?.updateSelectedRate(it)
    }

    private val ratesObserver = Observer<CurrencyTransfer> {
        context?.let { itContext ->
            val adapter =
                ArrayAdapter(
                    itContext,
                    R.layout.support_simple_spinner_dropdown_item,
                    it.getFullNameKeys()
                )
            currencyMenu?.adapter = adapter
            currencyMenu?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
                    mainViewModel.updateRate(it.data[pos].rate)
                }
            }
            val dataSize = it.data.size
            emptyState?.text = if (dataSize > 0) "" else getText(R.string.search_msg)
            val selectIndex = currencyMenu?.selectedItemPosition ?: 0
            val selectRate = if (it.data.size > 0) it.data[selectIndex].rate else 0.0
            val targetPriceText = inputPrice?.toString() ?: "1"
            gridAdapter =
                CurrencyGridAdapter(itContext, it.data, parseInputPrice(targetPriceText), selectRate)
            gridList?.adapter = gridAdapter
        }
    }
}