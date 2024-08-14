package ir.mhp.domain.functional

sealed class RequestResult<out T> {

    data object Idle : RequestResult<Nothing>()
    data object Loading : RequestResult<Nothing>()

    data class Error(
        private var errorMessage: String = "",
    ) : RequestResult<Nothing>() {
        val message: String
            get() = errorMessage
    }

    data class Result<out T>(
        val response: T? = null,
    ) : RequestResult<T>()
}