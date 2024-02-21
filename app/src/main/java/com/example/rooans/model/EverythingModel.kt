package com.example.rooans.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rooans.util.Utils
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName

data class Everything(
    @SerializedName(Utils.status) val status: String = "",
    @SerializedName(Utils.totalResults) val totalResults: Long = 0,
    @SerializedName(Utils.articles) val articles: List<Article>?,
)

@IgnoreExtraProperties
@Entity(tableName = Utils.articles)
data class Article(
    @PrimaryKey(autoGenerate = true)
    @SerializedName(Utils.id) @ColumnInfo(name = Utils.id) val id: Int,
    @SerializedName(Utils.source) @ColumnInfo(name = Utils.source) val source: Source?,
    @SerializedName(Utils.author) @ColumnInfo(name = Utils.author) val author: String?,
    @SerializedName(Utils.title) @ColumnInfo(name = Utils.title) val title: String?,
    @SerializedName(Utils.description) @ColumnInfo(name = Utils.description) val description: String?,
    @SerializedName(Utils.url) @ColumnInfo(name = Utils.url) val url: String?,
    @SerializedName(Utils.urlToImage) @ColumnInfo(name = Utils.urlToImage) val urlToImage: String?,
    @SerializedName(Utils.publishedAt) @ColumnInfo(name = Utils.publishedAt) val publishedAt: String?,
    @SerializedName(Utils.content) @ColumnInfo(name = Utils.content) val content: String?,
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            Utils.source to source,
            Utils.author to author,
            Utils.title to title,
            Utils.description to description,
            Utils.url to url,
            Utils.urlToImage to urlToImage,
            Utils.publishedAt to publishedAt,
            Utils.content to content,
        )
    }
}

data class Source(
    @SerializedName(Utils.id) @ColumnInfo(name = Utils.id) val id: String?,
    @SerializedName(Utils.name) @ColumnInfo(name = Utils.name) val name: String?,
)