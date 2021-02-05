package gaur.himanshu.august.notetaking.local.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import gaur.himanshu.august.notetaking.*
import gaur.himanshu.august.notetaking.local.adapters.HomeAdapter
import gaur.himanshu.august.notetaking.local.hilt.HomeRepositoryModule
import gaur.himanshu.august.notetaking.local.models.Note
import gaur.himanshu.august.notetaking.local.ui.home.repository.IHomeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(HomeRepositoryModule::class)
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: NoteTakingFragmentFactory

    @Inject
    lateinit var repository: IHomeRepository

    lateinit var viewModels: HomeViewModel


    @Before
    fun setUp() {
        hiltRule.inject()
        viewModels = HomeViewModel(repository)
    }

    @After
    fun tearDown() {
    }


    @Test
    fun insertionTesting() {
        val note = Note("shggh", "shfga", "sdf1", "1")
        val navController = mock(NavController::class.java)

        viewModels.insertNote(ApplicationProvider.getApplicationContext(), note)

        launchFragmentInHiltContainer<HomeFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = viewModels
            homeAdapter.setContentList(viewModels.list.getOrAwaitValue().peekContent().data!!)
        }

        onView(withText("shggh")).check(matches(isDisplayed()))

    }


    @Test
    fun deletionTesting() = runBlockingTest {
        val note = Note("shggh", "shfga", "sdf1", "1")
        viewModels.insertNote(ApplicationProvider.getApplicationContext(), note)
        launchActivity()
        onView(withId(R.id.list_item_root)).perform(longClick())

        onView(withId(R.id.list_item_root)).check(doesNotExist())
    }

    @Test
    fun upgradationTesting() {

        val note = Note("shggh", "shfga", "sdf2", "1")
        viewModels.insertNote(ApplicationProvider.getApplicationContext(), note)
        launchActivity()


        onView(withId(R.id.home_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeAdapter.MyViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.add_title)).perform(typeText("jai shree ram"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.save_note)).perform(click())

        onView(withText("shgghjai shree ram")).check(matches(isDisplayed()))

    }


    @Test
    fun toolbarMenuTesting_jumpToLoginActivity() {


        launchActivity()

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())

        onView(withText("Logout")).perform(click())

        onView(withId(R.id.login_container)).check(matches(isDisplayed()))


    }


    private fun launchActivity(): ActivityScenario<ContainerActivity>? {
        val activityScenario = launch(ContainerActivity::class.java)
        activityScenario.onActivity { activity ->
            // Disable animations in RecyclerView
            (activity.findViewById(R.id.home_recycler) as RecyclerView).itemAnimator = null
        }
        return activityScenario
    }
}