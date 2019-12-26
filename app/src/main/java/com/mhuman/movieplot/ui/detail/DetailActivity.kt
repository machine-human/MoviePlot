package com.mhuman.movieplot.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ButtonObject
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.mhuman.movieplot.BuildConfig
import com.mhuman.movieplot.R
import com.mhuman.movieplot.base.BaseActivity
import com.mhuman.movieplot.databinding.ActivityDetailBinding
import com.mhuman.movieplot.network.model.MovieInfoList
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.browse
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private val viewModel by viewModel<DetailViewModel>()
    private val recyclerViewAdapter: DetailRecyclerViewAdapter by lazy { DetailRecyclerViewAdapter() }
    private val movieInfoList: MutableList<MovieInfoList> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.AppDarkTheme)
        else
            setTheme(R.style.AppLightTheme)

        super.onCreate(savedInstanceState)

        binding {
            vm = viewModel
            view = this@DetailActivity
            recycler_view_cast_list.adapter = recyclerViewAdapter
            recycler_view_cast_list.layoutManager =
                LinearLayoutManager(this@DetailActivity, RecyclerView.HORIZONTAL, false)
        }

        registerEvent()

        with(viewModel) {
            loadTrailerList(intent.getIntExtra(EXTRA_MOVIE_ID, 0))
            loadCastList(intent.getIntExtra(EXTRA_MOVIE_ID, 0))
        }

        text_view_movie_title_content.apply {
            text = intent.getStringExtra(EXTRA_MOVIE_TITLE)
        }

        card_view_movie_title.setOnClickListener {
            browse(BuildConfig.NAVER_SEARCH_URL + intent.getStringExtra(EXTRA_MOVIE_TITLE))
        }

        card_view_favorite.setOnClickListener {
            movieInfoList.add(
                MovieInfoList(
                    intent.getIntExtra(EXTRA_MOVIE_ID, 0),
                    intent.getStringExtra(EXTRA_MOVIE_OVERVIEW),
                    intent.getStringExtra(EXTRA_MOVIE_POSTER_PATH),
                    intent.getStringExtra(EXTRA_MOVIE_TITLE)
                )
            )
            viewModel.saveFavoriteMovieInfo(movieInfoList)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }

    fun onShowKaKaoLink() {
        val KAKAO_BASE_LINK = "https://developers.kakao.com"
        // 공유하기 눌렀을 때 처리
        val params = FeedTemplate
            .newBuilder(
                ContentObject.newBuilder(
                    intent.getStringExtra(EXTRA_MOVIE_TITLE),
                    "https://image.tmdb.org/t/p/w500/" + intent.getStringExtra(
                        EXTRA_MOVIE_POSTER_PATH
                    ),
                    LinkObject.newBuilder()
                        .setWebUrl(KAKAO_BASE_LINK)
                        .setMobileWebUrl(KAKAO_BASE_LINK)
                        .build()
                )
                    .setDescrption(intent.getStringExtra(EXTRA_MOVIE_OVERVIEW))
                    .build()
            ).addButton(
                ButtonObject(
                    "앱에서 바로 확인", LinkObject.newBuilder()
                        .setWebUrl(KAKAO_BASE_LINK)
                        .setMobileWebUrl(KAKAO_BASE_LINK)
                        .setAndroidExecutionParams("123")
                        .build()
                )
            )
            .build()

        // 상세페이지 접근 리스너
        KakaoLinkService.getInstance()
            .sendDefault(this, params, object : ResponseCallback<KakaoLinkResponse>() {
                override fun onFailure(errorResult: ErrorResult) {
                    //no-operation
                }

                override fun onSuccess(result: KakaoLinkResponse) {
                    //no-operation
                }
            })
    }

    override fun registerEvent() {
        with(viewModel) {

            liveTrailerKey.observe(this@DetailActivity, Observer {
                lifecycle.addObserver(you_tube_player_preview)
                you_tube_player_preview.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(it, 0F)
                    }
                })
            })

            liveEventForSaveFavoriteMovieInfo.observe(this@DetailActivity, Observer {
                if (it) showToastMessage(getString(R.string.msg_success_save_favorite_movie))
                else showToastMessage(getString(R.string.msg_failed_save_favorite_movie))
            })

            liveTostEvent.observe(this@DetailActivity, Observer(::showToastMessage))
        }
    }

    companion object {
        const val EXTRA_MOVIE_TITLE = "EXTRA_MOVIE_INFO"
        const val EXTRA_MOVIE_OVERVIEW = "EXTRA_MOVIE_OVERVIEW"
        const val EXTRA_MOVIE_POSTER_PATH = "EXTRA_MOVIE_POSTER_PATH"
        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
    }
}
