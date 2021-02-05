package gaur.himanshu.august.notetaking.local.ui.auth

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before


import org.junit.Test

@ExperimentalCoroutinesApi
class LoginFragmentTest {


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    /*
    * Username password not be empty
    * */


    @Test
    fun emailPasswordFieldsIsEmpty_returnFalse() {
        val username = ""
        val password = ""
        assertThat(AuthUtils.validatingLoginInput(username, password)).isFalse()
    }

    @Test
    fun emailOrPasswordLengthLesssThanSix_returnFalse() {
        val username = "asdfs"
        val password = "jskl"
        assertThat(AuthUtils.validatingLoginInput(username, password)).isFalse()
    }

    @Test
    fun emailFieldIsNotLikeEmailPattern_returnFalse() {
        val username = "himanshuGaurg"
        val password = "jaishreeram"

        assertThat(AuthUtils.validatingLoginInput(username, password)).isFalse()
    }

    @Test
    fun validInput_returnTrue() {
        val username = "jaishreeRam@gmail.com"
        val password = "jai shree ram"
        assertThat(AuthUtils.validatingLoginInput(username, password)).isTrue()
    }


}