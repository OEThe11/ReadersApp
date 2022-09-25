package com.example.readersapp.screens.home

import android.icu.text.CaseMap
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.readersapp.R
import com.example.readersapp.components.*
import com.example.readersapp.model.MBook
import com.example.readersapp.navigation.ReadersScreens
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

@Preview
@Composable
fun HomeScreen(navController: NavController = NavController(LocalContext.current)) {
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
                HomeContent(navController = navController)
            }

        }
}

@Composable
fun HomeContent(navController: NavController) {

    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty())
        email.split("@")[0]
                        else "Not Available"

    val listOfBooks = listOf(
        MBook("dggbr", "Hello, it's me", "Someone", null),
        MBook("dfhyr", "He, it's me", "Someone", null),
        MBook("dgregr", "Hello, me", "Someone", null),
        MBook("dxggw", "Hello, it's me", "Someone", null),
        MBook("vufhy", "Hello, ite", "Someone", null)
    )

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

        ReadingRightNowArea(books = listOf(), navController = navController)
        
        TitleSection(label = "Reading List")

        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    HorizontalScrollableComponent(listOfBooks){
        Log.d("TAG", "BookListArea: $it")
        //Todo: nav to details
    }
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>, onCardPressed: (String) -> Unit) {

    val scrollState = rememberScrollState()

    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollState)) {

        for (book in listOfBooks){
            ListCard(book){
                onCardPressed(it)

            }
        }
    }

}


@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController){
    ListCard()

}










