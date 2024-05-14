package com.example.rawg.data.roomModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platform")
data class RoomPlatform(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var gameId:Int,
    var releaseAt: String,
)

@Entity(tableName = "platformItem")
data class RoomPlatformItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var platformId: Int,
    var name: String?
)

@Entity(tableName = "platformRequirement")
data class RoomPlatformRequirement(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var platformId: Int,
    var minimum: String,
    var recommended: String
)