package com.example.to_do.home.addtask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.to_do.databinding.BottomsheetAddtaskBinding
import com.example.to_do.db.model.MyDatabase
import com.example.to_do.db.model.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class AddTaskBottomSheet : BottomSheetDialogFragment() {

    var cuurentdate = Calendar.getInstance()
    lateinit var viewBinding: BottomsheetAddtaskBinding

    init {
        cuurentdate.set(Calendar.HOUR, 0)
        cuurentdate.set(Calendar.MINUTE, 0)
        cuurentdate.set(Calendar.MILLISECOND, 0)
        cuurentdate.set(Calendar.SECOND, 0)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = BottomsheetAddtaskBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDate()
        viewBinding.date.setOnClickListener {
            showdatepicker()
            setDate()
        }
        viewBinding.submitBtn.setOnClickListener {
            isDateValid()
            if (isDateValid()) showAlertDailog()

        }

    }

    private fun showAlertDailog() {
        val alertDialog = AlertDialog.Builder(context).setMessage("Are you sure to insert the task")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                insertTask()
                dialogInterface.dismiss()
                dismiss()
            }).setCancelable(false);
        alertDialog.show()
    }

    var onDismissLisenter: OnDismissLisenter? = null

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissLisenter?.ondismiss()

    }

    private fun insertTask() {

        var title = viewBinding.tasktitle.editText?.text.toString()
        var des = viewBinding.taskdescription.editText?.text.toString()

        activity?.let {
            MyDatabase.getdatabase(it)
                .getDao().inserTask(
                    task = Task(
                        title = title,
                        description = des,
                        date = cuurentdate.timeInMillis
                    )
                )
        }
    }

    private fun setDate() {
        viewBinding.taskDateText.text = "" + cuurentdate.get(Calendar.DAY_OF_MONTH) + "/" + (
                cuurentdate.get(Calendar.MONTH) + 1) + "/" + cuurentdate.get(Calendar.YEAR)
    }

    private fun showdatepicker() {
        activity?.let {
            DatePickerDialog(
                it, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->

                    cuurentdate.set(Calendar.YEAR, year)
                    cuurentdate.set(Calendar.MONTH, month)
                    cuurentdate.set(Calendar.DAY_OF_MONTH, day)
                    setDate()
                }, cuurentdate.get(Calendar.YEAR), cuurentdate.get(Calendar.MONTH),
                cuurentdate.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }

    }

    private fun isDateValid(): Boolean {
        var isValid = true

        if (viewBinding.tasktitle.editText?.text.toString().isBlank()) {
            viewBinding.tasktitle.error = "Please Enter Title"
            isValid = false
        } else viewBinding.tasktitle.error = null

        if (viewBinding.taskdescription.editText?.text.toString().isBlank()) {
            viewBinding.taskdescription.error = "Please Enter Your Description"
            isValid = false
        } else viewBinding.taskdescription.error = null

        if (viewBinding.taskDateText.text.isNullOrBlank()) {
            viewBinding.taskDateText.error = "Please Select Date"
            isValid = false
        } else viewBinding.taskDateText.error = null

        return isValid

    }
}