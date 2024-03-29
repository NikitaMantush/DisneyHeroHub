package com.mantushnikita.disneyherohub3.ui.list

import HeroListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.mantushnikita.disneyherohub3.R
import com.mantushnikita.disneyherohub3.databinding.FragmentHeroListBinding
import com.mantushnikita.disneyherohub3.network.Hero
import com.mantushnikita.disneyherohub3.ui.hero.HeroFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroListFragment : Fragment() {

    private var binding: FragmentHeroListBinding? = null
    private val viewModel: HeroListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeroListBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HeroListState.Loading -> {
                    binding?.progressView?.visibility = View.VISIBLE

                }

                is HeroListState.HeroListLoaded -> {
                    binding?.progressView?.visibility = View.GONE
                    setList(state.heroList)
                }

                is HeroListState.Error -> {
                    binding?.progressView?.visibility = View.GONE
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }
            }
        }
        viewModel.processAction(HeroListAction.Init)
    }

    private fun setList(list: List<Hero>) {
        binding?.recyclerView?.run {
            if (adapter == null) {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = HeroListAdapter { heroId ->
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, HeroFragment().apply {
                            arguments = bundleOf("id" to heroId)
                        })
                        .addToBackStack(null)
                        .commit()
                }
            }
            (adapter as? HeroListAdapter)?.submitList(list)
        }
    }
}