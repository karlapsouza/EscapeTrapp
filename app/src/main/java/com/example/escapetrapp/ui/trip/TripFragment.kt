package com.example.escapetrapp.ui.trip

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.extensions.hideKeyboard
import com.example.escapetrapp.models.RequestState
import com.example.escapetrapp.models.Trip


class TripFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_travel

    private lateinit var etTravelName: EditText
    private lateinit var etTravelDestination: EditText
    private lateinit var etDateStartTravel: EditText
    private lateinit var etDateFinishTravel: EditText
    private lateinit var btCreateTravel: Button
    private val tripViewModel: TripViewModel by viewModels()

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
    }

    private fun setUpListener(){
        btCreateTravel.setOnClickListener {
            hideKeyboard()
            val newTrip = Trip(
                etTravelName.text.toString(),
                etTravelDestination.text.toString()
//                etDateStartTravel,
//                etDateFinishTravel
            )
            tripViewModel.travel(newTrip)
        }

    }

    private fun registerObserver() {
        this.tripViewModel.travelState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage("Viagem cadastrada com sucesso!")
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_travelFragment_to_travelListFragment)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.trowable.message)
                }
                is RequestState.Loading -> showLoading("Realizando cadastro da vaigem") }
        }) }

}