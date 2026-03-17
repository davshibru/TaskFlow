package com.davidshibru.taskflow.data.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

internal val Context.appDataStore by preferencesDataStore(
    name = "app-data-store"
)