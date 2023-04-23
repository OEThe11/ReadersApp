package com.example.readersapp.screens.home

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readersapp.components.*
import com.example.readersapp.model.MBook
import com.example.readersapp.navigation.ReadersScreens
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "HomeScreen"

@Preview
@Composable
fun HomeScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
        Scaffold(topBar = {
            ReadersAppBar(title = "Readers Unite", navController = navController)
        },
            floatingActionButton = {
                FABContent{
                    navController.navigate(ReadersScreens.SearchScreen.name)
                }
            }) {
            //content
            Surface(modifier = Modifier.fillMaxSize()) {
                //home Content
                HomeContent(navController = navController, viewModel = viewModel)
            }

        }
}

@Composable
fun HomeContent(navController: NavController, viewModel: HomeScreenViewModel) {
    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()){
        listOfBooks = viewModel.data.value.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }

        Log.d(TAG, "HomeContent: $listOfBooks")
    }


    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty())
        email.split("@")[0]
                        else "Not Available"

//    val listOfBooks = listOf(
//        MBook("dggbr", "Hello, it's me", "Someone", null),
//        MBook("dfhyr", "He, it's me", "Someone", null),
//        MBook("dgregr", "Hello, me", "Someone", null),
//        MBook("dxggw", "Hello, it's me", "Someone", null),
//        MBook("vufhy", "Hello, ite", "Someone", null)
//    )

    Column(modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "You Are Reading \n Some New Material I See")
            Spacer(modifier = Modifier.requiredWidth(120.dp))
            Column (verticalArrangement = Arrangement.Center){
                Icon(imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReadersScreens.ReadersStatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant)
                Text(text = currentUserName,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
                Divider()

            }
        }

        ReadingRightNowArea(listOfBooks = listOfBooks, navController = navController)
        
        TitleSection(label = "Reading List")

        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {

    val addedBooks = listOfBooks.filter { mBook ->
        mBook.startedReading == null && mBook.finishedReading == null
    }

    HorizontalScrollableComponent(listOfBooks){
      navController.navigate(ReadersScreens.UpdateScreen.name + "/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>,
                                  viewModel: HomeScreenViewModel = hiltViewModel(),
                                  onCardPressed: (String) -> Unit) {

    val scrollState = rememberScrollState()

    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollState)) {
        if (viewModel.data.value.loading == true){
            LinearProgressIndicator()
        }
        else{
            if (listOfBooks.isNullOrEmpty()){
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(text = "No books found. Add a Book",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp)
                        )
                }
            }
            else{
                for (book in listOfBooks){
                    ListCard(book){
                        onCardPressed(book.googleBookId.toString())

                    }
                }
            }
        }
    }

}


@Composable
fun ReadingRightNowArea(listOfBooks: List<MBook>, navController: NavController){

    
    //Filter books by reading now
    val readingNowList = listOfBooks.filter { mBook ->
        mBook.startedReading != null && mBook.finishedReading == null
    }

    HorizontalScrollableComponent(readingNowList){
        Log.d("TAG", "BoolListArea: $it")
        navController.navigate(ReadersScreens.UpdateScreen.name + "/$it")
    }

}










