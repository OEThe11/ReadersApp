package com.example.readersapp.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.readersapp.components.InputField
import com.example.readersapp.components.ReadersAppBar
import com.example.readersapp.model.Item
import com.example.readersapp.navigation.ReadersScreens


@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        ReadersAppBar(
            title = "Search Books",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            showProfile = false){
            navController.navigate(ReadersScreens.HomeScreen.name)
        }
    }) {
        Surface() {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ){query ->
                    viewModel.searchBooks(query = query)
                }
                BookList(navController = navController)
            }

        }
    }
}


@Composable
fun BookList(navController: NavController, viewModel: SearchViewModel = hiltViewModel()){


    val listOfBooks = viewModel.list

//        listOf(
//        MBook("dggbr", "Hello, it's me", "Someone", null),
//        MBook("dfhyr", "He, it's me", "Someone", null),
//        MBook("dgregr", "Hello, me", "Someone", null),
//        MBook("dxggw", "Hello, it's me", "Someone", null),
//        MBook("vufhy", "Hello, ite", "Someone", null)
//    )


    LazyColumn(modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)){
         items(items = listOfBooks){ book ->
             ListContainer(book, navController)
         }
    }
}



@Composable
fun ListContainer(book: Item, navController: NavController = NavController(LocalContext.current)) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(8.dp)
            .clickable { }) {
            Row(modifier = Modifier.padding(2.dp),
                horizontalArrangement = Arrangement.Start) {
                Image(painter = rememberImagePainter(data = "http://books.google.com/books/content?id=JGH0DwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"),
                    contentDescription = "Photo of Book",
                modifier = Modifier
//                    .padding(4.dp)
                    .width(100.dp)
                    .height(140.dp))
                Column(modifier = Modifier
                    .padding(start = 5.dp, top = 4.dp)
                    .fillMaxHeight()) {
                    Text(text = book.volumeInfo.title,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h3
                        )
                    Text(text = "Author: ${book.volumeInfo.authors}",
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic
                        )
                    Text(text = "Notes: ${book.volumeInfo.subtitle}",
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Text(text = "[Computers]",
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic
                    )

                }
            }
        }
        

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
){

    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }

    Column() {

        InputField(valueState = searchQueryState, labelId = "Search", enabled = true,
                onAction = KeyboardActions{
                    if (!valid) return@KeyboardActions
                    onSearch(searchQueryState.value.trim())
                    searchQueryState.value = ""
                    keyboardController?.hide()
                }
        )

    }
    
}