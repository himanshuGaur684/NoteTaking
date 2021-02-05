package gaur.himanshu.august.notetaking.local.ui.auth

import android.util.Log

object AuthUtils {

    fun validatingLoginInput(username: String, password: String): Boolean {
        if (username.isEmpty() || password.isEmpty()) {
            return false
        } else if (username.length <= 6) {
            return false
        } else if (password.length <= 6) {
            return false
        } else if (!username.contains("@")) {
            return false
        }
        return true
    }

    fun validateRegisterInput(
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false
        }

        if (username.length <= 6) {
            return false
        }
        if (password.length <= 6) {
            return false
        }
        if (confirmPassword.length <= 6) {
            return false
        }
        if (!username.contains("@")) {
            return false
        }
        if(password != confirmPassword){
            Log.d("TAG", "validateRegisterInput: ")
            return false
        }

        return true
    }

}