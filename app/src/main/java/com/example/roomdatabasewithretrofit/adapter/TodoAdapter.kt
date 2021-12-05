package com.example.roomdatabasewithretrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasewithretrofit.R
import com.example.roomdatabasewithretrofit.model.Todo
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    private  var userTodo = emptyList<Todo>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.MyViewHolder {
        return  MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoAdapter.MyViewHolder, position: Int) {

        val currentItem = userTodo[position]
        holder.itemView.tvTitle.text = currentItem.title
        holder.itemView.cvDone.isChecked = currentItem.completed
    }

    fun setData(data: List<Todo>){
        this.userTodo = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return userTodo.size
    }

}