package kr.wayde.githubserach.local.room.dao

import androidx.room.*
import io.reactivex.Single
import kr.wayde.githubserach.local.room.model.GithubUserDto

@Dao
interface UserDao {
    @Delete
    fun delete(user: GithubUserDto)

    @Query("SELECT * FROM users WHERE login LIKE :login LIMIT 1")
    fun findById(login: String): Single<GithubUserDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GithubUserDto?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<GithubUserDto?>?)

    @Query("SELECT * FROM users")
    fun loadAll(): Single<List<GithubUserDto>>
}