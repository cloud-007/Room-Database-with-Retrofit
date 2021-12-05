package com.example.roomdatabasewithretrofit.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.roomdatabasewithretrofit.Data.UserDao
import com.example.roomdatabasewithretrofit.model.Todo

class UserRepository(private val userDao: UserDao) {

    val realAllData: LiveData<List<Todo>> = userDao.readAllData()

    fun addTodo(todo: Todo){
        val f = userDao.addTodo(todo)
        Log.d("inserted", f.toString())
    }

    fun deleteTodo(todo: Todo){
        userDao.deleteTodo(todo)
    }
}