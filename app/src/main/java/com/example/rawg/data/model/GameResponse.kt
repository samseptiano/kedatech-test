package com.example.rawg.data.model

import com.example.rawg.data.modelMapper.GameDetail
import com.example.rawg.data.modelMapper.GameItem
import com.google.gson.annotations.SerializedName

data class GameResponse(
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("slug")
    var slug: String,
    @field:SerializedName("name")
    var name: String,
    @field:SerializedName("released")
    var released: String,
    @field:SerializedName("background_image")
    var background_image: String,
    @field:SerializedName("platforms")
    var platforms: List<PlatformResponse>?,
    @field:SerializedName("name_original")
    var originalName: String? = null,
    @field:SerializedName("description")
    var description: String? = null,
    @field:SerializedName("website")
    var website: String? = null,
    @field:SerializedName("rating")
    var rating: String? = null,
    @field:SerializedName("playtime")
    var playtime: String? = null,
) {
    fun toGameItem() : GameItem {
        return GameItem(id, slug, name, released, background_image,
            platforms?.map { it.toPlatform() } ?: listOf())
    }

    fun toGameDetail() : GameDetail {
        return GameDetail(id, slug, name, released, background_image,
            platforms?.map { it.toPlatform() } ?: listOf(), originalName, description, website, rating, playtime)
    }
}
