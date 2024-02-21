package com.example.rooans.database.`interface`

import com.google.firebase.database.DatabaseReference

interface DbImpl {
    fun get(dataBase: DatabaseReference)
}