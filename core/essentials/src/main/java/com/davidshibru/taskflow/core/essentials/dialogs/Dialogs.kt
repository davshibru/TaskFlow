package com.davidshibru.taskflow.core.essentials.dialogs

interface Dialogs {

    suspend fun showAlertDialog(config: DialogConfig): Boolean
}