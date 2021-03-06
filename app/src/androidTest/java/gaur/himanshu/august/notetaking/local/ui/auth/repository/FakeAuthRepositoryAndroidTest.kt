package gaur.himanshu.august.notetaking.local.ui.auth.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gaur.himanshu.august.notetaking.Result
import gaur.himanshu.august.notetaking.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class FakeAuthRepositoryAndroidTest : IAuthRepository {


    private var isConnected = true



    fun setNetwork(boolean: Boolean) {
        isConnected = boolean
    }


    override suspend fun login(
        username: String,
        password: String,
        flag: MutableLiveData<Result<Nothing>>
    ) {

        if (isConnected) {
            flag.postValue(Result(Status.SUCCESS, null, null))
        } else {
            flag.postValue(Result(Status.ERROR, null, null))
        }
    }

    override suspend fun register(
        username: String,
        password: String,
        confirmPassword: String,
        flag: MutableLiveData<Result<Nothing>>
    ) {
        if (isConnected) {
            flag.postValue(Result(Status.SUCCESS, null, null))
        } else {
            flag.postValue(Result(Status.ERROR, null, null))
        }
    }

    override suspend fun saveEmailInDataStore(key: String, value: String) {

    }

    override fun getData(key: String):String {
       return "null"
    }
}