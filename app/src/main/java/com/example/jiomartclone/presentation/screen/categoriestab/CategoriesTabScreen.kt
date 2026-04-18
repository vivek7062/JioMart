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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.jiomartclone.presentation.components.ShowAppLoader
import com.example.jiomartclone.presentation.components.ShowErrorScreen
import kotlinx.coroutines.launch

@Composable
fun CategoriesTabScreen(
    modifier: Modifier = Modifier,
    categoriesTabViewModel: CategoriesTabViewModel = hiltViewModel(),
) {
    val state by categoriesTabViewModel.state.collectAsStateWithLifecycle()
    val isLoading = state.categoriesUiState.isLoading
    val errorMessage = state.categoriesUiState.error
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        categoriesTabViewModel.onIntent(CategoriesIntent.LoadData)
    }
    Box(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(.18f)
                    .fillMaxHeight()
                    .padding(top = 8.dp)
                    .background(Color.White)
            ) {
                state.categoriesUiState.data?.data?.leftMenu?.let {
                    state.selectedMenu?.let { selected ->
                        ShowLeftMenu(
                            selectMenu = selected, menu = it
                        ) { menu ->
                            categoriesTabViewModel.selectMenu(menu)
                            scope.launch {
                                listState.scrollToItem(0)
                            }
                        }
                    }
                }
            }
            val selectedMenu = state.selectedMenu
            val categories = state.categoriesUiState.data?.data?.categories.orEmpty()

            val filtered = categories.filter { it.menuId == selectedMenu }

            Box(
                modifier = Modifier
                    .weight(.82f)
                    .fillMaxHeight()
            ) {
                if (selectedMenu != null && filtered.isNotEmpty()) {
                    CategoryList(listState, filtered)
                }
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
fun ShowLeftMenu(
    selectMenu: String,
    menu: List<LeftMenu>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(items = menu) { menuItem ->
            LeftMenuItem(selectMenu = selectMenu, menuItem = menuItem) {
                onClick(it)
            }
        }
    }
}

@Composable
fun LeftMenuItem(
    selectMenu: String,
    menuItem: LeftMenu,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = modifier.clickable {
            onClick(menuItem.id)
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(4.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        if (selectMenu == menuItem.id) Color(0xFF105874)
                        else Color.Transparent
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 4.dp)
                    .background(
                        color = if (selectMenu == menuItem.id) MaterialTheme.colorScheme.background else Color(
                            0xffffffff
                        ), shape = RoundedCornerShape(topStart = 36.dp, bottomStart = 36.dp)
                    ), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(menuItem.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(30.dp)
                        .height(40.dp)

                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = menuItem.title,
            color = if (selectMenu == menuItem.id) Color(0xFF105874) else Color(0xff3E3E3E),
            modifier = Modifier
                .padding(start = 4.dp)
                .fillMaxWidth(),
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CategoryList(
    listState: LazyListState,
    category: List<Category>,
    modifier: Modifier = Modifier,
) {
    if (category.isEmpty()) return
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxHeight(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        category.first().sections.forEach { categoryItem ->
            item(key = categoryItem.title) {
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

            items(items = categoryItem.items.chunked(4), key = { row -> row.first().id } ) { categoryList ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categoryList.forEach { item ->
                        Box(modifier = Modifier.weight(1f)) {
                            CategoryItemScreen(categoryItem = item)
                        }
                    }
                    repeat(4 - categoryList.size) {
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
        modifier = modifier.width(80.dp), horizontalAlignment = Alignment.CenterHorizontally
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
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current).data(categoryItem.image)
                        .crossfade(true).size(100).build(),
                ),
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
                lineHeight = 16.sp, platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}