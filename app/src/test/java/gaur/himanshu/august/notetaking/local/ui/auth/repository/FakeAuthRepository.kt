package gaur.himanshu.august.notetaking.local.ui.auth.repository

import androidx.lifecycle.MutableLiveData
import gaur.himanshu.august.notetaking.Result
import gaur.himanshu.august.notetaking.Status
import javax.inject.Inject

class FakeAuthRepository @Inject constructor() : IAuthRepository {


    private var isConnected = false


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
}