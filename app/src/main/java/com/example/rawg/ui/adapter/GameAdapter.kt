package com.example.rawg.ui.adapter

import android.R.color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rawg.R
import com.example.rawg.base.ui.BaseAdapter
import com.example.rawg.data.modelMapper.GameItem
import com.example.rawg.data.roomModel.RoomGameDetail
import com.example.rawg.databinding.ItemGameBinding


class GameAdapter<T> : BaseAdapter<ItemGameBinding, ItemGameViewHolder>() {
    private var listGame = arrayListOf<T>()
    private var onItemClick: (T) -> Unit = {}
    private var onAddFavorit: (T, Int) -> Unit = { data, position -> }

    internal fun updateGameData(data: List<T?>?) {
        resetGameData()
        listGame.addAll(data as Collection<T>)
        if(listGame.isNotEmpty()) {
            notifyItemInserted(listGame.size)
            notifyItemChanged(listGame.size)
        }
    }

    internal fun notifyRemoveAndChange(position: Int) {
        listGame.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(0, listGame.size)
    }

    internal fun resetGameData() {
        listGame.clear()
    }

    internal fun whenItemClick(itemClick: (T) -> Unit) {
        onItemClick = { itemClick(it) }
    }

    internal fun whenFavoritClick(addFavoritClick: (T, Int) -> Unit) {
        onAddFavorit = { data, position -> addFavoritClick(data, position) }
    }

    override fun getBindingAdapter(parent: ViewGroup): ItemGameBinding {
        return ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun getViewHolder(adapterBinding: ItemGameBinding): ItemGameViewHolder {
        return ItemGameViewHolder(adapterBinding)
    }

    override fun bindVH(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemGameViewHolder).bind(listGame[position], position, onItemClick, onAddFavorit)
    }

    override fun totalItem(): Int = listGame.size
}


class ItemGameViewHolder(private val binding: ItemGameBinding) :
    RecyclerView.ViewHolder(binding.root) {
    internal fun <T>bind(game: T, position: Int, onItemClick: (T) -> Unit, onAddFavorite: (T, Int) -> Unit) {
        when(game) {
            is GameItem -> {
                binding.titleGame.text = game.name
                binding.releaseGame.text = game.released

                if(game.isAddFavorit) {
                    setToFavorit()
                } else {
                    setToUnFavorit()
                }

                if (game.background_image.isNotEmpty()) {
                    Glide.with(binding.root.context).load(game.background_image).placeholder(R.drawable.ic_launcher_background).into(binding.iconGame)
                }

                binding.root.setOnClickListener {
                    onItemClick(game)
                }

                binding.btnAddFavorit.setOnClickListener {
                    onAddFavorite(game, position)
                }

            }
            is RoomGameDetail -> {
                binding.titleGame.text = game.name
                binding.releaseGame.text = game.released
                if (game.background_image.isNotEmpty()) {
                    Glide.with(binding.root.context).load(game.background_image).placeholder(R.drawable.ic_launcher_background).into(binding.iconGame)
                }

                binding.root.setOnClickListener {
                    onItemClick(game)
                }

                binding.btnAddFavorit.text = binding.root.context.getString(R.string.remove_favorit)

                binding.btnAddFavorit.setOnClickListener {
                    onAddFavorite(game, position)
                }
            }
        }
    }

    internal fun setToFavorit() {
        binding.btnAddFavorit.setCompoundDrawablesWithIntrinsicBounds( R.drawable.red_heart_icon, 0, 0, 0)
        binding.btnAddFavorit.setTextColor(binding.root.context.resources.getColor(R.color.red_700))
    }

    internal fun setToUnFavorit() {
        binding.btnAddFavorit.setCompoundDrawablesWithIntrinsicBounds( R.drawable.grey_heart_icon, 0, 0, 0)
        binding.btnAddFavorit.setTextColor(binding.root.context.resources.getColor(R.color.gray))
    }
}
