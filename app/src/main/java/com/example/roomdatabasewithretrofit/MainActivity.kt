package com.example.roomdatabasewithretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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

        var adapter = TodoAdapter()

        val recyclewView = binding.rvToDos
        recyclewView.adapter = adapter

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
                //Log.d("debug", "Done")
                adapter = TodoAdapter()
                binding.rvToDos.adapter = adapter
            } else {
                Log.e(TAG, "Response not successful")
            }
            binding.progressBar.isVisible = false
        }
    }

    private fun setUpRecyclerView() = binding.rvToDos.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

}