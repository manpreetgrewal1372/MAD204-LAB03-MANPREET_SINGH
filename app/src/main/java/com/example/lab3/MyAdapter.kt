/**
 * Course: MAD204 - Lab 3
 * Student: Manpreet Singh (A00198842)
 * Date: November 2, 2025
 *
 * Description:
 * RecyclerView Adapter to bind notes to the list and handle long press events for deletion.
 */

package com.example.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val notes: MutableList<String>,
    private val onLongClick: (Int) -> Unit
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    /** ViewHolder class that binds a single note item to the view */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteText: TextView = itemView.findViewById(R.id.noteText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notes[position]
        holder.noteText.text = note

        // Handle long press event for deletion
        holder.itemView.setOnLongClickListener {
            onLongClick(position)
            true
        }
    }

    override fun getItemCount(): Int = notes.size
}
