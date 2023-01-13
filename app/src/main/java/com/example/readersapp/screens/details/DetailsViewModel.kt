package com.example.readersapp.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readersapp.data.Resource
import com.example.readersapp.model.Item
import com.example.readersapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository)
    : ViewModel(){
        suspend fun getBookInfo(bookId: String): Resource<Item>{
            return repository.getBookInfo(bookId)
        }
}