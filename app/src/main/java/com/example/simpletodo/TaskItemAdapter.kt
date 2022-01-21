package com.example.simpletodo

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.R
import android.util.Log

import android.view.LayoutInflater
import android.widget.TextView


//bridge that tells the recycler view how to display the data we give it
class TaskItemAdapter(val ListOfItems: List<String>, val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>()
{

    interface OnLongClickListener
    {
        fun onItemLongClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Usually involves inflating a layout from XML and returning the holder
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {

        //get the data model based on position
        val item = ListOfItems.get(position)
        holder.textView.text = item
    }

    override fun getItemCount(): Int
    {
        return ListOfItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        //store references to elements in our layout view
        val textView: TextView
        init
        {
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener()
            {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }

}