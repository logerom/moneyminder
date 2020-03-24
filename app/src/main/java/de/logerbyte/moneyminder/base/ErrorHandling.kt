package de.logerbyte.moneyminder.base

import android.content.Context
import android.widget.Toast

/**
 * This class is in one case for Errorhandling in EditTextFields and in other case for Error/Alert Handling with
 * Toasts or AlertDialogs
 */
class ErrorHandling {

    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }


}

