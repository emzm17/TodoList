package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(var title:String,
                var description:String,
                var category:String,
                var date:Long,
                var time:Long,
                var isFinished:Int=0,
                @PrimaryKey(autoGenerate = true)
                var id:Long=0
                )