package com.kevincpchang.example.currencyconverter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.kevincpchang.example.currencyconverter.ui.CurrencyConverterFragmentActivity

class CurrencyConverterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CurrencyConverterFragmentActivity.newInstance())
                .commitNow()
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(this)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        val view = currentFocus
        if (view != null
            && (event?.action == MotionEvent.ACTION_UP || event?.action == MotionEvent.ACTION_MOVE)
            && view is EditText
            && !view.javaClass.name.startsWith("android.webkit")) {
            val record = IntArray(2)
            view.getLocationOnScreen(record)
            val x = event.rawX + view.left - record[0]
            val y = event.rawY + view.top - record[1]
            if (x < view.left || x > view.right || y < view.top || y > view.bottom) {
                hideKeyboard(this)
                view.clearFocus()
            }
        }

        return super.dispatchTouchEvent(event)
    }

    private fun hideKeyboard(activity: Activity) {
        if (activity.window != null) {
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }
}
