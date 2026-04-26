package com.example.jiomartclone.presentation.navigation

sealed class Routes(val route : String) {
    object Splash : Routes("splash")
    object Login : Routes("login")
    object Signup : Routes("signup")
    object Otp : Routes("otp/{email}"){
        fun createRoutes(email : String) = "otp/$email"
    }
    object Forgotpassword : Routes("forgotpassword/{email}"){
        fun createRoutes(email: String) = "forgotpassword/$email"
    }
    object TwwetsListByCategory : Routes("tweetlist/{category}"){
        fun createRoutes(category: String) = "tweetlist/$category"
    }
    object Home : Routes("home")

    object Search : Routes("Search")
    object Profile : Routes("profile")

    object Category : Routes("category/{category}"){
        fun createRoutes(category: String) = "category/$category"
    }

    object Categories : Routes("categories")
    object Offers : Routes("offers")
}