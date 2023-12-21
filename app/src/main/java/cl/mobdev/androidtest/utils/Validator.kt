package cl.mobdev.androidtest.utils

object Validator {
    fun isValidPassword(password: String): Boolean {
        val minLength = MIN_PASS_LENGTH
        val upperCasePattern = Regex("[A-Z]")
        val lowerCasePattern = Regex("[a-z]")
        val digitPattern = Regex("[0-9]")

        if (password.length < minLength) {
            return false
        }

        if (!upperCasePattern.containsMatchIn(password)) {
            return false
        }
        if (!lowerCasePattern.containsMatchIn(password)) {
            return false
        }
        if (!digitPattern.containsMatchIn(password)) {
            return false
        }
        return true
    }

    fun isValidEmail(email: String): Boolean {
        if (email.isEmpty()) {
            return false
        }
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

        val isValidEmailAddress = emailPattern.matches(email)
        val isNotEmpty = email.isNotEmpty()

        return (isValidEmailAddress) || (isNotEmpty)
    }

    private const val MIN_PASS_LENGTH = 6
}