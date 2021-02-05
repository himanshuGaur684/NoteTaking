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
@MediumTest
@RunWith(AndroidJUnit4::class)
class FireStoreWorkerTest {


    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var context: Context
    lateinit var executor: Executor


    @Before
    fun setUp() {

        context = ApplicationProvider.getApplicationContext()
        executor = Executors.newSingleThreadExecutor()


    }

    @After
    fun tearDown() {


    }

    @Test
    fun checkOurTestImplementation_resultTrue() {
        val note = Note("title", "desc", "agfhajkg", "HkAgr0k3y5HtxXHsHJ2z", "ahsdgjk")
        val workRequest = TestWorkerBuilder<FireStoreWorker>(context, executor)
            .setInputData(Data.Builder().putString(Constants.NOTE, Gson().toJson(note)).build())
            .build()

        val result = workRequest.doWork()

        assertThat(result).isEqualTo(ListenableWorker.Result.success())
    }

    @Test
    fun checkInputDataIsNull_returnFalse() {
        val workRequest = TestWorkerBuilder<FireStoreWorker>(context, executor)
            .build()

        val result = workRequest.doWork()

        assertThat(result).isEqualTo(ListenableWorker.Result.failure())
    }


}