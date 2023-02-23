package com.example.to_do.home.list

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.to_do.databinding.FragmentListBinding
import com.example.to_do.db.model.MyDatabase
import com.example.to_do.db.model.Task
import com.example.to_do.home.edit.EditTaskActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.*

class ListFragment : Fragment() {
    lateinit var viewbinding: FragmentListBinding
    var list: List<Task>? = null
    var tasksAdapter: TasksAdapter = TasksAdapter(null)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewbinding = FragmentListBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    val currentdate = Calendar.getInstance();

    init {
        currentdate.set(Calendar.MINUTE, 0)
        currentdate.set(Calendar.SECOND, 0)
        currentdate.set(Calendar.MILLISECOND, 0)
        currentdate.set(Calendar.HOUR, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewbinding.tasksRecycler.adapter = tasksAdapter
        viewbinding.calendarView.selectedDate = CalendarDay.today()
        viewbinding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                currentdate.set(Calendar.YEAR, date.year)
                currentdate.set(Calendar.MONTH, date.month - 1)
                currentdate.set(Calendar.DAY_OF_MONTH, date.day)
                loadTasksByDaylist()
            }
        }


        tasksAdapter.onItemClicked = object : OnItemClickedListner {
            override fun Onclick(task: Task) {
                showEditOrIsdoneAlert(task)
            }

        }
        tasksAdapter.onDeletedListner = object : OnDeletedListner {
            override fun ondelete(task: Task, Position: Int) {
                Alertdeletitem(task)
            }
        }
    }

    private fun showEditOrIsdoneAlert(task: Task) {
        val alertDialog = AlertDialog.Builder(context).setMessage("What do you want to do ?")
            .setNegativeButton("Update Task", DialogInterface.OnClickListener { dialog, i ->
                startEditTaskActivity(task)
                dialog.dismiss()
            }).setPositiveButton("Make Done", DialogInterface.OnClickListener { dialog, i ->
                makeTaskDone(task)
                tasksAdapter.notifyDataSetChanged()
                dialog.dismiss()
            });alertDialog.show()
    }

    private fun makeTaskDone(task: Task) {
        task.isDone = true
        activity?.let { MyDatabase.getdatabase(it).getDao().updateTask(task) }
    }

    private fun startEditTaskActivity(task: Task) {
        var intent = Intent(requireContext(), EditTaskActivity::class.java)
        intent.putExtra("Task", task)
        startActivity(intent)
    }

    private fun deletitem(task: Task) {
        activity?.let { MyDatabase.getdatabase(it).getDao().deleteTask(task) }
    }

    private fun Alertdeletitem(task: Task) {
        val alertDialog =
            AlertDialog.Builder(context).setMessage("Are you sur to delete this task ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, i ->
                    deletitem(task)
                    loadTasksByDaylist()
                    dialog.dismiss()
                }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, i ->
                    dialog.dismiss()
                });alertDialog.show()
    }


    fun loadTasksByDaylist() {
        if (!isResumed) {
            return
        }
        val tasks = MyDatabase.getdatabase(requireActivity()).getDao()
            .getAllTasksByDay(currentdate.timeInMillis)
        tasksAdapter.setData(tasks)
        tasksAdapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        loadTasksByDaylist()
    }
}