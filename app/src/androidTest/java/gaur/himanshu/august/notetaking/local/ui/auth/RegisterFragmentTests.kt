package gaur.himanshu.august.notetaking.local.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import gaur.himanshu.august.notetaking.*
import gaur.himanshu.august.notetaking.local.hilt.AuthRepositoryModule
import gaur.himanshu.august.notetaking.local.ui.auth.repository.IAuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
@UninstallModules(AuthRepositoryModule::class)
class RegisterFragmentTests {

    @get:Rule
    var coroutineRule = CoroutineRuleAndroidTest()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: IAuthRepository

    lateinit var viewModels: AuthViewModel

    @Inject
    lateinit var fragmentFactory: NoteTakingFragmentFactory

    @Mock
    lateinit var observer: Observer<Result<Nothing>>

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModels = AuthViewModel(repository)
        observer = Observer<Result<Nothing>> { }
    }

    @After
    fun tearDown() {
    }

    /*
    * Check the empty entry in our fields*/
    @Test
    fun checkEmptyField_returnFalse() = runBlockingTest {
        val navigation = mock(NavController::class.java)

        launchFragmentInHiltContainer<RegisterFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navigation)
            viewModel = viewModels
        }

        val username = ""
        val password = ""
        val confirmPassword = ""

        onView(withId(R.id.register_username)).perform(typeText(username))
        onView(withId(R.id.register_password)).perform(typeText(password))
        onView(withId(R.id.confirm_password)).perform(typeText(confirmPassword))

        onView(withId(R.id.register_button)).perform(click())

        onView(withText("Registration is failed")).check(matches(isDisplayed()))

    }

    /*
    * password and confirm password is not equal*/
    @Test
    fun checkOurConfirmPasswordAndPassword_returnFalse() = runBlockingTest {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<RegisterFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = viewModels
        }

        val username = "himanshu@gmail.com"
        val password = "himanshu"
        val confirmPassword = "himanshugaur"

        onView(withId(R.id.register_username)).perform(typeText(username))
        onView(withId(R.id.register_password)).perform(typeText(password))
        onView(withId(R.id.confirm_password)).perform(typeText(confirmPassword))

        onView(withId(R.id.register_button)).perform(click())

        onView(withText("Registration is failed")).check(matches(isDisplayed()))
    }

    @Test
    fun usernameDoesNotContainEmailPattern_returnFalse() = runBlockingTest {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<RegisterFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = viewModels
        }
        val username = "himanshugaur"
        val password = "password"
        val confirmPassword = "password"
        onView(withId(R.id.register_username)).perform(typeText(username))
        onView(withId(R.id.register_password)).perform(typeText(password))
        onView(withId(R.id.confirm_password)).perform(typeText(confirmPassword))

        onView(withId(R.id.register_button)).perform(click())

        onView(withText("Registration is failed")).check(matches(isDisplayed()))

    }

    @Test
    fun usernamePasswordConfirmPasswordLengthIsLessThanSixCharector_returnFalse() =
        runBlockingTest {
            val navController = mock(NavController::class.java)

            launchFragmentInHiltContainer<RegisterFragment>(fragmentFactory = fragmentFactory) {
                Navigation.setViewNavController(requireView(), navController)
                viewModel = viewModels
            }

            val username = "Himahfjhgjksgh"
            val password = "shdgf"
            val confirmPassword = "hfgsghfgdh"

            onView(withId(R.id.register_username)).perform(typeText(username))
            onView(withId(R.id.register_password)).perform(typeText(password))
            onView(withId(R.id.confirm_password)).perform(typeText(confirmPassword))

            onView(withId(R.id.register_button)).perform(click())


            onView(withText("Registration is failed")).check(matches(isDisplayed()))

        }

    /*
    * valid input result fine*/
    @Test
    fun validInput() = runBlockingTest {

        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<RegisterFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = viewModels
        }

        val username = "himansdhui@gmail.com"
        val password = "himanshu"
        val confirmPassword = "himanshu"

        onView(withId(R.id.register_username)).perform(typeText(username))
        onView(withId(R.id.register_password)).perform(typeText(password))
        onView(withId(R.id.confirm_password)).perform(typeText(confirmPassword))

        onView(withId(R.id.register_button)).perform(click())
        viewModels.getFlagOfAuthViewModel().observeForever(observer)

        try {
            assertThat(viewModels.flag.value?.status).isEqualTo(Status.SUCCESS)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            viewModels.flag.removeObserver(observer)
        }
    }
}