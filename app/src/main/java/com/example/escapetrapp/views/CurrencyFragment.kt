package com.example.escapetrapp.views

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.libcalculagasto.CalculateSpend


class CurrencyFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_currency

    private lateinit var etCurrencyValue: EditText
    private lateinit var sCurrencySpinner: Spinner
    private lateinit var tvConvertedCurrencyValue: TextView
    private lateinit var btConverterCurrency: Button
    private lateinit var tvCancelCurrency: TextView
    private lateinit var ibBackCurrency: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
    }

    private fun setUpView(view: View) {
        etCurrencyValue = view.findViewById(R.id.etCurrencyValue)
        sCurrencySpinner = view.findViewById(R.id.sCurrencySpinner)
        tvConvertedCurrencyValue = view.findViewById(R.id.tvConvertedCurrencyValue)
        btConverterCurrency = view.findViewById(R.id.btConverterCurrency)
        tvCancelCurrency = view.findViewById(R.id.tvCancelCurrency)
        ibBackCurrency = view.findViewById(R.id.ibBackCurrency)
        setUpListener()
    }

    private fun setUpListener() {
        btConverterCurrency.setOnClickListener {
            if(etCurrencyValue.text.isNotEmpty()) {
                val value = CalculateSpend().convertSpend(
                    0.0,
                    etCurrencyValue.text.toString().toDouble(),
                    sCurrencySpinner.selectedItemPosition
                )
                tvConvertedCurrencyValue.text = "R$ " + value.toString()
            }else{
                val value = CalculateSpend().convertSpend(
                    0.0,
                    1.0,
                    sCurrencySpinner.selectedItemPosition
                )
                tvConvertedCurrencyValue.text = "R$ " + value.toString()
            }
            if(sCurrencySpinner.selectedItemPosition == 0){
                tvConvertedCurrencyValue.text = "R$ " + etCurrencyValue.text
            }
        }
        tvCancelCurrency.setOnClickListener {
            findNavController().navigate(R.id.action_currencyFragment_to_homeFragment)
        }
        ibBackCurrency.setOnClickListener {
            findNavController().navigate(R.id.action_currencyFragment_to_homeFragment)
        }
    }


}
