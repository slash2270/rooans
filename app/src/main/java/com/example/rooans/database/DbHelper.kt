//package com.example.rooans.database
//
//import android.content.Context
//import androidx.room.Room
//import com.example.rooans.model.Article
//import com.example.rooans.util.Utils
//import kotlin.jvm.Synchronized
//
//class DbHelper {
//
//    @Volatile
//    var mDataBase: DataBase? = null
//    fun createDb(context: Context): DataBase? {
//        val tempDB = mDataBase
//        if(tempDB != null){
//            return tempDB
//        }
//        synchronized(this){
//            mDataBase = Room.databaseBuilder(context, DataBase::class.java, Utils.database)
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()    // 清除資料
//                .build()
//            return mDataBase
//        }
//    }
//
//    //新增
//    @Synchronized
//    fun add(data: Article) {
//        mDataBase?.dbDao()?.add(data)
//    }
//
//    //查询
//    @Synchronized
//    fun select(id: Int): Article? {
//        return mDataBase?.dbDao()?.select(id)
//    }
//
//    @Synchronized
//    fun selectList(): List<Article>? {
//        return mDataBase?.dbDao()?.selectList()
//    }
//
//    //删除
//    @Synchronized
//    fun delete(id: Int) {
//        mDataBase?.dbDao()?.delete(id)
//    }
//
//    @Synchronized
//    fun deleteTable() {
//        mDataBase?.dbDao()?.deleteTable()
//    }
//
//    //修改
//    @Synchronized
//    fun update(description: String, id: Int, urlToImage: String) {
//        mDataBase?.dbDao()?.update(id, description, urlToImage)
//    }
//
//    //關閉Db
//    @Synchronized
//    fun closeDb() {
//        mDataBase?.close()
//    }
//
//}