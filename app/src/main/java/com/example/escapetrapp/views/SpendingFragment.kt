import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.extensions.hideKeyboard
import com.example.escapetrapp.services.constants.SpendingConstants
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Spending
import com.example.escapetrapp.views.adapter.SpendingAdapter
import com.example.escapetrapp.viewsmodels.SpendingViewModel
import java.text.SimpleDateFormat
import java.util.*

class SpendingFragment : BaseAuthFragment(), DatePickerDialog.OnDateSetListener {

    override val layout = R.layout.fragment_spending
    private val spendingViewModel: SpendingViewModel by viewModels()
    private lateinit var mViewModel: SpendingViewModel
    private lateinit var mAdapter: SpendingAdapter
    private lateinit var ibBackSpending: ImageButton
    private lateinit var etSpending: EditText
    private lateinit var sCurrency: Spinner
    private lateinit var btAddSpending: Button
    private lateinit var etSpendingDate: EditText
    private lateinit var etSpendingDescription: EditText
    private lateinit var tvCancelSpending: TextView
    private lateinit var tvSpendingTitle: TextView
    private val mDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(SpendingViewModel::class.java)
        setUpView(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val spendingId = arguments?.getInt(SpendingConstants.SPENDINGID)
        if(spendingId != 0 && spendingId != null){
            //carregando os dados
            mViewModel.load(spendingId)
            loadData()
            registerObserver()
        }
    }

    private fun loadData(){
        this.spendingViewModel.oneSpending.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            tvSpendingTitle.text = getString(R.string.trip_text_update)
            btAddSpending.text = getString(R.string.button_edit_trip)
            etSpendingDescription.setText(it?.description)
            etSpending.setText(it?.value.toString())
            etSpendingDate.setText(it?.date)
            sCurrency.setSelection(it?.currency!!)
        })
    }

    private fun setUpView(view: View) {
        val context = view.getContext()
        ibBackSpending = view.findViewById(R.id.ibBackSpending)
        etSpending = view.findViewById(R.id.etSpending)
        sCurrency = view.findViewById(R.id.sCurrency)
        btAddSpending = view.findViewById(R.id.btAddSpending)
        etSpendingDate = view.findViewById(R.id.etSpendingDate)
        etSpendingDescription = view.findViewById(R.id.etSpendingDescription)
        tvCancelSpending = view.findViewById(R.id.tvCancelSpending)
        tvSpendingTitle = view.findViewById(R.id.tvSpendingTitle)
        setUpListener(context)
        registerObserver()
    }

    private fun setUpListener(context: Context) {
        ibBackSpending.setOnClickListener {
            findNavController().navigate(R.id.action_spendingFragment_to_spendingListFragment)
        }
        btAddSpending.setOnClickListener {
            hideKeyboard()
            val spendingId = arguments?.getInt(SpendingConstants.SPENDINGID)
            if(spendingId == 0) {
                val newSpending = Spending(
                    0,
                    etSpendingDescription.text.toString(),
                    etSpending.text.toString().toDoubleOrNull(),
                    etSpendingDate.text.toString(),
                    sCurrency.selectedItemPosition
                )
                spendingViewModel.addSpending(newSpending)
            }else {
                val spending = Spending(
                    spendingId!!,
                    etSpendingDescription.text.toString(),
                    etSpending.text.toString().toDoubleOrNull(),
                    etSpendingDate.text.toString(),
                    sCurrency.selectedItemPosition
                )
                spendingViewModel.updateSpending(spending)
            }
        }
        tvCancelSpending.setOnClickListener {
            findNavController().navigate(R.id.action_spendingFragment_to_spendingListFragment)
        }
        etSpendingDate.setOnClickListener{
            hideKeyboard()
            showDatePicker(context)
        }
    }

    private fun showDatePicker(context: Context) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(context, this, year, month, day).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val str = mDateFormat.format(calendar.time)
        if (etSpendingDate.hasFocus()) {
            hideKeyboard()
            etSpendingDate.setText(str)
        }
    }

    private fun registerObserver() {
        this.spendingViewModel.spendingState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage("Despesa cadastrada/atualizada com sucesso!")
                    NavHostFragment.findNavController(this).navigate(R.id.action_spendingFragment_to_spendingListFragment)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.trowable.message)
                }
                is RequestState.Loading -> showLoading("Aguarde um momento") }
        })
        this.spendingViewModel.spendingList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mAdapter.updateSpendings(it)
        })
    }

}


