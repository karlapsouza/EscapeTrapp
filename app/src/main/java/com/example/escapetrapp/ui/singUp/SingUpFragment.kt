package com.example.escapetrapp.ui.singUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController

import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment

class SingUpFragment : BaseFragment() {
    override val layout = R.layout.fragment_sign_up

    private lateinit var tvTerms: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvTerms = view.findViewById(R.id.tvTerms)

        tvTerms.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_singUpFragment)
        }
    }
}
