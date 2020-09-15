package com.example.escapetrapp.views

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.services.constants.TripConstants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class AboutFragment : BaseFragment() {
    override val layout = R.layout.fragment_about
    private lateinit var btShare: FloatingActionButton
    private lateinit var btCall: FloatingActionButton
    private lateinit var ivBackAbout: ImageView
    private lateinit var tvAbout: TextView
    private lateinit var tvAboutBru: TextView
    private lateinit var tvAboutKa: TextView
    private lateinit var tvAboutTati: TextView
    private lateinit var tvAboutVi: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
    }

    private fun setUpView(view: View) {
        btShare = view.findViewById(R.id.btShare)
        btCall = view.findViewById(R.id.btCall)
        ivBackAbout = view.findViewById(R.id.ivBackAbout)
        tvAbout = view.findViewById(R.id.tvAbout)
        tvAboutBru = view.findViewById(R.id.tvAboutBru)
        tvAboutKa = view.findViewById(R.id.tvAboutKa)
        tvAboutTati = view.findViewById(R.id.tvAboutTati)
        tvAboutVi = view.findViewById(R.id.tvAboutVi)
        setUpListener()
    }

    private fun setUpListener() {
        btShare.setOnClickListener {
            share()
        }
        btCall.setOnClickListener {
            val number = "9999999999"
            checkPermission(number)
        }
        ivBackAbout.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_homeFragment)
        }
    }

    private fun share(){
        val about = tvAbout.text
        val bru = tvAboutBru.text
        val ka = tvAboutKa.text
        val tati = tvAboutTati.text
        val vi = tvAboutVi.text

        val text = "$about \n $bru \n $ka \n $tati \n $vi"
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "$text")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun makeCall(number: String) {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$number")))
    }

    private fun checkPermission(number: String) {
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.CALL_PHONE)) {
                Toast.makeText(activity, "Please grant My App permission to call.", Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    554)  //antes estava 42
            }
        } else {
            Toast.makeText(activity, "Calling My App now ...", Toast.LENGTH_SHORT).show()
            makeCall(number)
        }
    }

}
