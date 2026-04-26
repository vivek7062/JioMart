package com.example.jiomartclone.di.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jiomartclone.data.local.auth.UserDao
import com.example.jiomartclone.data.local.auth.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserDataBaseModule {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE UserEntity ADD COLUMN isVerify INTEGER NOT NULL DEFAULT 0"
            )
        }
    }

    @Provides
    @Singleton
    fun provideUserDatabase(
        @ApplicationContext context: Context
    ): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "UserDatabase"
        ).addMigrations(MIGRATION_1_2).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideUserDao(
        userDatabase: UserDatabase
    ): UserDao {
        return userDatabase.userDao()
    }
}