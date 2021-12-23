package com.example.feature.core

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


fun Date.formatToEgyptianDateTimeDefaults(): String{
    val sdf= SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

