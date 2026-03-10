package com.davidshibru.taskflow.core.common.android

import android.content.Context
import android.widget.Toast

class CommonAndroid {

    fun hello(context: Context) {
        Toast.makeText(context, "Hello from CommonAndroid!", Toast.LENGTH_SHORT).show()
    }
}