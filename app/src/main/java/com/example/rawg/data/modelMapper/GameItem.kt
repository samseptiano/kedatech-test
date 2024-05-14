package com.example.rawg.data.modelMapper

data class GameItem(
    var id: Int,
    var slug: String,
    var name: String,
    var released: String,
    var background_image: String,
    var platforms: List<Platform>,
    var isAddFavorit: Boolean = false
)