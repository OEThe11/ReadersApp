package com.example.readersapp.navigation

enum class ReadersScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,//*
    ReadersHomeScreen,
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
            ReadersHomeScreen.name -> ReadersHomeScreen
            SearchScreen.name -> SearchScreen
            DetailScreen.name -> DetailScreen
            UpdateScreen.name -> UpdateScreen
            ReadersStatsScreen.name -> ReadersStatsScreen
            null -> ReadersHomeScreen
            else -> throw IllegalArgumentException("Route $route is not available")
        }
    }

}