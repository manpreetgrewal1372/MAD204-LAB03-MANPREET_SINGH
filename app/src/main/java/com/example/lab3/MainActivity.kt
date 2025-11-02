/**
 * Course: MAD204 - Lab 3
 * Student: Manpreet Singh (A00198842)
 * Date: November 2, 2025
 *
 * Description:
 * Persistent Notes App that allows users to add, delete (with undo), and save notes permanently
 * using SharedPreferences and GSON.
 */

package com.example.lab3

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noteInput: EditText
    private lateinit var addButton: Button
    private lateinit var adapter: MyAdapter
    private val notes = mutableListOf<String>()

    /**
     * Called when the activity is created.
     * Initializes UI elements, loads saved notes, and sets up listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        noteInput = findViewById(R.id.noteInput)
        addButton = findViewById(R.id.addButton)

        // Load saved notes before setting up the RecyclerView
        loadNotes()

        // Initialize RecyclerView adapter with delete callback
        adapter = MyAdapter(notes) { position -> deleteNoteWithUndo(position) }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Handle Add Note button click
        addButton.setOnClickListener {
            val note = noteInput.text.toString().trim()

            // Prevent adding empty note
            if (note.isEmpty()) {
                Toast.makeText(this, "Please enter a note.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Add note to the list and notify adapter
            notes.add(note)
            adapter.notifyItemInserted(notes.size - 1)
            noteInput.text.clear()
            Toast.makeText(this, "Note added!", Toast.LENGTH_SHORT).show()

            // Save notes persistently
            saveNotes()
        }
    }

    /**
     * Handles note deletion with UNDO functionality.
     * @param position index of note to delete
     */
    private fun deleteNoteWithUndo(position: Int) {
        val deletedNote = notes[position]
        notes.removeAt(position)
        adapter.notifyItemRemoved(position)
        saveNotes()

        // Snackbar with UNDO action
        Snackbar.make(recyclerView, "Note deleted", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                notes.add(position, deletedNote)
                adapter.notifyItemInserted(position)
                saveNotes()
            }.show()
    }

    /**
     * Saves notes to SharedPreferences using GSON serialization.
     */
    private fun saveNotes() {
        val sharedPref = getSharedPreferences("notes_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val gson = Gson()
        val json = gson.toJson(notes)
        editor.putString("notes_list", json)
        editor.apply()
    }

    /**
     * Loads saved notes from SharedPreferences at startup.
     */
    private fun loadNotes() {
        val sharedPref = getSharedPreferences("notes_pref", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString("notes_list", null)
        val type = object : TypeToken<MutableList<String>>() {}.type
        val loadedNotes: MutableList<String>? = gson.fromJson(json, type)
        if (loadedNotes != null) {
            notes.addAll(loadedNotes)
        }
    }

    /**
     * Ensures notes are saved when app is minimized or closed.
     */
    override fun onPause() {
        super.onPause()
        saveNotes()
    }
}
