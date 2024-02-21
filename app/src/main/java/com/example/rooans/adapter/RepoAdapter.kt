package com.example.rooans.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.example.rooans.R
import com.example.rooans.databinding.ItemViewBinding
import com.example.rooans.database.`interface`.DbImpl
import com.example.rooans.model.Article
import com.example.rooans.util.Utils
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoAdapter(val scope: CoroutineScope, val database: DatabaseReference, val dbImpl: DbImpl) : PagingDataAdapter<Article, ItemViewHolder>(COMPARATOR) {

    private lateinit var itemViewBinding: ItemViewBinding

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.source == newItem.source
            }
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        database.removeValue()
        itemViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_view, parent, false)
        return ItemViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Article? = getItem(position)
        if (item?.urlToImage != null && item.urlToImage.isNotEmpty() && item.description != null && item.description.isNotEmpty()) {
            holder.bindItem(item)
            itemViewBinding.ivImage.load(item.urlToImage)
            realtime(item, position)
        }
    }

    private fun realtime(article: Article, index: Int) {
        val addValueEventListener = database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                scope.launch(Dispatchers.IO) {
                    val childUpdates = hashMapOf<String, Any>("/${Utils.articles}/$index" to article.toMap())
                    database.updateChildren(childUpdates)
                    dbImpl.get(database)
//                    dataSnapshot.getValue<HashMap<String, Any>>().let { map ->
//                        if (map?.isNotEmpty() == true) {
//                            val listArticle = map[Utils.articles] as List<*>
//                            if (index > 0) {
//                                val childUpdates = hashMapOf<String, Any>("/${Utils.articles}/${listArticle.size}" to article.toMap())
//                                database.updateChildren(childUpdates)
//                                if (article !in listArticle) {
//                                    database.child(Utils.articles).child(listArticle.size.toString()).setValue(article)
//                                }
//                                listArticle.map {
//                                    article.description != (it as Article).description
//                                }.let { list ->
//                                    if (list.isNotEmpty()) {
//                                        database.child(Utils.articles).child(listArticle.size.toString()).setValue(article)
//                                    }
//                                }
//                            } else {
//                                val childUpdates = hashMapOf<String, Any>("/${Utils.articles}/0" to article.toMap())
//                                database.updateChildren(childUpdates)
//                            }
//                        }
//                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        database.addValueEventListener(addValueEventListener)
    }

}