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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

class AboutFragment : BaseFragment() {
    override val layout = R.layout.fragment_about
    private lateinit var btShare: ImageButton
    private lateinit var btCall: ImageButton
    private lateinit var ivBackAbout: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
    }

    private fun setUpView(view: View) {
        btShare = view.findViewById(R.id.btShare)
        btCall = view.findViewById(R.id.btCall)
        ivBackAbout = view.findViewById(R.id.ivBackAbout)
        setUpListener()
    }

    private fun setUpListener() {
        btShare.setOnClickListener {}
        btCall.setOnClickListener {
            //requestCall()
            val number = "9999999999"
            checkPermission(number)
        }
        ivBackAbout.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_homeFragment)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CALL_PERMISSION_CODE) {
            handleCallPermissionResult(permissions, grantResults)
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun handleCallPermissionResult(permissions: Array<out String>, grantResults: IntArray) {
        val denied = grantResults.indices.filter { grantResults[it] != PackageManager.PERMISSION_GRANTED }
        if (denied.isEmpty()) {
            makeCall()
        } else {
            showErrorSnackbar()
        }
    }

    private fun requestCall() {
        val permissionStatus = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            makeCall()
        } else {
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), CALL_PERMISSION_CODE)
        }
    }

    private fun makeCall() {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:79999999999")))
    }

    private fun makeCall2(number: String) {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$number")))
    }

    private fun showErrorSnackbar() {
        view?.let { Snackbar.make(it, "You denied calling permission request", Snackbar.LENGTH_SHORT).show() }
    }

    companion object {
        const val CALL_PERMISSION_CODE = 554   //antes estava 101
    }

    //sem pedir permissao do usuário
//    fun Context.makePhoneCall(number: String) : Boolean {
//        try {
//            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
//            startActivity(intent)
//            return true
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return false
//        }
//    }

    private fun checkPermission(number: String) {
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.CALL_PHONE)) {
                Toast.makeText(activity, "Please grant My App permission to call.", Toast.LENGTH_SHORT).show()
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    554)  //antes estava 42
            }
        } else {
            // Permission has already been granted
            Toast.makeText(activity, "Calling My App now ...", Toast.LENGTH_SHORT).show()
            makeCall2(number)
        }
    }

}
