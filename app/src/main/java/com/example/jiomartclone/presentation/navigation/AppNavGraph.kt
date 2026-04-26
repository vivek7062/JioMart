package com.example.jiomartclone.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jiomartclone.presentation.screen.auth.ForGotPasswordScreen
import com.example.jiomartclone.presentation.screen.auth.Login
import com.example.jiomartclone.presentation.screen.auth.OtpScreen
import com.example.jiomartclone.presentation.screen.auth.SignupScreen
import com.example.jiomartclone.presentation.screen.categoriestab.CategoriesTabScreen
import com.example.jiomartclone.presentation.screen.category.CategoryScreen
import com.example.jiomartclone.presentation.screen.home.HomeScreen
import com.example.jiomartclone.presentation.screen.offertab.OffersTabScreen
import com.example.jiomartclone.presentation.screen.search.SearchScreen
import com.example.jiomartclone.presentation.screen.splash.SplashScreen
import com.example.jiomartclone.presentation.screen.tweetlist.TweetListByCategory

@Composable
fun AppNavGraph(navController: NavHostController, currentHeaderColor: MutableState<String>) {
    NavHost(navController, Routes.Splash.route) {
        composable(Routes.Splash.route) {
            SplashScreen(navController)
        }
        composable(Routes.Login.route) {
            Login(navController)
        }
        composable(Routes.Signup.route) {
            SignupScreen(navController)
        }
        composable(Routes.Otp.route) {
            val email = it.arguments?.getString("email")?:""
            OtpScreen(navController, email)
        }
        composable(Routes.Forgotpassword.route) {
            val email = it.arguments?.getString("email")?:""
            ForGotPasswordScreen(navController, email)
        }

        composable(Routes.Home.route) {
            HomeScreen(navController,currentHeaderColor = currentHeaderColor)
        }
        composable(Routes.TwwetsListByCategory.route){
            val category = it.arguments?.getString("category")?:""
            TweetListByCategory(category, navController)
        }
        composable(Routes.Search.route) {
            SearchScreen(navController)
        }
        composable(Routes.Profile.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Profile")
            }
        }

        composable(Routes.Category.route) {
            val category = it.arguments?.getString("category")?:""
            CategoryScreen(category = category)
        }

        composable(Routes.Categories.route) {
            CategoriesTabScreen()
        }

        composable(Routes.Offers.route) {
            OffersTabScreen()
        }
    }
}