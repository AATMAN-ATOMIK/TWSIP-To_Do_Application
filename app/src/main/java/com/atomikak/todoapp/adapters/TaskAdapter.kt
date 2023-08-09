package com.atomikak.todoapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.atomikak.todoapp.R
import com.atomikak.todoapp.helperClass.SqliteHelper
import com.atomikak.todoapp.helperClass.Task

class TaskAdapter(val context: Context, val taskList: ArrayList<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    val sqliteHelper = SqliteHelper(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_task_card, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        when (taskList[position].t_priority.toString()) {
            "Regular" -> {
                holder.task_card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.green50
                    )
                )
                holder.lb_tc_priority.setBackgroundResource(R.drawable.my_priority_background_green)
                holder.lb_tc_priority.setTextColor(ContextCompat.getColor(context, R.color.green))
                holder.tc_edit.setTextColor(ContextCompat.getColor(context, R.color.green))
                holder.tc_ch_complete.buttonTintList = ColorStateList.valueOf(R.color.green)
            }

            "Normal" -> {
                holder.task_card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.blue50
                    )
                )
                holder.lb_tc_priority.setBackgroundResource(R.drawable.my_priority_background_blue)
                holder.lb_tc_priority.setTextColor(ContextCompat.getColor(context, R.color.blue))
                holder.tc_edit.setTextColor(ContextCompat.getColor(context, R.color.blue))
                holder.tc_ch_complete.buttonTintList = ColorStateList.valueOf(R.color.blue)
            }

            "Medium" -> {
                holder.task_card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.yellow50
                    )
                )
                holder.lb_tc_priority.setBackgroundResource(R.drawable.my_priority_background_yellow)
                holder.lb_tc_priority.setTextColor(ContextCompat.getColor(context, R.color.yellow))
                holder.tc_edit.setTextColor(ContextCompat.getColor(context, R.color.yellow))
                holder.tc_ch_complete.buttonTintList = ColorStateList.valueOf(R.color.yellow)
            }

            "High" -> {
                holder.task_card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.red50
                    )
                )
                holder.lb_tc_priority.setBackgroundResource(R.drawable.my_priority_background_red)
                holder.lb_tc_priority.setTextColor(ContextCompat.getColor(context, R.color.red50))
                holder.tc_edit.setTextColor(ContextCompat.getColor(context, R.color.red50))
                holder.tc_ch_complete.buttonTintList = ColorStateList.valueOf(R.color.red50)
            }
        }
        holder.tc_lb_taskTitle.text = taskList[position].t_title.toString()
        holder.tc_lb_taskDes.text = taskList[position].t_des.toString()
        holder.tc_lb_taskDate.text =
            "Date - ${taskList[position].t_date.toString()}, TIME - ${taskList[position].t_time.toString()}"
        holder.lb_tc_priority.text = taskList[position].t_priority.toString()
        holder.tc_lb_taskStatus.text = taskList[position].t_status.toString()

        holder.tc_ch_complete.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (taskList[position].t_status == "Pending") {
                    viewAlert("Pending",position)
                } else {
                    viewAlert("Complete",position)
                }
            }
        }
    }

    private fun viewAlert(s: String,position: Int) {
        if (s == "Pending") {
            val alertDialog = AlertDialog.Builder(context)
                .setNegativeButton("Complete And Delete"
                ) { _, _ -> taskList[position].t_id?.let { sqliteHelper.deleteTask(it) } }
                .setPositiveButton("Complete"
                ) { _, _ -> taskList[position].t_id?.let { sqliteHelper.completeTask(it) } }
                .create()
        } else {
            val alertDialog = AlertDialog.Builder(context)
                .setNegativeButton("Cancel"
                ) { dialog, _ -> dialog!!.dismiss() }
                .setPositiveButton("Delete"
                ) { _, _ -> taskList[position].t_id?.let { sqliteHelper.deleteTask(it) } }
                .create()
        }
        notifyDataSetChanged()
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tc_lb_taskTitle: TextView = itemView.findViewById(R.id.tc_lb_taskTitle)
        val tc_lb_taskDes: TextView = itemView.findViewById(R.id.tc_lb_taskDes)
        val tc_lb_taskDate: TextView = itemView.findViewById(R.id.tc_lb_taskDate)
        val tc_ch_complete: CheckBox = itemView.findViewById(R.id.tc_ch_complete)
        val lb_tc_priority: TextView = itemView.findViewById(R.id.lb_tc_priority)
        val tc_lb_taskStatus: TextView = itemView.findViewById(R.id.tc_lb_taskStatus)
        val tc_edit: TextView = itemView.findViewById(R.id.tc_edit)
        val task_card: CardView = itemView.findViewById(R.id.task_card)
    }
}
