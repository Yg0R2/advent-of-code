package utils

object Logger {

    var isDebugEnabled: Boolean = false

    fun debugLog(message: String) {
        if (isDebugEnabled) {
            println(message)
        }
    }

}
