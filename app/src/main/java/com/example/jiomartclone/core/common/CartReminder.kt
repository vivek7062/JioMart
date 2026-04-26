package com.example.jiomartclone.core.common

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.jiomartclone.data.local.auth.CartDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CartReminder @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val cartDao: CartDao,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {

        val items = cartDao.getCartItemsOnce().size

        if (items > 0) {
            notificationHelper.showNotification(
                "Want to checkout",
                "Your cart has $items items"
            )
        }

        return Result.success()
    }
}