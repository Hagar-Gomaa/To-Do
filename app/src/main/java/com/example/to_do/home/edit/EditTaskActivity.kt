package com.example.to_do.home.edit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.to_do.MainActivity
import com.example.to_do.R
import com.example.to_do.databinding.ActivityEditTaskBinding
import com.example.to_do.db.model.MyDatabase
import com.example.to_do.db.model.Task
import java.text.SimpleDateFormat
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityEditTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     viewBinding =ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val task = (intent.getSerializableExtra("Task") as?Task)!!
          showData(task)

        viewBinding.saveChanges.setOnClickListener {
            isDateValid()
            if (isDateValid()){
            updateTask(task)
        }}
    }

    private fun updateTask(task: Task) {
        if (isDateValid()){
            task.title = viewBinding.tasktitle.text.toString()
            task.description = viewBinding.taskdesc.text.toString()
        }
        MyDatabase.getdatabase(this).getDao().updateTask(task)
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showData(task: Task) {
        viewBinding.tasktitle.setText(task.title)
        viewBinding.taskdesc.setText(task.description)
        var date =convertLongToTime(task.date)
        viewBinding.taskDateText.setText(date)

    }

    @SuppressLint("SimpleDateFormat")
    private fun convertLongToTime(date: Long?): String {
        val date =Date(date!!)
        val format =SimpleDateFormat("yyyy.MM.dd HH:MM")
        return format.format(date)
    }

    private fun isDateValid(): Boolean {
        var isValid = true

        if (viewBinding.tasktitle.text.toString().isBlank()) {
            viewBinding.tasktitle.error = "Please Enter Title"
            isValid = false
        } else viewBinding.tasktitle.error = null

        if (viewBinding.taskdesc.text.toString().isBlank()) {
            viewBinding.taskdesc.error = "Please Enter Your Description"
            isValid = false
        } else viewBinding.taskdesc.error = null

        if (viewBinding.taskDateText.text.isNullOrBlank()) {
            viewBinding.taskDateText.error = "Please Select Date"
            isValid = false
        } else viewBinding.taskDateText.error = null

        return isValid

    }
}