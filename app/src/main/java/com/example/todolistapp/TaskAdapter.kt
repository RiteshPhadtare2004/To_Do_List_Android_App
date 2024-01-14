package com.example.todolistapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView

class TaskAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_task, parent, false)
        }

        val taskTextView = listItemView!!.findViewById<TextView>(R.id.textTask)
        val checkboxTask = listItemView.findViewById<CheckBox>(R.id.checkboxTask)

        taskTextView.text = getItem(position)

        // Handle checkbox click
        checkboxTask.setOnCheckedChangeListener { _, isChecked ->
            // Handle task completion or deletion based on isChecked
            if (isChecked) {
                // Delete the task from the database
                val dbHelper = DatabaseHelper(context)
                getItem(position)?.let { dbHelper.deleteTask(it) }

                // Remove the task from the list and notify the adapter
                remove(getItem(position))
                notifyDataSetChanged()
            }
        }

        return listItemView
    }
}
