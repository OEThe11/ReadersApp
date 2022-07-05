package com.example.readersapp.navigation

enum class ReadersScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,//*
    HomeScreen,
    SearchScreen,
    DetailScreen,
    UpdateScreen,
    ReadersStatsScreen;

    companion object{
        fun fromRoute(route: String?): ReadersScreens
        = when(route?.substringBefore("/")){
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            HomeScreen.name -> HomeScreen
            SearchScreen.name -> SearchScreen
            DetailScreen.name -> DetailScreen
            UpdateScreen.name -> UpdateScreen
            ReadersStatsScreen.name -> ReadersStatsScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not available")
        }
    }

}