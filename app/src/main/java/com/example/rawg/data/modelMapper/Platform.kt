package com.example.rawg.data.modelMapper

data class Platform(
    var platform: PlatformItem?,
    var releaseAt: String?,
    var requirements: PlatformRequirement?,
)

data class PlatformItem(
    var id: Int,
    var name: String?
)

data class PlatformRequirement(
    var minimum: String?,
    var recommended: String?
)