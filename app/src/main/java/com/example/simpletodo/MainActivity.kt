package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity()
{

    var ListOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. remove the item from the list
                ListOfTasks.removeAt(position)
                // 2. notify the adapter that our dta set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        loadItems()

        //look up recycler view in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //create adapter passing in the sample user data
        adapter = TaskItemAdapter(ListOfTasks, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up the button and input field, so that the user can enter a task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        findViewById<Button>(R.id.button).setOnClickListener()
        {
            //1. grab the text the user has inputted into @id/addTaskField
            val userInputtedtask = inputTextField.text.toString()

            //2. Add the string to our list of tasks: ListOfTasks
            ListOfTasks.add(userInputtedtask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(ListOfTasks.size - 1)

            //3. Reset text field
            inputTextField.setText("")

            saveItems()
        }

    }

    //save the data that the user has inputted
    //save data by writing and reading from a file

    //get the file we need
    fun getDataFile() : File
    {
        //every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    //load the items by reading every line in the data file
    fun loadItems()
    {
        try
        {
            ListOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch(ioException: IOException)
        {
            ioException.printStackTrace()
        }
    }

    //save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), ListOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}