package com.example.rawg.data.model

import com.example.rawg.data.modelMapper.Platform
import com.example.rawg.data.modelMapper.PlatformItem
import com.example.rawg.data.modelMapper.PlatformRequirement
import com.google.gson.annotations.SerializedName

data class PlatformResponse(
    @field:SerializedName("platform")
    var platform: PlatformItemResponse?,
    @field:SerializedName("release_at")
    var releaseAt: String?,
    @field:SerializedName("requirements_en")
    var requirements: PlatformRequirementResponse?,
) {
    fun toPlatform() : Platform {
        return Platform(platform?.toPlatformItem(), releaseAt?:"", requirements?.toPlatformRequirement())
    }
}

data class PlatformItemResponse(
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("name")
    var name: String
) {
    fun toPlatformItem(): PlatformItem {
        return PlatformItem(id, name)
    }
}

data class PlatformRequirementResponse(
    @field:SerializedName("minimum")
    var minimum: String,
    @field:SerializedName("recommended")
    var recommended: String
) {
    fun toPlatformRequirement(): PlatformRequirement {
        return PlatformRequirement(minimum, recommended)
    }
}
