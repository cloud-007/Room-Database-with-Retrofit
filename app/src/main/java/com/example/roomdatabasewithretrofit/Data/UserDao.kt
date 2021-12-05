package com.example.roomdatabasewithretrofit.Data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomdatabasewithretrofit.model.Todo

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTodo(todo: Todo): Long

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Todo>>
}