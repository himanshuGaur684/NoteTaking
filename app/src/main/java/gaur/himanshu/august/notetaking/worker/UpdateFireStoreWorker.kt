package gaur.himanshu.august.notetaking.worker

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import gaur.himanshu.august.notetaking.Constants
import gaur.himanshu.august.notetaking.local.models.Note

class UpdateFireStoreWorker(
    private val appContext: Context,
    params: WorkerParameters
) : Worker(appContext, params) {
    override fun doWork(): Result {
        val pref = appContext.getSharedPreferences(Constants.DATASTORE_NAME, MODE_PRIVATE)
        //inputData.getString(Constants.EMAIL) ?: return Result.failure()
        val pref2 = pref.getString(Constants.EMAIL, "")
        val notes = inputData.getString(Constants.NOTE)
        if(pref2==null){
            return Result.failure()
        }
        if(notes==null){
            return Result.failure()
        }
        return try {
            val updatedNote = Gson().fromJson(notes, Note::class.java)
            FirebaseFirestore.getInstance().collection(pref2)
                .document(updatedNote.firestoreId!!).set(updatedNote)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}