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
import androidx.test.ext.junit.runners.AndroidJUnit4
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
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@MediumTest
@UninstallModules(AuthRepositoryModule::class)
class LoginFragmentTests {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRuleAndroidTest()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    lateinit var viewModels: AuthViewModel


    @Mock
    lateinit var observer: Observer<Result<Nothing>>

    @Inject
    lateinit var repo: IAuthRepository

    @Inject
    lateinit var fragmentFactory: NoteTakingFragmentFactory


    @Before
    fun setUp() {
        observer = Observer<Result<Nothing>> { }
        hiltRule.inject()
        //repository = FakeAuthRepositoryAndroidTest()
        viewModels = AuthViewModel(repo)
    }

    @After
    fun tearDown() {
        coroutineRule.cleanupTestCoroutines()
    }

    /*
    * Check our views are present in the fragment*/
    @Test
    fun allViewsIsPresentCorrectly_returnTrue() {

        val navigation = mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navigation)
            viewModel = viewModels
        }

        // login username
        onView(withId(R.id.login_username)).check(matches(isDisplayed()))

        onView(withId(R.id.login_password)).check(matches(isDisplayed()))

        onView(withId(R.id.login_button)).check(matches(isDisplayed()))


    }

    /*
    * Check if our any field is empty*/
    @Test
    fun checkIfAnyFieldisEmpty_returnFalse() = runBlockingTest {

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment>(fragmentFactory = fragmentFactory) {

            Navigation.setViewNavController(requireView(), navController)

            viewModel = viewModels
        }

        val username = ""
        val password = ""



        onView(withId(R.id.login_button)).perform(click())

        onView(withText("Login Failed")).check(matches(isDisplayed()))


    }

    /*
    * check our email is of correct email pattern*/
    @Test
    fun usernameDoesNotContainGmailPattern_returnFalse() = runBlockingTest {

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = viewModels

        }

        val username = "himsafhfhsgh"
        val password = "himanshu"

        onView(withId(R.id.login_username)).perform(typeText(username))
        onView(withId(R.id.login_password)).perform(typeText(password))

        onView(withId(R.id.login_button)).perform(click())



        onView(withText("Login Failed")).check(matches(isDisplayed()))


    }

    /*
    * check the length of our username and passwords*/
    @Test
    fun usernameIsLessThanSixDigit_returnFalse() = runBlockingTest {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = viewModels
        }

        val username = "himand"
        val password = "passwod"

        onView(withId(R.id.login_username)).perform(typeText(username))
        onView(withId(R.id.login_password)).perform(typeText(password))
        onView(withId(R.id.login_button)).perform(click())

        onView(withText("Login Failed")).check(matches(isDisplayed()))

    }

    /*
    * Valid input is accepted and check our state is success or not*/
    @Test
    fun validInput_returnTrue() = runBlockingTest {
        val navController = mock(NavController::class.java)
        // launch the fragment in hilt container
        launchFragmentInHiltContainer<LoginFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            // ovverride viewmodel in our loginFragment
            viewModel = viewModels
        }
        // set our observer in viewmodels flag
        viewModels.getFlagOfAuthViewModel().observeForever(observer)
        val username = "himanshugaur@gmail.com"
        val password = "jaishreeram"


        onView(withId(R.id.login_username)).perform(typeText(username))
        onView(withId(R.id.login_password)).perform(typeText(password))

        onView(withId(R.id.login_button)).perform(click())
        // check our status of our live data
        try {
            viewModels.flag.observeForever(observer)
            assertThat(viewModels.flag.value?.status).isEqualTo(Status.SUCCESS)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            viewModels.flag.removeObserver(observer)
        }
    }

    @Test
    fun navigationToRegisterFragment() = runBlockingTest {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<LoginFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = viewModels
        }

        onView(withId(R.id.navigate_to_register)).perform(click())

        verify(navController).navigate(R.id.action_loginFragment_to_registerFragment)

    }
}