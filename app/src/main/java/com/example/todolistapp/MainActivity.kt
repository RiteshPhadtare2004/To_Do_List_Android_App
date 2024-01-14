package com.example.todolistapp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var taskEditText: EditText
    private lateinit var addButton: Button
    private lateinit var taskListView: ListView
    private lateinit var tasks: ArrayList<String>
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskEditText = findViewById(R.id.taskEditText)
        addButton = findViewById(R.id.addButton)
        taskListView = findViewById(R.id.taskListView)
        tasks = ArrayList()

        adapter = TaskAdapter(this, R.layout.list_item_task, tasks)
        taskListView.adapter = adapter

        // Retrieve tasks from the database and update the tasks list
        tasks.addAll(DatabaseHelper(this).getAllTasks())
        adapter.notifyDataSetChanged()

        addButton.setOnClickListener {
            val task = taskEditText.text.toString()
            if (task.isNotEmpty()) {
                // Insert the new task into the database
                val dbHelper = DatabaseHelper(this)
                dbHelper.insertTask(task)

                // Update the tasks list and notify the adapter
                tasks.clear()
                tasks.addAll(dbHelper.getAllTasks())
                adapter.notifyDataSetChanged()

                taskEditText.text.clear()
            }
        }
    }
}
