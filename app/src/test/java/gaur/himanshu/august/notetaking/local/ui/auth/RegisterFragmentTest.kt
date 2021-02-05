package gaur.himanshu.august.notetaking.local.ui.auth

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterFragmentTest {


    @Test
    fun fieldsIsSEmpty_returnFalse() {
        val username = ""
        val password = ""
        val confirmPassword = ""
        assertThat(AuthUtils.validateRegisterInput(username, password, confirmPassword)).isFalse()
    }

    @Test
    fun fieldsIsLessthanSixCharecters_returnFalse() {
        val username = "jafjl"
        val password = "jkjgj"
        val confirmPassword = "jkjgj"

        assertThat(AuthUtils.validateRegisterInput(username, password, confirmPassword)).isFalse()
    }

    @Test
    fun passwordConfirmPasswordNotMatchingCorrectly_returnFalse(){
        val username="jimans@gmail.com"
        val password="himanshu"
        val confirmPassword="himanhu"

        assertThat(AuthUtils.validateRegisterInput(username, password, confirmPassword)).isFalse()

    }


    @Test
    fun usernameDoesNotMatchEmailPattern_returnFalse(){
        val username="himanshu"
        val password="himanshu"
        val confirmPassword="himanshu"

        assertThat(AuthUtils.validateRegisterInput(username, password, confirmPassword))
    }

    @Test
    fun validInput_returnTrue(){
        val username="himanshu@gmail.com"
        val password="himanshu"
        val confirmPassword="himanshu"

        assertThat(AuthUtils.validateRegisterInput(username,password,confirmPassword)).isTrue()
    }



}