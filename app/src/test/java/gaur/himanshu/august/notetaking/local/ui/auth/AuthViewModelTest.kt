package gaur.himanshu.august.notetaking.local.ui.auth


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import gaur.himanshu.august.notetaking.CoroutineRule
import gaur.himanshu.august.notetaking.Status
import gaur.himanshu.august.notetaking.getOrAwaitValues
import gaur.himanshu.august.notetaking.local.ui.auth.repository.FakeAuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    lateinit var viewModel: AuthViewModel

    lateinit var fakeAuthRepository: FakeAuthRepository

    @Before
    fun setUp() {
        fakeAuthRepository = FakeAuthRepository()
        viewModel = AuthViewModel(fakeAuthRepository)
    }

    @After
    fun tearDown() {
    }

    /*
    * login successful if network is present */
    @Test
    fun loginWhenInternetIsPresent_returnTrue() = runBlockingTest {

        val username = "fsjghk"
        val password = "sfghkjghf"

        fakeAuthRepository.setNetwork(true)
        viewModel.login(username, password)

        assertThat(viewModel.flag.getOrAwaitValues().status).isEqualTo(Status.SUCCESS)

    }

    @Test
    fun loginWhenInternetIsNotPresent_returnFalse() {
        val username = "sfhkfh"
        val password = "jasfhgj"

        fakeAuthRepository.setNetwork(false)
        viewModel.register(username, password, password)

        val value = viewModel.flag.getOrAwaitValues()

        assertThat(value.status).isEqualTo(Status.ERROR)

    }



}