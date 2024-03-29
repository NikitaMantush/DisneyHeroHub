package com.mantushnikita.disneyherohub3.ui.hero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mantushnikita.disneyherohub3.databinding.FragmentHeroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroFragment : Fragment() {

    private var binding: FragmentHeroBinding? = null

    private val viewModel: HeroViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeroBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("id")?.let { heroId ->
            viewModel.processAction(HeroAction.LoadById(heroId))
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HeroState.Loading -> {
                    binding?.progressView?.visibility = View.VISIBLE
                }

                is HeroState.HeroLoaded -> {
                    binding?.progressView?.visibility = View.GONE
                    binding?.run {
                        heroImageView.run {
                            Glide.with(requireContext())
                                .load(state.hero.imageUrl)
                                .into(this)
                        }
                        heroNameTextView.text = state.hero.name
                        val filmTypes = listOf("film", "shortFilms", "tvShows", "videoGames")
                        filmTypes.forEach { filmType ->
                            val filmContent = state.hero.filmContent.find { it.name == filmType }
                            when (filmType) {
                                "film" -> filmsListTextView.text = filmContent?.film?.joinToString()
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
                    binding?.progressView?.visibility = View.GONE
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }
            }
        }
        binding?.reloadButton?.setOnClickListener {
            viewModel.processAction(HeroAction.Reload)
        }
    }
}