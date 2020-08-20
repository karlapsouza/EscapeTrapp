package com.example.escapetrapp.ui.trip

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.extensions.hideKeyboard
import com.example.escapetrapp.models.RequestState
import com.example.escapetrapp.models.Trip
import java.util.*


class TripFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_travel

    private lateinit var etTravelName: EditText
    private lateinit var etTravelDestination: EditText
    private lateinit var etDateStartTravel: EditText
    private lateinit var etDateFinishTravel: EditText
    private lateinit var btCreateTravel: Button
    private val tripViewModel: TripViewModel by viewModels()
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
    }

    private fun setUpView(view: View) {
        etTravelName = view.findViewById(R.id.etTravelName)
        etTravelDestination = view.findViewById(R.id.etTravelDestination)
        etDateStartTravel = view.findViewById(R.id.etDateStartTravel)
        etDateFinishTravel = view.findViewById(R.id.etDateFinishTravel)
        btCreateTravel = view.findViewById(R.id.btCreateTravel)

        setUpListener()
        registerObserver()
    }

    private fun setUpListener(){
        btCreateTravel.setOnClickListener {
            hideKeyboard()
            val newTrip = Trip(
                etTravelName.text.toString(),
                etTravelDestination.text.toString(),
                etDateStartTravel.text.toString(),
                etDateFinishTravel.text.toString()
            )
            tripViewModel.addTrip(newTrip)
        }
        etDateStartTravel.setOnClickListener{
        }
        etDateFinishTravel.setOnClickListener{
//            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//                etDateFinishTravel.setText("" + dayOfMonth + "/" + month + "/" + year)
//            }, year, month, day)
//            dpd.show()
        }

    }

    private fun registerObserver() {
        this.tripViewModel.tripState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage("Viagem cadastrada com sucesso!")
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.trowable.message)
                }
                is RequestState.Loading -> showLoading("Realizando cadastro da viagem") }
        })
    }


}