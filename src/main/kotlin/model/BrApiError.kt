package model

/**
 * Generic Error class for handling errors from API calls
 *
 * @property errorMessage Formatted error message
 * @property errorCode Error code for additional handling
 */
data class BrApiError(
    val errorMessage: String,
    val errorCode: Int
)