package com.example.readersapp.repository

import com.example.readersapp.network.BooksApi
import javax.inject.Inject

class ListBookRepository @Inject constructor(private val api: BooksApi){

}