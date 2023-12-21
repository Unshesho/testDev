package cl.mobdev.androidtest.session.data.cache

object SharedPreferencesLogin {

    private var tokenShared = ""

    fun saveAuthToken(token: String) {
        tokenShared = token;
    }

    fun fetchAuthToken(): String? {
        return tokenShared
    }

}