package com.example.readersapp.screens.update

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.readersapp.components.ReadersAppBar
import com.example.readersapp.data.DataOrException
import com.example.readersapp.model.MBook
import com.example.readersapp.screens.home.HomeScreenViewModel

private const val TAG = "UpdateScreen"

@Composable
fun UpdateScreen(navController: NavHostController, bookItemId: String,
                 viewModel: HomeScreenViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        ReadersAppBar(title = "Update Book",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController){
            navController.popBackStack()
        }
    }) {

        val bookInfo = produceState<DataOrException<List<MBook>, Boolean,
                Exception>>(initialValue = DataOrException(data = emptyList(),
            loading = true, e = Exception(""))){
                    value =  viewModel.data.value
        }.value
        
        Surface(modifier = Modifier
            .padding(top = 3.dp)
            .fillMaxSize()) {
            
            Column(modifier = Modifier
                .padding(top = 3.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
                Log.d(TAG, "UpdateScreen: ${viewModel.data.value.data.toString()}")
                if (bookInfo.loading == true){
                    LinearProgressIndicator()
                    bookInfo.loading = false
                }else{
                    Text(text = viewModel.data.value.data?.get(0)?.title.toString())
                }
            }
            
        }
        
    }
}