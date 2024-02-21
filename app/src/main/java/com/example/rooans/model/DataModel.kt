package com.example.rooans.model

import androidx.paging.cachedIn
import com.example.rooans.adapter.RepoAdapter
import com.example.rooans.repo.Repository
import kotlinx.coroutines.*

class DataModel {

    private lateinit var deferredArticles: Deferred<List<Article?>?>

    suspend fun getData(scope: CoroutineScope, repoAdapter: RepoAdapter): List<Article?>? {
        Repository.getPagingData().cachedIn(scope).collect { pagingData ->
            repoAdapter.submitData(pagingData)
        }
        deferredArticles = scope.async(Dispatchers.IO) {
            repoAdapter.snapshot().items
        }
        return  deferredArticles.await()
    }

}