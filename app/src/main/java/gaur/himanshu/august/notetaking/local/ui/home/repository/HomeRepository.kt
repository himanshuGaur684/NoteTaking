package gaur.himanshu.august.notetaking.local.ui.home.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import gaur.himanshu.august.notetaking.Constants
import gaur.himanshu.august.notetaking.local.models.Note
import gaur.himanshu.august.notetaking.local.room.NoteDao
import gaur.himanshu.august.notetaking.worker.FireStoreDeleteWorker
import gaur.himanshu.august.notetaking.worker.FireStoreWorker
import gaur.himanshu.august.notetaking.worker.UpdateFireStoreWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeRepository(
    private val noteDao: NoteDao,
    private val prefrenceDataStore: SharedPreferences,
    private val firestore: FirebaseFirestore
) : IHomeRepository {
    override fun getSaveData(key: String): String {
        return prefrenceDataStore.getString(key, "").toString()
    }

    override suspend fun syncDataFromNetwork() {
        val list = mutableListOf<Note>()
        val email = prefrenceDataStore.getString(Constants.EMAIL, "")!!

        firestore.collection(email).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.documents?.forEach {

                    Log.d("TAG", "syncDataFromNetwork: ${it.data}")
                    val title = it.data?.get("title").toString()
                    val desc = it.data?.get("desc").toString()
                    val time = it.data?.get("time").toString()
                    val fireStoreId = it.id.toString()
                    val emails = it.data?.get("email").toString()

                    list.add(Note(title, desc, time, firestoreId = fireStoreId, email = emails))


                }

                CoroutineScope(IO).launch { noteDao.insertList(list) }
            } else {
                return@addOnCompleteListener
            }


        }


    }

    override suspend fun insertNote(context: Context, note: Note) {


        val firestoreWorker = OneTimeWorkRequestBuilder<FireStoreWorker>().setInputData(
            Data.Builder().putString(Constants.NOTE, Gson().toJson(note)).build()
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).build()
        WorkManager.getInstance(context).enqueue(firestoreWorker)
        noteDao.insert(note)
    }

    override suspend fun deleteNote(context: Context, note: Note) {
        val workRequest = OneTimeWorkRequestBuilder<FireStoreDeleteWorker>()
            .setInputData(Data.Builder().putString(Constants.NOTE, Gson().toJson(note)).build())
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
        noteDao.delete(note)
    }

    override suspend fun insertList(list: List<Note>) {

    }

    override suspend fun getAllNotes(): List<Note> {
        val emails = prefrenceDataStore.getString(Constants.EMAIL, "")
        return noteDao.getAllNotes(emails!!)
    }

    override suspend fun updateNote(context: Context, note: Note) {

        val workerRequest = OneTimeWorkRequestBuilder<UpdateFireStoreWorker>()
            .setInputData(
                Data.Builder().putString(Constants.NOTE, Gson().toJson(note))
                    .putString(Constants.EMAIL, prefrenceDataStore.getString(Constants.EMAIL, ""))
                    .build()
            )
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).build()

        WorkManager.getInstance(context).enqueue(workerRequest)


        noteDao.update(note)
    }

    // Remote

    override suspend fun insertNoteRemote() {

    }


    override suspend fun deleteRemoteNote(note: Note) {

    }


}