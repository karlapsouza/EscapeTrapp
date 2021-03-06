package com.example.escapetrapp.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.extensions.hideKeyboard
import com.example.escapetrapp.services.constants.SpotConstants
import com.example.escapetrapp.services.constants.TripConstants
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Spot
import com.example.escapetrapp.viewsmodels.SpotViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class SpotFragment : BaseAuthFragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    override val layout = R.layout.fragment_spot

    private lateinit var etPlace: EditText
    private lateinit var etStartDateSpot: EditText
    private lateinit var etEndDateSpot: EditText
    private lateinit var etStartTimeSpot: EditText
    private lateinit var etEndTimeSpot: EditText
    private lateinit var etDescriptionSpot: EditText
    private lateinit var btAddSpot: Button
    private lateinit var ibBackSpotList: ImageButton
    private lateinit var tvCancelSpot: TextView
    private val mDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val mTimeFormat = SimpleDateFormat("HH:mm")
    private val spotViewModel: SpotViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerBackPressedAction()
        registerObserver()
    }

    private fun setUpView(view: View) {
        val context = view.getContext()

        etPlace = view.findViewById(R.id.etPlace)
        etStartDateSpot = view.findViewById(R.id.etStartDateSpot)
        etEndDateSpot = view.findViewById(R.id.etEndDateSpot)
        etStartTimeSpot = view.findViewById(R.id.etStartTimeSpot)
        etEndTimeSpot = view.findViewById(R.id.etEndTimeSpot)
        etDescriptionSpot = view.findViewById(R.id.etDescriptionSpot)
        btAddSpot = view.findViewById(R.id.btAddSpot)
        ibBackSpotList = view.findViewById(R.id.ibBackSpotList)
        tvCancelSpot = view.findViewById(R.id.tvCancelSpot)

        setUpListener(context)
        registerObserver()
    }

    private fun registerObserver() {
        this.spotViewModel.spotState.observe(viewLifecycleOwner, Observer{
            when(it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage("Atividade cadastrada/atualizada com sucesso!")
                    val bundle = Bundle()
                    bundle.putInt(TripConstants.TRIPID, id)
                    findNavController().navigate(R.id.action_spotFragment_to_spotListFragment, bundle)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.trowable.message)
                }
                is RequestState.Loading -> showLoading("Realizando cadastro da atividade") }

        })
    }

    private fun setUpListener(context: Context) {
        btAddSpot.setOnClickListener {
            hideKeyboard()
            val spotId = arguments?.getInt(SpotConstants.SPOTID)
            val tripId = arguments?.getInt(TripConstants.TRIPID)
            if(spotId == 0){
                val newSpot = Spot(0,
                    etPlace.text.toString(),
                    etStartDateSpot.text.toString(),
                    etEndDateSpot.text.toString(),
                    etStartTimeSpot.text.toString(),
                    etEndTimeSpot.text.toString(),
                    etDescriptionSpot.text.toString(),
                    tripId!!
                )
                spotViewModel.addSpot(newSpot)
            }else{
                val spot = Spot(spotId!!,
                    etPlace.text.toString(),
                    etStartDateSpot.text.toString(),
                    etEndDateSpot.text.toString(),
                    etStartTimeSpot.text.toString(),
                    etEndTimeSpot.text.toString(),
                    etDescriptionSpot.text.toString(),
                    tripId!!
                )
                spotViewModel.updateSpot(spot)
            }
        }
        etStartDateSpot.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                hideKeyboard()
                showDatePicker(context)
            }
        }
        etEndDateSpot.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                hideKeyboard()
                showDatePicker(context)
            }
        }
        etStartTimeSpot.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                hideKeyboard()
                showTimePicker(context)
            }
        }
        etEndTimeSpot.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                hideKeyboard()
                showTimePicker(context)
            }
        }
        ibBackSpotList.setOnClickListener {
            findNavController().navigate(R.id.action_spotFragment_to_spotListFragment)
        }
        tvCancelSpot.setOnClickListener {
            findNavController().navigate(R.id.action_spotFragment_to_spotListFragment)
        }
    }

    private fun showDatePicker(context: Context){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(context,this,  year, month, day).show()
    }

    private fun showTimePicker(context: Context){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(context,this,hour, minute,true).show()
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year,month, dayOfMonth)
        val str = mDateFormat.format(calendar.time)
        if(etStartDateSpot.hasFocus()) {
            hideKeyboard()
            etStartDateSpot.setText(str)
        }else if(etEndDateSpot.hasFocus()){
            hideKeyboard()
            etEndDateSpot.setText(str)
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        val hourStr = if (hourOfDay < 10) "0${hourOfDay}" else "${hourOfDay}"
        val minuteStr = if (minute < 10) "0${minute}" else "${minute}"
        calendar.set(hourStr.toInt(), minuteStr.toInt())
        val str = mTimeFormat.format(calendar.time)
        if(etStartTimeSpot.hasFocus()) {
            hideKeyboard()
            etStartTimeSpot.setText(str)
        }else if(etEndTimeSpot.hasFocus()){
            hideKeyboard()
            etEndTimeSpot.setText(str)
        }
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_spotFragment_to_spotListFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


}