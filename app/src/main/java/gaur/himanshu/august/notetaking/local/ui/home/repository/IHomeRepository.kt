package gaur.himanshu.august.notetaking.local.ui.home.repository

import android.content.Context
import gaur.himanshu.august.notetaking.local.models.Note
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {

    suspend fun insertNote(context: Context,note: Note)

    suspend fun deleteNote(context: Context,note: Note)

    suspend fun insertList(list: List<Note>)

    suspend fun getAllNotes(): List<Note>

    suspend fun insertNoteRemote()

    suspend fun updateNote(context: Context,note: Note)

    suspend fun deleteRemoteNote(note: Note)

    fun getSaveData(key:String): String

   suspend fun syncDataFromNetwork()

}