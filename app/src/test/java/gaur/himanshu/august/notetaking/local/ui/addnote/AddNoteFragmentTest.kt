package gaur.himanshu.august.notetaking.local.ui.addnote

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class AddNoteFragmentTest {


    @Test
    fun titleDescriptionIsEmpty_returnFalse() {
        val title = ""
        val desc = ""
        assertThat(AddNoteUtils.validateNote(title, desc)).isFalse()
    }

    @Test
    fun titleIsShort_returnFalse() {
        val title = "a"
        val desc = "afjkjf"
        assertThat(AddNoteUtils.validateNote(title, desc)).isFalse()
    }

    @Test
    fun validInput_returnTrue() {
        val title = "himanshu"
        val desc = "description"
        assertThat(AddNoteUtils.validateNote(title, desc)).isTrue()
    }

}