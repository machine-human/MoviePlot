package com.mhuman.movieplot.ui.movie

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.commit451.modalbottomsheetdialogfragment.ModalBottomSheetDialogFragment
import com.commit451.modalbottomsheetdialogfragment.Option
import com.mhuman.movieplot.R
import com.mhuman.movieplot.base.BaseFragment
import com.mhuman.movieplot.databinding.FragmentMovieBinding
import com.mhuman.movieplot.ext.makeClearableEditText
import com.mhuman.movieplot.network.model.MovieInfoList
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment :
    BaseFragment<FragmentMovieBinding, MovieViewModel>(R.layout.fragment_movie),
    ModalBottomSheetDialogFragment.Listener {
    override val viewModel by viewModel<MovieViewModel>()
    private val pagingAdpater: MoviePagingAdapter by lazy { MoviePagingAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding {
            recycler_view_movie_list.adapter = pagingAdpater
        }

        registerEvent()
        showMovieList(null, null)

        edit_text_search_movie.makeClearableEditText(null, null)

        floating_button_genre.setOnClickListener { setDialogBuilder() }

        swipe_refresh.setOnRefreshListener {
            showMovieList(null, null)
            swipe_refresh.isRefreshing = false
        }

        recycler_view_movie_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.computeVerticalScrollOffset() == 0)
                    setVisibilityMoveScrollButtonWithSearchEditText(true)
                else if (floating_button_move_scroll_position_to_top.isVisible == false)
                    setVisibilityMoveScrollButtonWithSearchEditText(false)
            }
        })

        floating_button_move_scroll_position_to_top.setOnClickListener {
            recycler_view_movie_list.scrollToPosition(0)
            setVisibilityMoveScrollButtonWithSearchEditText(true)
        }

        edit_text_search_movie.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text.toString().equals("")) {
                    showToastMessage(getString(R.string.msg_description_write_movie_title))
                    true
                }
                showMovieList(v.text.toString(), null)
                true
            } else
                false
        }
    }

    private fun showEmptyImage(resultIsNull: Boolean) {
        if (resultIsNull) {
            if (layout_empty_search_movie.isVisible == false)
                layout_empty_search_movie.isVisible = true
        } else {
            if (layout_empty_search_movie.isVisible == true)
                layout_empty_search_movie.isVisible = false
        }
    }

    private fun showMovieList(query: String?, genreCode: String?) {
        with(viewModel) {
            if (query != null && genreCode != null)
                loadGenreMovieList(query, genreCode)
            else if (query != null && genreCode == null) {
                loadSearchMovieList(query)
            } else
                loadMovieList()
        }
    }

    private fun setVisibilityMoveScrollButtonWithSearchEditText(visible: Boolean) {
        if (visible) {
            layout_search_movie.isVisible = true
            floating_button_move_scroll_position_to_top.hide()
        } else {
            layout_search_movie.isVisible = false
            floating_button_move_scroll_position_to_top.show()
        }
    }

    private fun setDialogBuilder() {
        ModalBottomSheetDialogFragment.Builder()
            .add(R.menu.menu_bottom_sheet)
            .layout(R.layout.item_bottom_sheet)
            .header("영화 장르")
            .columns(3)
            .show(this@MovieFragment.childFragmentManager, "")
    }

    override fun onModalOptionSelected(tag: String?, option: Option) {
        val keyword = view.let { resources.getResourceName(option.id).split("_") }
        showMovieList(option.title.toString(), keyword[1])
    }

    override fun registerEvent() {
        with(viewModel) {
            getPagedListLiveData().observe(
                this@MovieFragment,
                object : Observer<PagedList<MovieInfoList>> {
                    override fun onChanged(t: PagedList<MovieInfoList>?) {
                        hideLoading()
                        pagingAdpater.submitList(null)
                        pagingAdpater.notifyDataSetChanged()
                        showEmptyImage(false)
                        pagingAdpater.submitList(t)
                    }
                })

            liveNetworkErrors.observe(this@MovieFragment, Observer {
                hideLoading()
                showEmptyImage(true)
            })
        }
    }
}
