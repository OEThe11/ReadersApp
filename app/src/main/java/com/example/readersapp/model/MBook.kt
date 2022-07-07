package com.example.readersapp.model

data class MBook(
    //variables in Data Classes NEED to be var for Firebase to work
    var id: String? = null,
    var title: String? = null,
    var authors: String? = null,
    var notes: String? = null
)
