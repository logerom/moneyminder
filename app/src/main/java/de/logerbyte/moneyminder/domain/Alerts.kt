package de.logerbyte.moneyminder.domain

import android.content.Context
import android.widget.Toast

class Alerts {}

fun Context?.showToastAlert(alertText: String) {
    Toast.makeText(this, alertText, Toast.LENGTH_LONG).show()
}
