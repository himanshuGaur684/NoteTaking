package gaur.himanshu.august.notetaking.local.ui.auth.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import gaur.himanshu.august.notetaking.Constants
import gaur.himanshu.august.notetaking.Result
import gaur.himanshu.august.notetaking.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class AuthRepository(
    private val auth: FirebaseAuth,
    private val prefrenceDataStore: SharedPreferences
) : IAuthRepository {

    private val _flag = MutableLiveData<Result<Nothing>>()
    val flag: LiveData<Result<Nothing>> = _flag


    override suspend fun login(
        username: String,
        password: String,
        flag: MutableLiveData<Result<Nothing>>
    ) {
        _flag.postValue(Result(Status.LOADING, null, "loading"))

        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
            if (it.isSuccessful) {
                CoroutineScope(IO).launch{
                }
                flag.postValue(Result(Status.SUCCESS, null, null))
            } else {
                flag.postValue(Result(Status.ERROR, null, null))
            }
        }


    }

    override suspend fun saveEmailInDataStore(key: String, value: String) {
      prefrenceDataStore.edit().putString(key, value).apply()
    }

    override fun getData(key: String): String {
      return prefrenceDataStore.getString(key,"").toString()
    }


    override suspend fun register(
        username: String,
        password: String,
        confirmPassword: String,
        flag: MutableLiveData<Result<Nothing>>
    ) {
        _flag.postValue(Result(Status.LOADING, null, "loading"))

        auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener {
            if (it.isSuccessful) {
                CoroutineScope(IO).launch{
                    saveEmailInDataStore(Constants.EMAIL,username)
                }
                flag.postValue(Result(Status.SUCCESS, null, null))
            } else {
                flag.postValue(Result(Status.ERROR, null, null))
            }
        }

    }

}