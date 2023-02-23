package com.example.to_do.home.list

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do.R
import com.example.to_do.databinding.ItemTaskBinding
import com.example.to_do.db.model.Task

class TasksAdapter(var list: List<Task>? = null) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    var onItemClicked:OnItemClickedListner?=null
    var onDeletedListner:OnDeletedListner?=null

    class TaskViewHolder(val viewBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val viewBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(viewBinding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val listitem = list!![position]
        holder.viewBinding.title.text = listitem?.title.toString()
        holder.viewBinding.desc.text = listitem?.description.toString()

        if(onItemClicked!=null){
         holder.viewBinding.card.setOnLongClickListener {
             onItemClicked?.Onclick(list!![position])
             true
         }}
        if (listitem.isDone == true){
           holder.viewBinding.doneBtn.setBackgroundColor(Color.GREEN)
            holder.viewBinding.title.setTextColor(Color.GREEN)
            holder.viewBinding.doneBtn.setImageResource(R.drawable.ic_check_24)
            holder.viewBinding.startLine.setBackgroundColor(Color.GREEN)
        }

        holder.viewBinding.delete.setOnClickListener{
            onDeletedListner?.ondelete(list!![position],position)

           holder.viewBinding.swipe.close()
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun setData(newList :List<Task>){
        list = newList
        notifyDataSetChanged()
    }}
   public interface OnItemClickedListner {
        fun Onclick(task:Task)
    }

    public interface OnDeletedListner {
        fun ondelete(task:Task ,Position:Int)
    }

