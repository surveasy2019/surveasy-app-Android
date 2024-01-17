package com.surveasy.surveasy.presentation.util

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun showTwoButtonDialog(
    context: Context,
    title: String,
    content: String,
    positiveTitle: String,
    negativeTitle: String,
    positiveListener: () -> Unit
) {
    val builder = AlertDialog.Builder(context).apply {
        setTitle(title).setMessage(content)
        setPositiveButton(positiveTitle) { dialogInterface, _ ->
            positiveListener()
            dialogInterface.cancel()
        }
        setNegativeButton(negativeTitle) { dialogInterface, _ ->
            dialogInterface.cancel()
        }
    }
    val dialog = builder.create()
    dialog.show()
}