package com.example.readersapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.readersapp.screens.ReadersSplashScreen
import com.example.readersapp.screens.details.BookDetailScreen
import com.example.readersapp.screens.home.HomeScreen
import com.example.readersapp.screens.login.MainLogin
import com.example.readersapp.screens.search.SearchScreen
import com.example.readersapp.screens.search.SearchViewModel
import com.example.readersapp.screens.stats.ReadersStatsScreen
import com.example.readersapp.screens.update.UpdateScreen

@Composable
fun ReadersNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = ReadersScreens.SplashScreen.name){
        composable(ReadersScreens.SplashScreen.name){
            ReadersSplashScreen(navController = navController)
        }

        composable(ReadersScreens.HomeScreen.name){
            HomeScreen(navController = navController)
        }

        composable(ReadersScreens.LoginScreen.name){
            MainLogin(navController = navController)
        }

        composable(ReadersScreens.SearchScreen.name){
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }

        val detailsName = ReadersScreens.DetailScreen.name
        composable("$detailsName/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailScreen(navController = navController, bookId = it.toString())
            }
        }

        composable(ReadersScreens.UpdateScreen.name){
            UpdateScreen(navController = navController)
        }

        composable(ReadersScreens.ReadersStatsScreen.name){
            ReadersStatsScreen(navController = navController)
        }
    }
}