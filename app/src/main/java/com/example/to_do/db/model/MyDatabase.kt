package com.example.to_do.db.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities =[ Task::class], version = 3, exportSchema = false)
abstract class MyDatabase: RoomDatabase (){
    abstract fun getDao():Dao


        companion object {
        private  var myDatabase:MyDatabase ?=null
        fun getdatabase(context: Context):MyDatabase{
            if (myDatabase==null){
                myDatabase= Room.databaseBuilder(context,MyDatabase::class.java,"Task")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return myDatabase!!
        }
        }

}