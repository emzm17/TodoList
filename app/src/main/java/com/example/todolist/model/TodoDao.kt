package com.example.todolist.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TodoDao {

    @Insert()
    suspend fun insertTask(todo: Todo):Long

    @Query("Select * from Todo where isFinished == 0")
    fun getTask(): LiveData<List<Todo>>

    @Query("Update Todo Set isFinished = 1 where id=:uid")
    fun finishTask(uid:Long)

    @Query("Delete from Todo where id=:uid")
    fun deleteTask(uid:Long)

    @Query("Select * from Todo where isFinished == 1")
    fun getFinishedTask():LiveData<List<Todo>>

    @Query("Delete from Todo where isFinished == 1")
    fun delete_finished_task()

}