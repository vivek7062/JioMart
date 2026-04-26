package com.example.jiomartclone.presentation.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiomartclone.data.local.auth.CartDao
import com.example.jiomartclone.data.local.auth.CartItem
import com.example.jiomartclone.domain.model.lowprice.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartDao: CartDao) : ViewModel() {
    val cartItems = cartDao.getCartItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val totalItems = cartItems.map { list ->
        list.sumOf { it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val totalPrice = cartItems.map { list ->
        list.sumOf {
            ((it.price * (100 - it.discount)) / 100.0 * it.quantity).toInt()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    fun addToCart(product: Product){
        viewModelScope.launch {
            val existing = cartDao.getCartItem(product.productId)
            if(existing == null){
                cartDao.insert(
                    CartItem(
                        productId = product.productId,
                        name = product.productName,
                        price = product.productPrice,
                        quantity = 1,
                        discount = product.productDiscount,
                        image = product.productImageUrl
                    )
                )
            } else {
                cartDao.insert(existing.copy(quantity = existing.quantity+1))
            }
        }
    }

    fun removeFromCart(productId : Int){
        viewModelScope.launch {
            val existing = cartDao.getCartItem(productId) ?: return@launch
            if(existing.quantity<=1){
                cartDao.delete(productId)
            } else{
                cartDao.insert(existing.copy(quantity = existing.quantity-1))
            }
        }
    }
}