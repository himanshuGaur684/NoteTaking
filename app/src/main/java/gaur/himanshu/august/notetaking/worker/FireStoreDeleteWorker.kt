package gaur.himanshu.august.notetaking.worker

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import gaur.himanshu.august.notetaking.Constants
import gaur.himanshu.august.notetaking.local.models.Note
import gaur.himanshu.august.notetaking.utils.EspressoIdlingResource

class FireStoreDeleteWorker(
    private val appContext: Context,
    params: WorkerParameters
) : Worker(appContext, params) {
    override fun doWork(): Result {
        EspressoIdlingResource.countingIdlingResource.increment()
        val firestore = FirebaseFirestore.getInstance()
        val sharedPreferences =
            appContext.getSharedPreferences(Constants.DATASTORE_NAME, MODE_PRIVATE)
        inputData.getString(Constants.NOTE) ?: return Result.failure(inputData)

        return try {

            val note = Gson().fromJson(inputData.getString(Constants.NOTE), Note::class.java)

            firestore.collection(sharedPreferences.getString(Constants.EMAIL, "")!!)
                .document(note.firestoreId!!).delete()
            EspressoIdlingResource.countingIdlingResource.decrement()
            Result.success()
        } catch (e: Exception) {
            EspressoIdlingResource.countingIdlingResource.decrement()
            Result.retry()

        }


    }
}