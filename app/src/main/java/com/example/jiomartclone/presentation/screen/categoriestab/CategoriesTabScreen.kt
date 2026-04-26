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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.jiomartclone.presentation.components.ShowErrorScreen
import com.example.jiomartclone.presentation.screen.home.ShimmerBox
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

    LaunchedEffect(true) {
        categoriesTabViewModel.onIntent(CategoriesIntent.LoadData)
    }
    Box(modifier = modifier.fillMaxSize()) {

        when {
            isLoading -> {
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(Modifier.weight(.18f)) { LeftMenuShimmer() }
                    Box(Modifier.weight(.82f)) { CategoryListShimmer() }
                }
            }

            errorMessage != null -> {
                ShowErrorScreen(message = errorMessage) {
                    categoriesTabViewModel.onIntent(CategoriesIntent.Retry)
                }
            }

            else -> {
                Row(modifier = Modifier.fillMaxSize()) {

                    Box(
                        modifier = Modifier
                            .weight(.18f)
                            .fillMaxHeight()
                            .padding(top = 8.dp)
                    ) {
                        state.categoriesUiState.data?.data?.leftMenu?.let {
                            state.selectedMenu?.let { selected ->
                                ShowLeftMenu(
                                    selectMenu = selected,
                                    menu = it
                                ) { menu ->
                                    categoriesTabViewModel.selectMenu(menu)
                                    scope.launch { listState.scrollToItem(0) }
                                }
                            }
                        }
                    }

                    val selectedMenu = state.selectedMenu
                    val categories =
                        state.categoriesUiState.data?.data?.categories.orEmpty()

                    val filtered = remember(categories, selectedMenu) {
                        categories.filter { it.menuId == selectedMenu }
                    }

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
   Box(modifier = modifier.fillMaxHeight().background(Color.White)) {
       LazyColumn {
           items(
               items = menu,
               key = { it.id }
           ) { menuItem ->
               LeftMenuItem(selectMenu = selectMenu, menuItem = menuItem) {
                   onClick(it)
               }
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
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(menuItem.icon)
            .crossfade(true)
            .build()
    )
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
                        color = if (selectMenu == menuItem.id)
                            MaterialTheme.colorScheme.background
                        else Color.White,
                        shape = RoundedCornerShape(topStart = 36.dp, bottomStart = 36.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (painter.state is AsyncImagePainter.State.Loading) {
                    ShimmerBox(
                        modifier = Modifier
                            .width(30.dp)
                            .height(40.dp)
                    )
                }
                Image(
                    painter = painter,
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
    val sections = remember(category) {
        category.firstOrNull()?.sections.orEmpty()
    }
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxHeight(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        sections.forEach { categoryItem ->

            item(key = "header_${categoryItem.title}") {
                Text(
                    text = categoryItem.title,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
            }

            val chunkedItems = categoryItem.items.chunked(4)

            items(
                items = chunkedItems,
                key = { row -> row.joinToString("_") { it.id } }
            ) { categoryList ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
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

@Composable
fun LeftMenuShimmer() {
    LazyColumn {
        items(10) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .size(40.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                ShimmerBox(
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth(0.6f)
                )
            }
        }
    }
}

@Composable
fun CategoryItemShimmer() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        ShimmerBox(
            modifier = Modifier
                .size(80.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        ShimmerBox(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.8f)
        )
    }
}

@Composable
fun CategoryListShimmer() {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {

        repeat(5) {
            item {
                ShimmerBox(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(16.dp)
                        .fillMaxWidth(0.4f)
                )
            }
            items(3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(4) {
                        Box(modifier = Modifier.weight(1f)) {
                            CategoryItemShimmer()
                        }
                    }
                }
            }
        }
    }
}