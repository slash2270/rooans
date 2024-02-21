package com.example.rooans.database.`interface`//package com.example.rooans.database

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.rooans.model.Article

@Dao
interface DbDao {

    //插入
    @Insert(onConflict = 5)
    fun add(databaseModel: Article)

    //修改
    @Query("UPDATE articles SET description=:description, urlToImage=:urlToImage WHERE id=:id")
    fun update(id: Int, description: String, urlToImage: String)

    //删除
    @Query("DELETE FROM articles WHERE id=:id")
    fun delete(id: Int)

   @Query("DELETE FROM articles")
    fun deleteTable()

    //查詢
    @Query("SELECT * FROM articles WHERE id=:id")
    fun select(id: Int): Article?

    @Query("SELECT * FROM articles ORDER BY id ASC")
    fun selectList(): List<Article>?

    @Query("SELECT * FROM articles WHERE id IN (:id)")
    fun selectListById(id: IntArray): List<Article>?

}