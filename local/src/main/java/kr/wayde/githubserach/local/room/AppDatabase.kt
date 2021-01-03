package kr.wayde.githubserach.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.wayde.githubserach.local.room.dao.UserDao
import kr.wayde.githubserach.local.room.model.GithubUserDto

@Database(entities = arrayOf(GithubUserDto::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}