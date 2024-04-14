package com.mantushnikita.disneyherohub3.ui.list

import HeroListAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mantushnikita.disneyherohub3.App
import com.mantushnikita.disneyherohub3.databinding.FragmentHeroListBinding
import com.mantushnikita.disneyherohub3.network.Hero
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class HeroListFragment : Fragment() {

    @Inject
    lateinit var viewModelProvider: HeroListProviderFactory

    private var binding: FragmentHeroListBinding? = null

    private val viewModel: HeroListViewModel by viewModels {
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
        binding = FragmentHeroListBinding.inflate(inflater)
        return binding?.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest {
                    it.let {
                        when (it) {
                            is HeroListState.Loading -> {
                                binding?.progressView?.visibility = View.VISIBLE

                            }

                            is HeroListState.HeroListLoaded -> {
                                binding?.progressView?.visibility = View.GONE
                                setList(it.heroList)
                            }

                            is HeroListState.Error -> {
                                binding?.progressView?.visibility = View.GONE
                                Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
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
                    findNavController().navigate(
                        HeroListFragmentDirections.actionHeroListFragmentToHeroFragment(
                            heroId
                        )
                    )
                }
            }
            (adapter as? HeroListAdapter)?.submitList(list)
        }
    }
}