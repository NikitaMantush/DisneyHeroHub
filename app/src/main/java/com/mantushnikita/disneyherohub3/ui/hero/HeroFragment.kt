package com.mantushnikita.disneyherohub3.ui.hero

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.mantushnikita.disneyherohub3.App
import com.mantushnikita.disneyherohub3.R
import com.mantushnikita.disneyherohub3.databinding.FragmentHeroBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class HeroFragment : Fragment() {

    @Inject
    lateinit var viewModelProvider: HeroProviderFactory

    private var binding: FragmentHeroBinding? = null

    private val viewModel: HeroViewModel by viewModels {
        viewModelProvider
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeroBinding.inflate(inflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.getInt("id")?.let { heroId ->
            viewModel.processAction(HeroAction.LoadById(heroId))
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    it.let {
                        when (it) {
                            is HeroState.Loading -> {
                                binding?.progressView?.visibility = View.VISIBLE
                            }

                            is HeroState.HeroLoaded -> {
                                binding?.bannerViewHeroFragment?.showBanner(
                                    "Success",
                                    "Hero is loaded",
                                    R.drawable.ic_success,
                                    R.drawable.shape_border_green,
                                    R.color.banner_green_text
                                )
                                binding?.progressView?.visibility = View.GONE
                                binding?.run {
                                    heroImageView.run {
                                        Glide.with(requireContext())
                                            .load(it.hero.imageUrl)
                                            .into(this)
                                    }
                                    heroNameTextView.text = it.hero.name
                                    val filmTypes =
                                        listOf("film", "shortFilms", "tvShows", "videoGames")
                                    filmTypes.forEach { filmType ->
                                        val filmContent =
                                            it.hero.filmContent.find { it.name == filmType }
                                        when (filmType) {
                                            "film" -> filmsListTextView.text =
                                                filmContent?.film?.joinToString()

                                            "shortFilms" -> shortFilmsListTextView.text =
                                                filmContent?.film?.joinToString()

                                            "tvShows" -> tvShowsListTextView.text =
                                                filmContent?.film?.joinToString()

                                            "videoGames" -> videoGamesListTextView.text =
                                                filmContent?.film?.joinToString()
                                        }
                                    }
                                }
                            }

                            is HeroState.Error -> {
                                binding?.bannerViewHeroFragment?.showBanner(
                                    "Error",
                                    it.error,
                                    R.drawable.shape_border_green,
                                    R.drawable.ic_error,
                                    R.color.banner_red_text
                                )
                                binding?.progressView?.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
        binding?.reloadButton?.setOnClickListener {
            viewModel.processAction(HeroAction.Reload)
        }
    }
}