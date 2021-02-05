package gaur.himanshu.august.notetaking.worker


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.testing.TestWorkerBuilder
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import gaur.himanshu.august.notetaking.Constants
import gaur.himanshu.august.notetaking.local.models.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@MediumTest
class UpdateFireStoreWorkerTest {


    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var context: Context
    lateinit var executors: Executor


    @Before
    fun setUp() {
        hiltRule.inject()
        context = ApplicationProvider.getApplicationContext()
        executors = Executors.newSingleThreadExecutor()
    }

    @After
    fun tearDown() {
    }


    @Test
    fun inputDataIsNull_returnFalse() {
        val workRequest = TestWorkerBuilder<UpdateFireStoreWorker>(context, executors).build()

        val result = workRequest.doWork()

        assertThat(result).isEqualTo(ListenableWorker.Result.failure())
    }

    @Test
    fun validNoteInWorkRequestReached_returnTrue() {
        val note = Note("fjagh", "fjakgh", "gjasdkgh", "HkAgr0k3y5HtxXHsHJ2z")
        val gson = Data.Builder().putString(Constants.NOTE, Gson().toJson(note)).build()

        val workRequest =
            TestWorkerBuilder<UpdateFireStoreWorker>(context, executors).setInputData(gson).build()

        val result = workRequest.doWork()

        assertThat(result).isEqualTo(ListenableWorker.Result.success())

    }
}