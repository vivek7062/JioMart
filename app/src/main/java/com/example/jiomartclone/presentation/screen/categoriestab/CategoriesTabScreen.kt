package com.example.jiomartclone.presentation.screen.categoriestab

import Category
import CategoryItem
import LeftMenu
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.jiomartclone.presentation.components.ShowAppLoader
import com.example.jiomartclone.presentation.components.ShowErrorScreen

@Composable
fun CategoriesTabScreen(
    modifier: Modifier = Modifier,
    categoriesTabViewModel: CategoriesTabViewModel = hiltViewModel(),
) {
    val state by categoriesTabViewModel.state.collectAsStateWithLifecycle()
    val isLoading = state.categoriesUiState.isLoading
    val errorMessage = state.categoriesUiState.error
    val selectedMenu = rememberSaveable { mutableStateOf("") }
    state.categoriesUiState.data?.data?.leftMenu?.let {
        selectedMenu.value = it[0].id
    }
    Box(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(.2f)
                    .fillMaxHeight()
            ) {
                state.categoriesUiState.data?.data?.leftMenu?.let {
                    LeftMenu(menu = it){ menu->
                        selectedMenu.value = menu
                    }
                }
               }
            state.categoriesUiState.data?.data?.categories?.let {categories ->
                Box(
                    modifier = Modifier
                        .weight(.8f)
                        .fillMaxHeight()
                ) { CategoryList(categories.filter { it.menuId == selectedMenu.value }) }
            }

        }
        if (isLoading) {
            ShowAppLoader()
        }
        if (!isLoading && errorMessage != null) {
            ShowErrorScreen(message = errorMessage) {
                categoriesTabViewModel.onIntent(CategoriesIntent.Retry)
            }
        }
    }
}

@Composable
fun LeftMenu(menu: List<LeftMenu>, modifier: Modifier = Modifier, onClick: (String)-> Unit) {
    LazyColumn(modifier = modifier) {
        items(items = menu) { menuItem ->
            LeftMenuItem(menuItem = menuItem, onClick = onClick)
        }
    }
}

@Composable
fun LeftMenuItem(menuItem: LeftMenu, modifier: Modifier = Modifier, onClick: (String)-> Unit) {
    Text(text = menuItem.title, modifier = modifier.padding(vertical = 16.dp).clickable{
        onClick(menuItem.id)
    })
}

@Composable
fun CategoryList(category: List<Category>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxHeight()) {
        category[0].sections.forEach { categoryItem ->
            item {
                Text(
                    text = categoryItem.title,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
            }
            items(items = categoryItem.items.chunked(4)) { categoryList ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categoryList.forEach { item->
                        Box(modifier = Modifier.weight(1f)) {
                            CategoryItemScreen(categoryItem = item)
                        }
                    }
                    repeat(4-categoryList.size){
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

        }
    }
}

@Composable
fun CategoryItemScreen(
    modifier: Modifier = Modifier,
    categoryItem: CategoryItem,
) {
    Column(
        modifier = modifier.width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(categoryItem.image),
                contentDescription = categoryItem.id,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = categoryItem.title,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.titleSmall.copy(
                lineHeight = 16.sp,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}