package com.example.readersapp.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readersapp.data.DataOrException
import com.example.readersapp.data.Resource
import com.example.readersapp.model.Item
import com.example.readersapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: BookRepository): ViewModel(){

    var list: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)

    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("android")
    }

     fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()){
                return@launch
            }
            try {
                when(val response = repository.getBooks(query)){
                    is Resource.Success -> {
                        list = response.data!!
                        if (list.isNotEmpty()) isLoading = false
                    }
                    is Resource.Error -> {
                        isLoading = false
                        Log.e("Network", "searchBooks: Failed Getting books", )
                    }
                    else -> {isLoading = false}
                }
            }catch (exception: Exception){
                isLoading = false
                Log.d("Network", "searchBooks: ${exception.message.toString()}")
            }
        }

     }


//     val listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>> =
//        mutableStateOf(DataOrException(null, true, Exception("")))
//
//    init {
//        searchBooks("android")
//    }
//
//     fun searchBooks(query: String) {
//        viewModelScope.launch {
//            if (query.isEmpty()){
//                return@launch
//            }
//            listOfBooks.value.loading = true
//            listOfBooks.value = repository.getBooks(query)
//            Log.d("SVM", "searchBooks: ${listOfBooks.value.data.toString()}")
//            if (listOfBooks.value.data.toString().isNotEmpty()) listOfBooks.value.loading = false
//        }
//    }


}