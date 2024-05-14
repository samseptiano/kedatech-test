package com.example.rawg.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rawg.R
import com.example.rawg.base.ui.BaseFragment
import com.example.rawg.base.ui.endlessscroll.EndlessRecyclerViewScrollListener
import com.example.rawg.data.modelMapper.GameItem
import com.example.rawg.data.roomModel.RoomGameDetail
import com.example.rawg.databinding.FragmentGameListBinding
import com.example.rawg.ui.adapter.GameAdapter
import com.example.rawg.ui.adapter.ItemGameViewHolder
import com.example.rawg.ui.viewmodel.GameListViewModel
import com.example.rawg.utils.CONSTANTS
import com.example.rawg.utils.obtainViewModel
import com.example.rawg.utils.typingListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameListFragment : BaseFragment<FragmentGameListBinding>() {
    private lateinit var endlessScroll: EndlessRecyclerViewScrollListener
    private var pageSize: Int = 10
    private var currentPage:Int = 1
    private var search: String? = null
    private var listGame = arrayListOf<GameItem>()
    private var favListGame = arrayListOf<RoomGameDetail?>()

    private val gameAdapter by lazy {
        GameAdapter<GameItem>()
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGameListBinding
        get() = FragmentGameListBinding::inflate

    private val viewModel: GameListViewModel by lazy {
        obtainViewModel(requireActivity(), GameListViewModel::class.java, viewModelFactory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecycleView()
        setupClickListener()
        setupObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.action_favorit).isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorit) navigateToFavoritPage()
        return super.onOptionsItemSelected(item)
    }

    private fun setupObserver() {
        listGame.clear()

        lifecycleScope.launch {
            launch {
                viewModel.getListGameFavorit()
            }

            viewModel.gameFavoritList.observe(viewLifecycleOwner) { gameListFav ->
                favListGame.clear()
                if (gameListFav != null) { favListGame.addAll(gameListFav) }

                launch {
                    viewModel.getListGame(currentPage, pageSize, search)
                }
            }

            viewModel.gameList.observe(viewLifecycleOwner) { listGames ->
                hideLoading()
                showRecycleView()

                listGames?.map { gameItem ->
                    if (gameItem == null) return@map
                    gameItem.isAddFavorit = favListGame.find { it?.id == gameItem.id } != null

                    listGame.add(gameItem)
                }

                gameAdapter.updateGameData(listGame.distinctBy { it.id })
            }
        }
    }

    private fun setupRecycleView() {
        val layoutManagers =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        endlessScroll = object : EndlessRecyclerViewScrollListener(layoutManagers) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                lifecycleScope.launch(Dispatchers.Main) {
                    currentPage = page
                    viewModel.getListGame(currentPage + 1, pageSize, search)
                }
            }
        }

        binding.rvList.apply {
            layoutManager = layoutManagers
            adapter = gameAdapter
            addOnScrollListener(endlessScroll)
        }
    }

    private fun setupClickListener() {
        binding.rtSearch.typingListener {
            currentPage = 1
            search = it

            lifecycleScope.launch {
                showLoading()
                listGame.clear()
                gameAdapter.resetGameData()
                gameAdapter.notifyDataSetChanged()

                viewModel.getListGame(page = currentPage, pageSize = pageSize, search = it)
            }
        }

        gameAdapter.whenItemClick {
            navigateToDetailPage(it)
        }

        gameAdapter.whenFavoritClick { data, position ->
            lifecycleScope.launch {
                if(data.isAddFavorit) {
                    viewModel.removeGameToFavorit(data)
                } else {
                    viewModel.addGameToFavorit(data)
                }
            }

            if(data.isAddFavorit) setbuttonUnFavorit(position) else setbuttonFavorit(position)
            gameAdapter.updateGameData(listGame)
        }
    }

    private fun hideLoading() {
        binding.pbLoad.visibility = View.GONE
    }

    private fun showLoading() {
        binding.pbLoad.visibility = View.VISIBLE
    }

    private fun showRecycleView() {
        binding.rvList.visibility = View.VISIBLE
    }

    private fun setbuttonFavorit(position: Int) {
        (binding.rvList.findViewHolderForAdapterPosition(position) as ItemGameViewHolder).setToFavorit()
        listGame[position].isAddFavorit = true
    }

    private fun setbuttonUnFavorit(position: Int) {
        (binding.rvList.findViewHolderForAdapterPosition(position) as ItemGameViewHolder).setToUnFavorit()
        listGame[position].isAddFavorit = false
    }

    private fun navigateToDetailPage(data: GameItem) {
        val bundle = bundleOf(CONSTANTS.TAG_GAME_ID to data.id)
        findNavController().navigate(R.id.gameDetailFragment, bundle)
    }

    private fun navigateToFavoritPage() {
        findNavController().navigate(R.id.gameFavoritFragment)
    }

}