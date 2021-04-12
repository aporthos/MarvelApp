package com.globant.marvelapp.ui.common

import androidx.lifecycle.ViewModel
import com.globant.marvelapp.R
import com.globant.shared.utils.ErrorManager

abstract class BaseViewModel: ViewModel() {
    protected fun toGetMessageError(messageCode: String?): Int = messageCode?.let {
        val message = when (ErrorManager.fromString(it)) {
            ErrorManager.INVALID_CREDENTIALS -> {
                R.string.message_invalid_credential
            }
            ErrorManager.MISSING_PARAMETER -> {
                R.string.message_invalid_missing_parameter
            }
            ErrorManager.NO_INTERNET -> {
                R.string.message_no_internet
            }
            ErrorManager.ID_INVALID -> {
                R.string.character_detail_fragment_id_invalid
            }
            ErrorManager.JSON_INVALID -> {
                R.string.character_detail_fragment_json_invalid
            }
            else -> R.string.message_error_unknow
        }
        message
    } ?: run {
        R.string.message_error_unknow
    }
}