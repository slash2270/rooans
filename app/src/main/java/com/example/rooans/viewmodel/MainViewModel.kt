package com.example.rooans.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import coil.Coil
import coil.ImageLoader
import coil.request.CachePolicy
import coil.util.CoilUtils
import com.example.rooans.MainActivity
import com.example.rooans.R
import com.example.rooans.adapter.FooterAdapter
import com.example.rooans.adapter.RepoAdapter
import com.example.rooans.databinding.ActivityMainBinding
import com.example.rooans.database.`interface`.DbImpl
import com.example.rooans.model.Article
import com.example.rooans.model.DataModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainViewModel: ViewModel() {

    private lateinit var repoAdapter: RepoAdapter
    private lateinit var database: DatabaseReference
    private val listData: MutableLiveData<List<Article?>?> = MutableLiveData<List<Article?>?>()

    fun getData(activity: MainActivity) {
        database = Firebase.database.reference
        repoAdapter = RepoAdapter(viewModelScope, database, object : DbImpl {
            override fun get(dataBase: DatabaseReference) {
                database = dataBase
            }
        })
        viewModelScope.launch(Dispatchers.Main) {
            DataModel().getData(viewModelScope, repoAdapter).let { list ->
                if (list?.isNotEmpty() == true) {
                    listData.value = list.toSet().filterNotNull()
                    listData.observe(activity) { articles ->
                        Log.d("MainViewModel articles","${articles?.size}")
                    }
                }
            }
        }
    }

    fun recycler(binding: ActivityMainBinding, activity: MainActivity) {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = repoAdapter.withLoadStateFooter(FooterAdapter {
            repoAdapter.retry()
        })
    }

    fun adapter(binding: ActivityMainBinding, activity: MainActivity) {
        repoAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressCircular.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    binding.progressCircular.visibility = View.INVISIBLE
                    Toast.makeText(activity, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun coil(activity: MainActivity) {
        val okHttpClient = OkHttpClient.Builder()
            .cache(CoilUtils.createDefaultCache(activity))
            .build()
        val imageLoader = ImageLoader.Builder(activity)
            .availableMemoryPercentage(0.2)
            .diskCachePolicy(CachePolicy.ENABLED)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .crossfade(true)
            .crossfade(3000)
            .okHttpClient { okHttpClient }
            .build()
        Coil.setImageLoader(imageLoader)
    }

    fun destroy() {
        database.removeValue()
        database.onDisconnect()
    }

}