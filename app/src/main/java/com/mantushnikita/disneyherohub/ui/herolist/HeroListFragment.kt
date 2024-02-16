package com.mantushnikita.disneyherohub.ui.herolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mantushnikita.disneyherohub.databinding.FragmentHeroListBinding
import com.mantushnikita.disneyherohub.model.Hero
import com.mantushnikita.disneyherohub.ui.herolist.adapter.HeroListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroListFragment: Fragment() {

    private var binding: FragmentHeroListBinding? = null

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
        setHero(arrayListOf())
    }
    private fun setHero(listHero: ArrayList<Hero>){
        binding?.recyclerView?.run{
            if(adapter == null){
                layoutManager = LinearLayoutManager(requireContext())
                adapter = HeroListAdapter()
                (adapter as? HeroListAdapter)?.submitList(listHero)
            }
        }
    }
}