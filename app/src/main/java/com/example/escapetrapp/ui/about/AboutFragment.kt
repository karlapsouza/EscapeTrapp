package com.example.escapetrapp.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : BaseFragment() {
    override val layout = R.layout.fragment_about

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

}
