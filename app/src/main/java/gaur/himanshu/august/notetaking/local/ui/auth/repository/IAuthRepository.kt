package gaur.himanshu.august.notetaking.local.ui.auth.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import gaur.himanshu.august.notetaking.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IAuthRepository {


    suspend fun login(username:String,password:String,flag: MutableLiveData<Result<Nothing>>)

    suspend fun register(username: String,password: String,confirmPassword:String,flag:MutableLiveData<Result<Nothing>>)


    suspend fun saveEmailInDataStore(key:String,value:String)

    fun getData(key:String): String
}