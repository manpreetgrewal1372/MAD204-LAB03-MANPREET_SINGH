# MAD204-LAB03-MANPREET_SINGH
MAD204 Lab 3 - Persistent Notes App  An Android app that allows users to add, view, and delete notes using a RecyclerView. Notes are saved persistently using SharedPreferences and GSON, with a Snackbar undo feature for deletions. Includes full documentation, proper GitHub workflow, and user input validation.

# MAD204 - Lab 3: Persistent Notes App

**Student:** Manpreet Singh  
**Student ID:** A00198842  
**Date:** November 2, 2025  

---

## Overview
This Android app allows users to create, view, and delete notes using a RecyclerView.  
All notes are saved **persistently** using SharedPreferences and GSON, ensuring the list remains even after the app is closed.  
The app also provides a **Snackbar “UNDO” feature** for accidental deletions.

This project is part of the **MAD204 - Java Development for Mobile Applications** course, Lab 3.

---

## Features
- **Add Notes** dynamically via EditText and Button.  
- **Delete Notes** with long press and optional undo using Snackbar.  
- **Persistent Storage** using SharedPreferences + GSON.  
- **Dynamic RecyclerView** to display all notes in a scrolling list.  
- **Input Validation**: prevents empty notes from being added.  
- **Notch Support**: UI respects the device status bar and display cutouts.  
- **Full Documentation**: file headers, method/class Javadocs, and inline comments for clarity.
