package gaur.himanshu.august.notetaking.worker

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import gaur.himanshu.august.notetaking.Constants
import gaur.himanshu.august.notetaking.local.models.Note
import gaur.himanshu.august.notetaking.local.room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FireStoreWorker(

    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(
    context,
    workerParams
) {
    override fun doWork(): Result {

        val fireStore = FirebaseFirestore.getInstance()
        val noteDao = NoteDatabase.get(context).getNoteDb()
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(Constants.DATASTORE_NAME, MODE_PRIVATE)

        val notes = inputData.getString(Constants.NOTE)
        val pref = sharedPreferences.getString(Constants.EMAIL, "")

        if (notes == null) {
            return Result.failure()
        }
        if (pref.isNullOrEmpty()) {
            return Result.failure()
        }

        return try {
            val note =
                Gson().fromJson(
                    notes,
                    Note::class.java
                )

            fireStore.collection(pref).add(
                note
            ).addOnSuccessListener {
                note.firestoreId = it.id
                CoroutineScope(IO).launch {
                    noteDao.insert(note)
                }
            }


            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}