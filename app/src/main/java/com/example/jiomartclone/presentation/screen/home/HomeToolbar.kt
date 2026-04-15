package com.example.jiomartclone.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jiomartclone.presentation.screen.hometab.electronics.toColorSafe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeToolbar(
    title: String,
    color : String,
    onBack: () -> Unit,
    onLogout: () -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(title, fontWeight = FontWeight.Bold)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color.toColorSafe(),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),

        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.Menu, contentDescription = "Menu") // 🍔
            }
        },

        actions = {


            // ⋮ More menu
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            expanded = false
                            onLogout()
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun SearchBarUi(modifier: Modifier = Modifier,onProfile: () -> Unit, onSearch: () -> Unit) {
    Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(64.dp))
                .background(Color.White)
                .clickable {
                    onSearch.invoke()
                }
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Search, contentDescription = "Search")
                Text(
                    text = "Search in Quick",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = modifier.padding(start = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {
                    onProfile.invoke()
                }, contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color.Gray
            )
        }
    }
}