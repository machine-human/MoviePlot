package com.mhuman.movieplot.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.mhuman.movieplot.R
import com.mhuman.movieplot.base.BaseFragment
import com.mhuman.movieplot.base.BaseOnClickInterface
import com.mhuman.movieplot.databinding.FragmentFavoriteBinding
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment :
    BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(R.layout.fragment_favorite),
    BaseOnClickInterface {

    override val viewModel by viewModel<FavoriteViewModel>()
    private val recyclerViewAdapter: FavoriteRecyclerViewAdapter by lazy {
        FavoriteRecyclerViewAdapter(
            this
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding {
            //            recyclerViewAdapter.setHasStableIds(true)
            recycler_view_favorite_movie_list.adapter = recyclerViewAdapter
        }
        registerEvent()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavoriteMovieList()
    }

    override fun initializeUI() {
        with(viewModel) {
            floating_button_favorite_movie_list_clear.setOnClickListener {
                if (liveFavoriteMovieList.value?.size == 0 || liveFavoriteMovieList.value == null)
                    showToastMessage(getString(R.string.msg_favorite_movie_list_is_empty))
                else
                    showAlertDialog(getString(R.string.msg_delete_all)) {
                        viewModel.deleteAllFavoriteMovieList(recyclerViewAdapter)
                    }
            }
        }
    }

    override fun onItemLongSelected(view: View, position: Int) {
        showAlertDialog("영화 \"" + recyclerViewAdapter.getItem(position).title + "\"을 \n삭제 하시겠습니까?") {
            viewModel.deleteFavoriteMovieList(
                recyclerViewAdapter,
                position,
                recyclerViewAdapter.getItem(position).title
            )
        }
    }

    override fun registerEvent() {
        with(viewModel) {
            liveEventForDeleteFavoriteMovieList.observe(this@FavoriteFragment, Observer {
                if (it) showToastMessage(getString(R.string.msg_success_delete_favorite_movie))
                else showToastMessage(getString(R.string.msg_failed_delete_favorite_movie))
            })
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onClearedDisposable()
    }
}
