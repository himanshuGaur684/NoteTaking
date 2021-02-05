package gaur.himanshu.august.notetaking.local.ui.auth

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import gaur.himanshu.august.notetaking.Constants
import gaur.himanshu.august.notetaking.Result
import gaur.himanshu.august.notetaking.Status
import gaur.himanshu.august.notetaking.local.ui.auth.repository.IAuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: IAuthRepository) : ViewModel() {

    private val _flag = MutableLiveData<Result<Nothing>>()
    var flag: LiveData<Result<Nothing>> = _flag

    fun getFlagOfAuthViewModel():LiveData<Result<Nothing>>{
        return this.flag
    }

    fun register(username: String, password: String, confirmPassword: String) =
        viewModelScope.launch {
            repository.saveEmailInDataStore(Constants.EMAIL,username)

            _flag.postValue(Result(Status.LOADING, null, null))
            repository.register(username, password, confirmPassword, _flag)
        }


    fun login(username: String, password: String) = viewModelScope.launch {


        repository.saveEmailInDataStore(Constants.EMAIL,username)
        _flag.postValue(Result(Status.LOADING, null, null))
        repository.login(username, password, _flag)
        Log.d("TAG", "login: ${repository.getData(Constants.EMAIL)}")

    }
}