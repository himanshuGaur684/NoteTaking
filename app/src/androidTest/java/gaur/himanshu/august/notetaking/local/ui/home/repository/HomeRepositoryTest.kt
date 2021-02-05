package gaur.himanshu.august.notetaking.local.ui.home.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import gaur.himanshu.august.notetaking.local.models.Note
import kotlinx.coroutines.flow.Flow


class HomeRepositoryTest : IHomeRepository {


    val mutableList = mutableListOf<Note>()

    override fun getSaveData(key: String): String {
        return "null"
    }

    override suspend fun syncDataFromNetwork() {

    }


    private val _list = MutableLiveData<List<Note>>()


    private fun refresh() {
        _list.postValue(mutableList)
    }

    override suspend fun insertNote(context: Context,note: Note) {
        mutableList.add(note)
        refresh()
    }

    override suspend fun deleteNote(context:Context,note: Note) {
        mutableList.remove(note)
        refresh()
    }

    override suspend fun insertList(list: List<Note>) {
        mutableList.addAll(list)
        refresh()
    }

    override suspend fun getAllNotes(): List<Note> {
        return mutableList
    }

    override suspend fun insertNoteRemote() {

    }

    override suspend fun updateNote(context:Context,note: Note) {
        val i = mutableList.indexOf(note)
        mutableList.removeAt(i)
        mutableList.add(note)
    }

    override suspend fun deleteRemoteNote(note: Note) {

    }
}