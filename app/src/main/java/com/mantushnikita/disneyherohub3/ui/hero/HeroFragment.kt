package com.mantushnikita.disneyherohub3.ui.hero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mantushnikita.disneyherohub3.databinding.FragmentHeroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroFragment: Fragment() {

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
            viewModel.getHeroById(heroId)
        }
        viewModel.hero.observe(viewLifecycleOwner) { hero ->
            binding?.run {
                heroImageView.run {
                    Glide.with(requireContext())
                        .load(hero.imageUrl)
                        .into(this)
                }
                heroNameTextView.text = hero.name
                val filmTypes = listOf("film", "shortFilms", "tvShows", "videoGames")
                filmTypes.forEach { filmType ->
                    val filmContent = hero.filmContent.find { it.name == filmType }
                    when (filmType) {
                        "film" -> filmsListTextView.text = filmContent?.film?.joinToString()
                        "shortFilms" -> shortFilmsListTextView.text = filmContent?.film?.joinToString()
                        "tvShows" -> tvShowsListTextView.text = filmContent?.film?.joinToString()
                        "videoGames" -> videoGamesListTextView.text = filmContent?.film?.joinToString()
                    }
                }
            }
        }
    }
}