package com.example.jiomartclone

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jiomartclone.presentation.navigation.AppNavGraph
import com.example.jiomartclone.presentation.navigation.Routes
import com.example.jiomartclone.presentation.screen.auth.CustomToolbar
import com.example.jiomartclone.presentation.screen.home.DrawerContent
import com.example.jiomartclone.presentation.screen.home.HomeBottomBar
import com.example.jiomartclone.presentation.screen.home.HomeToolbar
import com.example.jiomartclone.presentation.screen.home.SearchBarUi
import com.example.jiomartclone.presentation.screen.hometab.electronics.toColorSafe
import com.example.jiomartclone.ui.theme.JioMartTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            RequestNotificationPermission()

            val navController = rememberNavController()
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route

            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val currentHeaderColor = rememberSaveable { mutableStateOf("#105874") }

            JioMartTheme {

                if (currentRoute == Routes.Splash.route) {

                    App(navController, currentHeaderColor)

                } else {

                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            DrawerContent {
                                scope.launch { drawerState.close() }
                            }
                        }
                    ) {

                        Scaffold(

                            topBar = {
                                if (currentRoute == Routes.Home.route) {

                                    Column(modifier = Modifier.background(color = currentHeaderColor.value.toColorSafe())) {

                                        HomeToolbar(
                                            title = "Home",
                                            color = currentHeaderColor.value,
                                            onBack = { scope.launch { drawerState.open() } },
                                            onLogout = {}
                                        )

                                        SearchBarUi(onProfile = {
                                            navController.navigate(Routes.Profile.route) {
                                                popUpTo(Routes.Home.route) {
                                                    inclusive = false
                                                }
                                            }
                                        }, onSearch = {
                                            navController.navigate(Routes.Search.route) {
                                                popUpTo(Routes.Home.route) {
                                                    inclusive = false
                                                }
                                            }
                                        })


                                    }

                                } else {

                                    CustomToolbar(
                                        showSearch = true,
                                        Modifier,
                                        (currentRoute ?: "").uppercase()
                                    ) {
                                        navController.popBackStack()
                                    }
                                }
                            },

                            // 🔻 Bottom Bar (only home)
                            bottomBar = {
                                if (currentRoute == Routes.Home.route || currentRoute == Routes.Categories.route) {
                                    HomeBottomBar(navController)
                                }
                            }

                        ) { padding ->

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(padding)
                            ) {
                                App(navController, currentHeaderColor)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RequestNotificationPermission() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        val context = LocalContext.current

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                Log.d("Permission", "Granted: $isGranted")
            }
        )

        val isGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        LaunchedEffect(isGranted) {
            if (!isGranted) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}


@Composable
fun App(navController: NavHostController, currentHeaderColor: MutableState<String>) {
    AppNavGraph(navController, currentHeaderColor)
}