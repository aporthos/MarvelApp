package com.globant.shared.utils

import com.globant.shared.utils.Constants.ID_INVALID_CODE
import com.globant.shared.utils.Constants.INVALID_CREDENTIALS_CODE
import com.globant.shared.utils.Constants.JSON_INVALID_CODE
import com.globant.shared.utils.Constants.MISSING_PARAMETER_CODE
import com.globant.shared.utils.Constants.NO_INTERNET_CODE

enum class ErrorManager(val error: String) {
    INVALID_CREDENTIALS(INVALID_CREDENTIALS_CODE),
    MISSING_PARAMETER(MISSING_PARAMETER_CODE),
    ID_INVALID(ID_INVALID_CODE),
    NO_INTERNET(NO_INTERNET_CODE),
    JSON_INVALID(JSON_INVALID_CODE);

    companion object {
        private val errorMap = values().associateBy(ErrorManager::error)
        fun fromString(insightsAction: String) = errorMap[insightsAction]
    }
}