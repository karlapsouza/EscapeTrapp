package com.example.escapetrapp.views

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.extensions.hideKeyboard
import com.example.escapetrapp.services.constants.TripConstants
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Trip
import com.example.escapetrapp.views.adapter.TripAdapter
import com.example.escapetrapp.viewsmodels.TripViewModel
import java.text.SimpleDateFormat
import java.util.*


class TripFragment: BaseAuthFragment(), DatePickerDialog.OnDateSetListener {
    override val layout = R.layout.fragment_trip

    private lateinit var etTravelName: EditText
    private lateinit var etTravelDestination: EditText
    private lateinit var etDateStartTravel: EditText
    private lateinit var etDateFinishTravel: EditText
    private lateinit var btCreateTravel: Button
    private lateinit var tvCancel: TextView
    private lateinit var ibBackTrip: ImageButton
    private lateinit var tvTripTitle: TextView
    private val mDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val tripViewModel: TripViewModel by viewModels()
    private lateinit var mViewModel: TripViewModel
    private lateinit var mAdapter: TripAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(TripViewModel::class.java)
        setUpView(view)
        registerBackPressedAction()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val tripId = arguments?.getInt(TripConstants.TRIPID)
        Toast.makeText(requireContext(), tripId.toString(), Toast.LENGTH_LONG).show()
        if(tripId != 0 && tripId != null){
            //carregando os dados
            mViewModel.load(tripId)
            loadData()
        }
    }

    private fun setUpView(view: View) {
        val context = view.getContext()

        etTravelName = view.findViewById(R.id.etTravelName)
        etTravelDestination = view.findViewById(R.id.etTravelDestination)
        etDateStartTravel = view.findViewById(R.id.etDateStartTravel)
        etDateFinishTravel = view.findViewById(R.id.etDateFinishTravel)
        btCreateTravel = view.findViewById(R.id.btCreateTravel)
        tvCancel = view.findViewById(R.id.tvCancel)
        ibBackTrip = view.findViewById(R.id.ibBackTrip)
        tvTripTitle = view.findViewById(R.id.tvTripTitle)

        setUpListener(context)
        registerObserver()
    }

    private fun setUpListener(context: Context){
        btCreateTravel.setOnClickListener {
            hideKeyboard()
            val tripId = arguments?.getInt(TripConstants.TRIPID)
            if(tripId == 0){
                val newTrip = Trip(0,
                    etTravelName.text.toString(),
                    etTravelDestination.text.toString(),
                    etDateStartTravel.text.toString(),
                    etDateFinishTravel.text.toString()
                )
                tripViewModel.addTrip(newTrip)
            }else{
                val trip = Trip(tripId!!,
                    etTravelName.text.toString(),
                    etTravelDestination.text.toString(),
                    etDateStartTravel.text.toString(),
                    etDateFinishTravel.text.toString()
                )
                tripViewModel.updateTrip(trip)
            }
        }
        etDateStartTravel.setOnClickListener{
            hideKeyboard()
            showDatePicker(context)
        }
        etDateFinishTravel.setOnClickListener{
            hideKeyboard()
            showDatePicker(context)
        }
        tvCancel.setOnClickListener {
            findNavController().navigate(R.id.action_travelFragment_to_travelListFragment)
        }
        ibBackTrip.setOnClickListener {
            findNavController().navigate(R.id.action_travelFragment_to_travelListFragment)
        }
    }

    private fun loadData(){
        this.tripViewModel.oneTrip.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            tvTripTitle.text = getString(R.string.trip_text_update)
            btCreateTravel.text = getString(R.string.button_edit_trip)
            etTravelName.setText(it?.name)
            etTravelDestination.setText(it?.destination)
            etDateStartTravel.setText(it?.initialDate)
            etDateFinishTravel.setText(it?.endDate)

        })
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
        if(etDateStartTravel.hasFocus()) {
            hideKeyboard()
            etDateStartTravel.setText(str)
        }else if(etDateFinishTravel.hasFocus()){
            hideKeyboard()
            etDateFinishTravel.setText(str)
        }
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
        this.tripViewModel.tripList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateTrips(it)
        })
    }


    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }




}