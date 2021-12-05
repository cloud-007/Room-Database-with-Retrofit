package com.example.roomdatabasewithretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.MainThread
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabasewithretrofit.Data.UserDao
import com.example.roomdatabasewithretrofit.adapter.TodoAdapter
import com.example.roomdatabasewithretrofit.api.RetrofitInstance
import com.example.roomdatabasewithretrofit.databinding.ActivityMainBinding
import com.example.roomdatabasewithretrofit.model.Todo
import com.example.roomdatabasewithretrofit.viewModel.UserViewModel
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

    private  lateinit var mViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        setContentView(binding.root)

        val adapter = TodoAdapter()

        val recyclerView = binding.rvToDos
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //userviewModel

        mViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mViewModel.readAllData.observe(this, { user->
            adapter.setData(user)
        })

        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getTodos()
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                for (item in response.body()!!) {
                    val todo = Todo(completed = item.completed, id = item.id,  title = item.title, userId = item.userId)
                    val f = mViewModel.addTodo(todo)
                   // Log.d("inserted", f.toString())
                    Log.d("debug", todo.id.toString() + " "+ todo.title)
                }
            } else {
                Log.e(TAG, "Response not successful")
            }
            binding.progressBar.isVisible = false
        }
    }

}