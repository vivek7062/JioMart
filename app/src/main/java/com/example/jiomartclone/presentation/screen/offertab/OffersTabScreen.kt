package com.example.jiomartclone.presentation.screen.offertab

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.jiomartclone.data.local.auth.CartItem
import com.example.jiomartclone.domain.model.lowprice.Product
import com.example.jiomartclone.domain.model.offertab.FilterSection
import com.example.jiomartclone.domain.model.offertab.LeftMenuOffer
import com.example.jiomartclone.domain.model.offertab.toFilterSections
import com.example.jiomartclone.presentation.components.ShowErrorScreen
import com.example.jiomartclone.presentation.screen.cart.CartViewModel
import com.example.jiomartclone.presentation.screen.categoriestab.LeftMenuShimmer
import com.example.jiomartclone.presentation.screen.home.ShimmerBox
import com.example.jiomartclone.presentation.screen.hometab.lowprice.ProductItem
import kotlinx.coroutines.launch
import kotlin.collections.isNotEmpty
import kotlin.collections.orEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersTabScreen(
    modifier: Modifier = Modifier,
    offersTabViewModel: OffersTabViewModel = hiltViewModel(),
) {
    val state by offersTabViewModel.state.collectAsStateWithLifecycle()
    val isLoading = state.offersTabUiState.isLoading
    val errorMessage = state.offersTabUiState.error
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showSheet by rememberSaveable { mutableStateOf(false) }
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()

    var tempFilters by rememberSaveable {
        mutableStateOf(state.selectedFilters)
    }

    LaunchedEffect(showSheet) {
        if (showSheet) {
            tempFilters = state.selectedFilters
        }
    }

    LaunchedEffect(true) {
        offersTabViewModel.onIntent(OffersTabIntent.LoadData)
    }

    Box(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(.18f)
                    .fillMaxHeight()
                    .padding(top = 8.dp)
            ) {
                state.offersTabUiState.data?.leftMenu?.let {
                    state.selectedMenuId?.let { selected ->
                        ShowLeftMenu(
                            selectMenu = selected, menu = it
                        ) { menu ->
                            offersTabViewModel.selectMenu(menu)
                            scope.launch {
                                listState.scrollToItem(0)
                            }
                        }
                    }
                }
            }
            val selectedMenu = state.selectedMenuId
            val products = state.offersTabUiState.data?.products.orEmpty()
            val filters = state.offersTabUiState.data?.filters?.toFilterSections().orEmpty()
            val priceOptions = filters.find { it.id == "price" }?.options.orEmpty()
            val discountOptions = filters.find { it.id == "discount" }?.options.orEmpty()

            val filteredProducts by remember(products, selectedMenu, state.selectedFilters) {
                derivedStateOf {
                    products.filter { product ->
                        if (product.menuId != selectedMenu) return@filter false
                        state.selectedFilters.all { (key, values) ->
                            values.isEmpty() || when (key) {
                                "brand" -> product.brand in values
                                "category" -> product.category in values
                                "subCategory" -> product.subCategory in values
                                "price" -> priceOptions.any {
                                    it.id in values &&
                                            product.productPrice in it.min!!..it.max!!
                                }
                                "discount" -> discountOptions.any {
                                    it.id in values &&
                                            product.productDiscount in it.min!!..it.max!!
                                }
                                else -> true
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(.82f)
                    .fillMaxHeight()
            ) {

                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                showSheet = true
                            }
                            .padding(vertical = 10.dp, horizontal = 12.dp)
                            .clip(RoundedCornerShape(64.dp))
                            .background(Color.White)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Tune,
                            tint = Color(0xFF6B7280),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Filter",
                            color = Color(0xFF6B7280),
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                        val hasActiveFilters = state.selectedFilters.values.any { it.isNotEmpty() }
                        if (hasActiveFilters) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFF1C00))
                            )
                        }
                    }
                    val alpha by animateFloatAsState(
                        targetValue = if (filteredProducts.isNotEmpty()) 1f else 0f,
                        label = ""
                    )

                    Box(modifier = Modifier.alpha(alpha)) {
                        ProductList(
                            products = filteredProducts,
                            cartViewModel = cartViewModel,
                            cartItems = cartItems
                        )
                    }
                }

            }
        }
        if (isLoading) {
            OffersTabShimmer()
        }
        if (!isLoading && errorMessage != null) {
            ShowErrorScreen(message = errorMessage) {
                offersTabViewModel.onIntent(OffersTabIntent.Retry)
            }
        }
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                modifier = modifier,
                dragHandle = null

            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Filters",
                            modifier = Modifier.weight(1f),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            modifier = Modifier.clickable {
                                scope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    showSheet = false
                                }
                            })
                    }

                    HorizontalDivider()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                    ) {
                        val filters =
                            state.offersTabUiState.data?.filters?.toFilterSections().orEmpty()
                        var selectedFilterId by rememberSaveable { mutableStateOf<String?>(null) }
                        LaunchedEffect(filters) {
                            if (selectedFilterId == null && filters.isNotEmpty()) {
                                selectedFilterId = filters.first().id
                            }
                        }

                        FilterContent(
                            filters = filters,
                            selectedFilterId = selectedFilterId,
                            onSelectFilter = { selectedFilterId = it },
                            onToggle = { filterId, value ->
                                tempFilters = tempFilters.toMutableMap().apply {
                                    val set = this[filterId]?.toMutableSet() ?: mutableSetOf()
                                    if (set.contains(value)) set.remove(value)
                                    else set.add(value)
                                    this[filterId] = set
                                }
                            },
                            selectedFilters = tempFilters
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                tempFilters = emptyMap()
                            }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(50.dp)
                        ) { Text(text = "Clear All") }

                        Button(
                            onClick = {
                                offersTabViewModel.applyFilters(tempFilters)
                                scope.launch { sheetState.hide() }
                                    .invokeOnCompletion { showSheet = false }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0F6E8C)
                            )
                        ) {
                            Text("Apply", color = Color.White)
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
    menu: List<LeftMenuOffer>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    Box(modifier = modifier
        .fillMaxHeight()
        .background(Color.White)) {
        LazyColumn(modifier = modifier, contentPadding = PaddingValues(bottom = 80.dp)) {
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
    menuItem: LeftMenuOffer,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = modifier,
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
                    .clickable {
                        onClick(menuItem.id)
                    }
                    .height(56.dp)
                    .padding(start = 4.dp)
                    .background(
                        color = if (selectMenu == menuItem.id) MaterialTheme.colorScheme.background else Color(
                            0xffffffff
                        ), shape = RoundedCornerShape(topStart = 36.dp, bottomStart = 36.dp)
                    ), contentAlignment = Alignment.Center) {
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
fun ProductList(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel,
    cartItems: List<CartItem>,
    products: List<Product>,
) {
    val haptic = LocalHapticFeedback.current
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 100.dp, start = 8.dp, end = 8.dp, top = 8.dp)
    ) {
        items(
            items = products,
            key = { it.productId }
        ) { item ->
            val quantity = cartItems.find {
                it.productId == item.productId
            }?.quantity ?: 0
            ProductItem(product = item, quantity = quantity, onAdd = {
                cartViewModel.addToCart(item)
                haptic.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
            }, onRemove = {
                cartViewModel.removeFromCart(item.productId)
                haptic.performHapticFeedback(
                    HapticFeedbackType.LongPress
                )
            })
        }
    }
}

@Composable
fun FilterContent(
    filters: List<FilterSection>,
    selectedFilterId: String?,
    selectedFilters: Map<String, Set<String>>,
    onSelectFilter: (String) -> Unit,
    onToggle: (String, String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF2F2F2))
    ) {

        LazyColumn(modifier = Modifier.weight(0.4f), userScrollEnabled = true) {
            items(filters) { filter ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelectFilter(filter.id)
                        }
                        .background(
                            color = if (filter.id == selectedFilterId) Color.White else Color(
                                0xFFF2F2F2
                            ),
                        )
                        .padding(16.dp),
                ) {
                    Text(
                        text = filter.title,
                        color = if (filter.id == selectedFilterId) Color(0xFF1A1A1A) else Color(
                            0xFF8E8E93
                        ),
                        maxLines = 1, overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                    val count = selectedFilters[filter.id]?.size ?: 0
                    if (count > 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFF9800))
                        )
                    }
                }
            }
        }
        val searchFilter = rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .weight(.6f)
                .fillMaxHeight()
                .background(color = Color.White)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(40.dp)
                    .border(
                        width = 1.dp, color = Color(0xFF3E3E3E), shape = RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )

                BasicTextField(
                    value = searchFilter.value,
                    onValueChange = { searchFilter.value = it },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    textStyle = TextStyle(
                        color = Color.Black, fontSize = 14.sp
                    ),
                    cursorBrush = SolidColor(Color.Black),
                    decorationBox = { innerTextField ->

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (searchFilter.value.isEmpty()) {
                                Text(
                                    text = "Search", color = Color(0xFF8E8E93), fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                    })
                if (searchFilter.value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                searchFilter.value = ""
                            })
                }
            }
            val selected = filters.find { it.id == selectedFilterId }
            val filterOptions = selected?.options?.filter { option ->
                searchFilter.value.isBlank() || option.label.contains(
                    searchFilter.value, ignoreCase = true
                )
            }.orEmpty()
            LazyColumn(userScrollEnabled = true) {
                items(filterOptions) { option ->
                    val isSelected = option.id in selectedFilters[selected?.id].orEmpty()
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .clickable {
                                onToggle(selected?.id!!, option.id)
                            }) {
                        Checkbox(
                            checked = isSelected, enabled = true, onCheckedChange = {
                                onToggle(selected?.id!!, option.id)
                            }, colors = CheckboxDefaults.colors()
                        )
                        Text(
                            text = option.label,
                            color = Color(0xFF8E8E93),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color.White
                                )
                                .padding(16.dp),
                            maxLines = 1, overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItemShimmer() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        ShimmerBox(
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        ShimmerBox(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        ShimmerBox(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth(0.5f)
        )
    }
}

@Composable
fun ProductGridShimmer() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(8) {
            ProductItemShimmer()
        }
    }
}

@Composable
fun FilterButtonShimmer() {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        ShimmerBox(
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        ShimmerBox(
            modifier = Modifier
                .height(14.dp)
                .width(60.dp)
        )
    }
}

@Composable
fun OffersRightShimmer() {
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FilterButtonShimmer()
        }

        ProductGridShimmer()
    }
}

@Composable
fun OffersTabShimmer() {
    Row(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .weight(.18f)
                .fillMaxHeight()
        ) {
            LeftMenuShimmer()
        }
        Box(
            modifier = Modifier
                .weight(.82f)
                .fillMaxHeight()
        ) {
            OffersRightShimmer()
        }
    }
}