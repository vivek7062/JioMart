package com.example.jiomartclone.presentation.screen.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jiomartclone.presentation.navigation.Routes

@Composable
fun HomeBottomBar(navController: NavHostController) {

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp)
                .background(Color.White)
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomBarItem(
                selected = currentRoute == Routes.Home.route,
                icon = Icons.Filled.Home,
                label = "Home"
            ) {
                navController.navigate(Routes.Home.route) {
                    popUpTo(0)
                }
            }

            BottomBarItem(
                selected = currentRoute == Routes.Categories.route,
                icon = Icons.Filled.GridView,
                label = "Categories"
            ) {
                navController.navigate(Routes.Categories.route)
            }

            BottomBarItem(
                selected = currentRoute == Routes.Profile.route,
                icon = Icons.Filled.ShoppingCart,
                label = "Cart"
            ) {
                navController.navigate(Routes.Profile.route)
            }

            BottomBarItem(
                selected = currentRoute == Routes.Offers.route,
                icon = Icons.Filled.LocalOffer,
                label = "Offers"
            ) {
                navController.navigate(Routes.Offers.route)
            }
        }
    }
}

@Composable
fun BottomBarItem(
    selected: Boolean,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    val iconColor by animateColorAsState(
        targetValue = if (selected) Color(0xFF2E7D32) else Color.Gray,
        label = "iconColor"
    )
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(start = 12.dp, end = 12.dp, bottom = 16.dp, top = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(
                    if (selected) Color(0xFFE8F5E9) else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor
            )
        }

        Text(
            text = label,
            fontSize = 11.sp,
            color = iconColor
        )
    }
}