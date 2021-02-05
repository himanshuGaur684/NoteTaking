package gaur.himanshu.august.notetaking.worker

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.*
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.TestWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import gaur.himanshu.august.notetaking.Constants
import gaur.himanshu.august.notetaking.getOrAwaitValue
import gaur.himanshu.august.notetaking.local.models.Note
import gaur.himanshu.august.notetaking.utils.EspressoIdlingResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@MediumTest

class FireStoreDeleteWorkerTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<WorkInfo.State>

    lateinit var context: Context
    lateinit var executor: Executor


    @Before
    fun setUp() {

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)

        context = InstrumentationRegistry.getInstrumentation().targetContext

        executor = Executors.newSingleThreadExecutor()

        val executor = SynchronousExecutor()
        val config =
            Configuration.Builder().setExecutor(executor).build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    /*
    * check if input data is not null we return Result.success()*/

    @Test
    fun checkIfInputIsPresent_returnResultSuccess() {


        val note = Note("title", "desc", "shfgk", "HkAgr0k3y5HtxXHsHJ2z", "asfj")
        val input = Data.Builder().putString(Constants.NOTE, Gson().toJson(note)).build()
        val workRequest =
            TestWorkerBuilder<FireStoreDeleteWorker>(context, executor).setInputData(input).build()

        val result = workRequest.doWork()

        assertThat(result).isEqualTo(ListenableWorker.Result.success())


    }

    /*
    * check if input is not present it return the value is Result.failure()*/
    @Test
    fun checkIfInputDataIsNotPresentItReturn_ResultFailure() {


        val workRequest = OneTimeWorkRequestBuilder<FireStoreDeleteWorker>()
            .build()
        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(workRequest)
        val info = workManager.getWorkInfoByIdLiveData(workRequest.id).getOrAwaitValue()
        assertThat(info.state).isEqualTo(WorkInfo.State.FAILED)

    }
}