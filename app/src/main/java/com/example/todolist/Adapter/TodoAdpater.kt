package com.example.todolist.Adapter

import android.icu.number.Notation.simple
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.Todo
import kotlinx.android.synthetic.main.item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TodoAdpater(val list:List<Todo>) :RecyclerView.Adapter<TodoAdpater.TodoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
   return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent ,false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemId(position: Int): Long {
       return list[position].id
    }
    override fun getItemCount(): Int {
       return list.size
    }

    inner class TodoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(todo: Todo) {
            with(itemView){
                    val colors=resources.getIntArray(R.array.random_color)
                    val rColor=colors[Random().nextInt(colors.size)]
                     viewColorTag.setBackgroundColor(rColor)
                     txtShowTitle.text=todo.title
                     txtShowTask.text=todo.description
                     txtShowCategory.text=todo.category
                      updateTime(todo.time)
                      updateDate(todo.date)
            }
        }
        private fun updateTime(time:Long){
            val myf="h:mm a"
            val simpletimeformat=SimpleDateFormat(myf)
            itemView.txtShowTime.text=simpletimeformat.format(Date(time))
        }
        private fun updateDate(time:Long){
            val myf="EEE,d MMM yyyy "
            val simpledateformat=SimpleDateFormat(myf)
            itemView.txtShowDate.text=simpledateformat.format(Date(time))
        }


    }

}