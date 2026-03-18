package com.davidshibru.taskflow.core.data

import android.content.Context
import android.widget.Toast

class Data {
    fun hello(context: Context) {
        Toast.makeText(context, "Hello from Data!", Toast.LENGTH_SHORT).show()
    }
}