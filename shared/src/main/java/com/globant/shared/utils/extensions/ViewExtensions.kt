package com.globant.shared.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(
            Context
                .INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}

fun <DB : ViewDataBinding> ViewGroup.binding(view: Int): DB {
    return DataBindingUtil.inflate(
        LayoutInflater.from(context),
        view,
        this,
        false
    )
}

fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, text, duration).show() }

fun Fragment?.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) =
    this?.let { activity.toast(text, duration) }

fun Fragment?.toast(@StringRes text: Int, duration: Int = Toast.LENGTH_LONG) =
    this?.let { activity.toast(getString(text), duration) }

fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            if (newTask) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    } catch (e: Exception) {
        false
    }
}

fun Fragment.browse(url: String, newTask: Boolean = false) = activity?.browse(url, newTask)