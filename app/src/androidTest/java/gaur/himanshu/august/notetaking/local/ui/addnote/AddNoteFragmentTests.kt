package gaur.himanshu.august.notetaking.local.ui.addnote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import gaur.himanshu.august.notetaking.NoteTakingFragmentFactory
import gaur.himanshu.august.notetaking.R
import gaur.himanshu.august.notetaking.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
@RunWith(AndroidJUnit4::class)
class AddNoteFragmentTests {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: NoteTakingFragmentFactory


    @Before
    fun setUp() {

        hiltRule.inject()


    }

    @After
    fun tearDown() {
    }

    @Test
    fun emptyTitleAndDiscription_returnFalse() {
        val navController = mock(NavController::class.java)


        val bundles = AddNoteFragmentArgs(null).toBundle()
        launchFragmentInHiltContainer<AddNoteFragment>(bundles, fragmentFactory = fragmentFactory) {

            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.add_title)).perform(ViewActions.typeText(""))
        onView(withId(R.id.add_discription)).perform(ViewActions.typeText(""))

        closeSoftKeyboard()

        onView(withId(R.id.save_note)).perform(click())

        onView(withText("Check your Entries")).check(matches(isDisplayed()))

    }


    @Test
    fun oneCharectersTitleAndDiscription_returnFalse() {
        val navController = mock(NavController::class.java)
        val bundles = AddNoteFragmentArgs(null).toBundle()
        launchFragmentInHiltContainer<AddNoteFragment>(bundles, fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.add_title)).perform(ViewActions.typeText("a"))
        onView(withId(R.id.add_discription)).perform(ViewActions.typeText("a"))

        closeSoftKeyboard()
        onView(withId(R.id.save_note)).perform(click())

        onView(withText("Check your Entries")).check(matches(isDisplayed()))

    }

    @Test
    fun validTitleAndDiscription_returnFalse() {
        val navController = mock(NavController::class.java)
        val bundles = AddNoteFragmentArgs(null).toBundle()
        launchFragmentInHiltContainer<AddNoteFragment>(bundles, fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.add_title)).perform(ViewActions.typeText("anis"))
        onView(withId(R.id.add_discription)).perform(ViewActions.typeText("anis sharma"))

        closeSoftKeyboard()
        onView(withId(R.id.save_note)).perform(click())


        verify(navController).popBackStack()

    }
}