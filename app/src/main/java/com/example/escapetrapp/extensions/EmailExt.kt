package com.example.escapetrapp.extensions

import android.util.Patterns
import java.util.regex.Pattern

class EmailExt {

    fun String.isValidEmail() = run {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        pattern.matcher(this).matches()
    }
}