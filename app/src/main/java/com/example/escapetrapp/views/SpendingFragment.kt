package com.example.escapetrapp.views

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.extensions.hideKeyboard
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Spending
import com.example.escapetrapp.views.adapter.SpendingAdapter
import com.example.escapetrapp.views.listener.SpendingListener
import com.example.escapetrapp.viewsmodels.SpendingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import java.text.SimpleDateFormat
import java.util.*

class SpendingFragment : BaseAuthFragment(), DatePickerDialog.OnDateSetListener {

    override val layout = R.layout.fragment_spending
    private val spendingViewModel: SpendingViewModel by viewModels()
    private lateinit var mViewModel: SpendingViewModel
    private lateinit var mAdapter: SpendingAdapter
    private lateinit var mListener: SpendingListener
    private lateinit var btHome: BottomNavigationItemView
    private lateinit var btBack: ImageButton
    private lateinit var etSpending: EditText
    private lateinit var sCurrency: Spinner
    private lateinit var btAddSpending: Button
    private lateinit var tvValueTotal: TextView
    private lateinit var etSpendingDate: EditText
    private lateinit var etSpendingDescription: EditText
    private val mDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)

        mViewModel = ViewModelProvider(this).get(SpendingViewModel::class.java)
        //Obtendo a recycler
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_all_spending)
        //Definindo um layout, como recycler se comporta na tela
        recycler.layoutManager = LinearLayoutManager(context)

        mViewModel.load()

        mListener = object : SpendingListener {

            override fun onClick(id: Int) {
//                val intent = Intent(context, SpendingFragment::class.java)

                //para passar dados na intent
//                val bundle = Bundle()
//                bundle.putInt(SpendingConstants.SPENDINGID, id)
//                intent.putExtras(bundle)
//                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load()
            }

        }
        mAdapter = SpendingAdapter(mListener)
        recycler.adapter = mAdapter

        registerObserver()
    }

    private fun registerObserver() {
        mViewModel.spendingState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage(it.data)
                    mViewModel.load()
                    //Limpar os campos
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.trowable.message)
                }
                is RequestState.Loading -> showLoading("Aguarde um momento") }
        })

        mViewModel.spendingList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mAdapter.updateSpendings(it)
        })
    }

    private fun setUpView(view: View) {
        val context = view.getContext()
        btHome = view.findViewById(R.id.navigation_home)
        btBack = view.findViewById(R.id.ibBackHome)
        etSpending = view.findViewById(R.id.etSpending)
        sCurrency = view.findViewById(R.id.sCurrency)
        btAddSpending = view.findViewById(R.id.btAddSpending)
        tvValueTotal = view.findViewById(R.id.tvValueTotal)
        etSpendingDate = view.findViewById(R.id.etSpendingDate)
        etSpendingDescription = view.findViewById(R.id.etSpendingDescription)
        setUpListener(context)
    }

    private fun setUpListener(context: Context){
        btHome.setOnClickListener {
            findNavController().navigate(R.id.main_nav_graph)
        }
        btBack.setOnClickListener {
            findNavController().navigate(R.id.action_spendingFragment_to_homeFragment)
        }
        btAddSpending.setOnClickListener {
            hideKeyboard()
            val newSpending = Spending(0,
                etSpendingDescription.text.toString(),
                etSpending.text.toString().toDoubleOrNull(),
                etSpendingDate.text.toString(),
                sCurrency.selectedItemPosition
            )
            spendingViewModel.addSpending(newSpending)
        }
        etSpendingDate.setOnClickListener{
            hideKeyboard()
            showDatePicker(context)
        }
    }

    private fun showDatePicker(context: Context){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(context,this,  year, month, day).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year,month, dayOfMonth)
        val str = mDateFormat.format(calendar.time)
        if(etSpendingDate.hasFocus()) {
            hideKeyboard()
            etSpendingDate.setText(str)
        }
    }

    private fun observer(){
//        mViewModel.spendingList.observe(viewLifecycleOwner, Observer {
//            mAdapter.updateSpendings(it)
//        })
    }


}