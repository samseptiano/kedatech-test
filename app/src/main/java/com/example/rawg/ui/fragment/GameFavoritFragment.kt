package com.example.rawg.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rawg.R
import com.example.rawg.base.ui.BaseFragment
import com.example.rawg.data.modelMapper.GameItem
import com.example.rawg.data.roomModel.RoomGameDetail
import com.example.rawg.databinding.FragmentGameFavoritBinding
import com.example.rawg.ui.adapter.GameAdapter
import com.example.rawg.ui.viewmodel.GameListViewModel
import com.example.rawg.utils.CONSTANTS
import com.example.rawg.utils.obtainViewModel
import com.example.rawg.utils.typingListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFavoritFragment : BaseFragment<FragmentGameFavoritBinding>() {
    private var listGameFavorit: ArrayList<RoomGameDetail?>? = null
    private val gameAdapter by lazy {
        GameAdapter<RoomGameDetail>()
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGameFavoritBinding
        get() = FragmentGameFavoritBinding::inflate

    private val viewModel: GameListViewModel by lazy {
        obtainViewModel(requireActivity(), GameListViewModel::class.java, viewModelFactory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        setupClickListener()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            launch {
                viewModel.getListGameFavorit()
            }
            launch {
                viewModel.gameFavoritList.observe(viewLifecycleOwner) { listGames ->
                    listGameFavorit = listGames as ArrayList<RoomGameDetail?>?

                    if (listGames?.isNotEmpty() == true) {
                        hideNodata()
                        showRecycleView()
                    } else {
                        showNodata()
                        hideRecycleView()
                    }

                    gameAdapter.updateGameData(listGames)
                }
            }
        }
    }

    private fun setupRecycleView() {
        val layoutManagers =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvList.apply {
            layoutManager = layoutManagers
            adapter = gameAdapter
        }
    }

    private fun setupClickListener() {
        gameAdapter.whenItemClick {
            navigateToDetailPage(it)
        }

        gameAdapter.whenFavoritClick { data, position ->
                lifecycleScope.launch {
                    viewModel.removeGameToFavorit(RoomGameDetail.toGameItem(data))
                }
                gameAdapter.notifyRemoveAndChange(position)
            }
    }

    private fun hideNodata() {
        binding.tvNoData.visibility = View.GONE
    }

    private fun showNodata() {
        binding.tvNoData.visibility = View.VISIBLE
    }

    private fun showRecycleView() {
        binding.rvList.visibility = View.VISIBLE
    }

    private fun hideRecycleView() {
        binding.rvList.visibility = View.GONE
    }

    private fun navigateToDetailPage(data: RoomGameDetail) {
        val bundle = bundleOf(CONSTANTS.TAG_GAME_ID to data.id)
        findNavController().navigate(R.id.gameDetailFragment, bundle)
    }

}