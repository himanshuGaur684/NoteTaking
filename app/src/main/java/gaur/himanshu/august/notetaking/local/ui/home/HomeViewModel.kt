package gaur.himanshu.august.notetaking.local.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gaur.himanshu.august.notetaking.Constants
import gaur.himanshu.august.notetaking.Events
import gaur.himanshu.august.notetaking.Result
import gaur.himanshu.august.notetaking.Status
import gaur.himanshu.august.notetaking.local.models.Note
import gaur.himanshu.august.notetaking.local.ui.home.repository.IHomeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: IHomeRepository) : ViewModel() {


    private val _list = MutableLiveData<Events<Result<List<Note>>>>()
    val list: LiveData<Events<Result<List<Note>>>> = _list

    private val key = MutableLiveData<String>(Constants.EMAIL)

    var data: String = repository.getSaveData(Constants.EMAIL)



    fun insertNote(context: Context, note: Note) = viewModelScope.launch {

        repository.insertNote(context, note)
        getAllNotes()
    }

    fun deleteNote(context: Context, note: Note) = viewModelScope.launch {
        repository.deleteNote(context, note)
        getAllNotes()
    }

    fun updateNote(context: Context, note: Note) = viewModelScope.launch {
        repository.updateNote(context, note)
        getAllNotes()
    }

    fun getAllNotes() = viewModelScope.launch {
        _list.postValue(Events(Result(Status.LOADING, null, null)))
        _list.postValue(Events(Result(Status.SUCCESS, repository.getAllNotes(), null)))
    }

    fun syncDataFromNetwork()=viewModelScope.launch {
        _list.postValue(Events(Result(Status.LOADING, null, null)))
        repository.syncDataFromNetwork()
        _list.postValue(Events(Result(Status.SUCCESS, repository.getAllNotes(), null)))

    }

}