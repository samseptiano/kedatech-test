package com.example.rawg.ui.fragment

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rawg.base.ui.BaseFragment
import com.example.rawg.base.ui.endlessscroll.EndlessRecyclerViewScrollListener
import com.example.rawg.data.modelMapper.GameDetail
import com.example.rawg.databinding.FragmentGameDetailBinding
import com.example.rawg.ui.viewmodel.GameDetailViewModel
import com.example.rawg.utils.CONSTANTS
import com.example.rawg.utils.obtainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameDetailFragment : BaseFragment<FragmentGameDetailBinding>() {
    private val gameId by lazy {
        arguments?.getInt(CONSTANTS.TAG_GAME_ID, 0)
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGameDetailBinding
        get() = FragmentGameDetailBinding::inflate

    private val viewModel: GameDetailViewModel by lazy {
        obtainViewModel(requireActivity(), GameDetailViewModel::class.java, viewModelFactory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            launch {
                viewModel.getDetailGame(gameId ?: 0)
            }
            launch {
                viewModel.gameDetail.observe(viewLifecycleOwner) {
                    it?.let { it1 -> setupBinding(it1) }
                }
            }
        }
    }

    private fun setupBinding(data: GameDetail) {
        binding.apply {
            var platforms = ""
            data.platforms.mapIndexed { index, platform ->
                platforms += platform.platform?.name.orEmpty()
                if(index < data.platforms.size - 1) platforms += ", "
            }

            Glide.with(requireContext()).load(data.background_image).into(imageGame)
            gameTitle.text = data.name
            gameRelease.text = "Released on: ${data.released}"
            gamePlaytime.text = "Total playtime: ${data.playtime} hours"
            gamePlatform.text = "Support on: ${platforms}"
            gameWebsite.text = data.website
            gameOriginalName.text = "${data.name} (a.k.a ${data.originalName})"
            gameRating.text = "${data.rating}/5"
            gameRatingStar.rating = data.rating?.toFloat() ?: 0f
            gameDescription.text = Html.fromHtml(data.description)
        }
    }

}