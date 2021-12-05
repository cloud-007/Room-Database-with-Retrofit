package com.example.roomdatabasewithretrofit.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomdatabasewithretrofit.Data.UserDatabase
import com.example.roomdatabasewithretrofit.model.Todo
import com.example.roomdatabasewithretrofit.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Todo>>

    private val repository: UserRepository

    init{
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.realAllData
    }

    fun addTodo(todo: Todo){
        viewModelScope.launch (Dispatchers.IO){
            repository.addTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteTodo(todo)
        }
    }

}