package com.example.escapetrapp.views

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.extensions.hideKeyboard
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Trip
import com.example.escapetrapp.viewsmodels.TripViewModel
import java.text.SimpleDateFormat
import java.util.*


class TripFragment : BaseAuthFragment(), DatePickerDialog.OnDateSetListener {
    override val layout = R.layout.fragment_travel

    private lateinit var etTravelName: EditText
    private lateinit var etTravelDestination: EditText
    private lateinit var etDateStartTravel: EditText
    private lateinit var etDateFinishTravel: EditText
    private lateinit var btCreateTravel: Button
    private val mDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val tripViewModel: TripViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
    }

    private fun setUpView(view: View) {
        val context = view.getContext()

        etTravelName = view.findViewById(R.id.etTravelName)
        etTravelDestination = view.findViewById(R.id.etTravelDestination)
        etDateStartTravel = view.findViewById(R.id.etDateStartTravel)
        etDateFinishTravel = view.findViewById(R.id.etDateFinishTravel)
        btCreateTravel = view.findViewById(R.id.btCreateTravel)

        setUpListener(context)
        registerObserver()
    }

    private fun setUpListener(context: Context){
        btCreateTravel.setOnClickListener {
            hideKeyboard()
            val newTrip = Trip(0,
                etTravelName.text.toString(),
                etTravelDestination.text.toString(),
                etDateStartTravel.text.toString(),
                etDateFinishTravel.text.toString()
            )
            tripViewModel.addTrip(newTrip)
        }
        etDateStartTravel.setOnClickListener{
            hideKeyboard()
            showDatePicker(context)
        }
        etDateFinishTravel.setOnClickListener{
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

    private fun registerObserver() {
        this.tripViewModel.tripState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage("Viagem cadastrada com sucesso!")
                    NavHostFragment.findNavController(this).navigate(R.id.action_travelFragment_to_travelListFragment)

                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.trowable.message)
                }
                is RequestState.Loading -> showLoading("Realizando cadastro da viagem") }

        })
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year,month, dayOfMonth)
        val str = mDateFormat.format(calendar.time)
        if(etDateStartTravel.hasFocus()) {
            etDateStartTravel.setText(str)
        }else if(etDateFinishTravel.hasFocus()){
            etDateFinishTravel.setText(str)
        }
    }



}