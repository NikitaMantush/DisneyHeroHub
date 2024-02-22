package com.mantushnikita.disneyherohub3.ui.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mantushnikita.disneyherohub3.databinding.ItemHeroBinding
import com.mantushnikita.disneyherohub3.network.Hero

class HeroListViewHolder(private val binding: ItemHeroBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        hero: Hero,
        onClick: (id:Int) -> Unit
    ) {
        binding.nameHero.text = hero.name

        binding.imageHero.run {
            Glide.with(context)
                .load(hero.imageUrl)
                .into(this)
        }
        binding.root.setOnClickListener {
            onClick(hero._id)
        }
    }
}