package com.example.to_do.db.model

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao  {
    @Insert
    fun  inserTask(task:Task);
    @Delete
    fun deleteTask(task:Task)
    @Update
    fun updateTask(task:Task)
    @Query("select * from Task where date =:date")
    fun getAllTasksByDay(date :Long):List<Task>
}