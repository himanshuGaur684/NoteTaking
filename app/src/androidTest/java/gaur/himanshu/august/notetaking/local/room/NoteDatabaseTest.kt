package gaur.himanshu.august.notetaking.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import gaur.himanshu.august.notetaking.local.models.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class NoteDatabaseTest {


    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("fake_db")
    lateinit var roomDb: NoteDatabase

    lateinit var noteDao: NoteDao

    @Before
    fun setUp() {

        hiltRule.inject()

        noteDao = roomDb.getNoteDb()
    }

    @After
    fun tearDown() {

        roomDb.close()
    }


    @Test
    fun insertNote_Test() = runBlockingTest {

        val note = Note("title", "desc", "timer1", "9")

        noteDao.insert(note)

        assertThat(noteDao.getAllNotes()).isNotEmpty()

    }

    @Test
    fun insertList_Test() = runBlockingTest {
        val list =
            listOf<Note>(Note("title", "desc", "timer1", "2"), Note("title", "desc", "timer2", "9"))

        noteDao.insertList(list)

        assertThat(noteDao.getAllNotes().size).isEqualTo(2)

    }

    @Test
    fun delete_Test() = runBlockingTest {
        val note = Note("title", "desc", "time1", "4")

        noteDao.insert(note)

        noteDao.delete(note)

        assertThat(noteDao.getAllNotes()).isEmpty()
    }

}