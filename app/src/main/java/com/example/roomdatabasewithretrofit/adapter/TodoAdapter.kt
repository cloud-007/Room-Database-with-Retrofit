package com.example.roomdatabasewithretrofit.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasewithretrofit.Data.UserDao
import com.example.roomdatabasewithretrofit.R
import com.example.roomdatabasewithretrofit.model.Todo
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    private var todoList = emptyList<Todo>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: TodoAdapter.MyViewHolder, position: Int) {
        holder.itemView.tvTitle.text = todoList[position].title
        holder.itemView.cvDone.isChecked = todoList[position].completed
        Log.d("find", todoList[position].title)
    }

    override fun getItemCount(): Int {
        Log.d("find", todoList.size.toString())
        return todoList.size
    }

    fun setData(todo: List<Todo>) {
        todoList = todo
        notifyDataSetChanged()
    }

}
