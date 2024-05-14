package com.example.rawg.data.roomModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rawg.data.modelMapper.GameItem

@Entity(tableName = "favoriteGame")
data class RoomGameDetail(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var slug: String,
    var name: String,
    var released: String,
    var background_image: String
) {
    companion object {
        fun toRoomGameDetail(gameItem: GameItem): RoomGameDetail {
            return RoomGameDetail(
                gameItem.id,
                gameItem.slug,
                gameItem.name,
                gameItem.released,
                gameItem.background_image
            )
        }
        fun toGameItem(roomGameDetail: RoomGameDetail) : GameItem {
            return GameItem(
                roomGameDetail.id,
                roomGameDetail.slug,
                roomGameDetail.name,
                roomGameDetail.released,
                roomGameDetail.background_image,
                listOf()
            )
        }
    }
}