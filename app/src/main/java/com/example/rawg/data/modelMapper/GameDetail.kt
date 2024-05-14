package com.example.rawg.data.modelMapper

data class GameDetail(
    var id: Int,
    var slug: String,
    var name: String,
    var released: String,
    var background_image: String,
    var platforms: List<Platform>,
    var originalName: String? = null,
    var description: String? = null,
    var website: String? = null,
    var rating: String? = null,
    var playtime: String? = null,
)