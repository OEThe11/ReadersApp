package com.example.readersapp.repository

import com.example.readersapp.data.Resource
import com.example.readersapp.model.Item
import com.example.readersapp.network.BooksApi
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {

    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            Resource.Loading(data = true)

            val itemList = api.getAllBooks(searchQuery).items
            if(itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        }catch (exception: Exception){
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getBookInfo(bookId: String): Resource<Item>{
        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookId)
        }catch (exception: Exception){
            return Resource.Error(message = "An error occurred ${exception.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }







//    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
//
//    private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()
//
//
//    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>,Boolean,Exception>{
//            try{
//                dataOrException.loading = true
//                dataOrException.data = api.getAllBooks(searchQuery).items
//                if (dataOrException.data!!.isNotEmpty()) dataOrException.loading = false
//
//
//            }catch (e: Exception){
//                dataOrException.e = e
//            }
//        return dataOrException
//    }
//
//    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception>{
//        val response = try {
//            bookInfoDataOrException.loading = true
//            bookInfoDataOrException.data = api.getBookInfo(bookId = bookId)
//            if (bookInfoDataOrException.data.toString().isNotEmpty()) bookInfoDataOrException.loading = false
//            else {}
//        }catch (e: Exception){
//            bookInfoDataOrException.e = e
//        }
//        return bookInfoDataOrException
//    }
}