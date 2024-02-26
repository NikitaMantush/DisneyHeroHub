import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mantushnikita.disneyherohub3.databinding.ItemHeroBinding
import com.mantushnikita.disneyherohub3.network.Hero
import com.mantushnikita.disneyherohub3.ui.list.adapter.HeroListViewHolder

class HeroListAdapter(
    private val onClick: (id:Int) -> Unit
) : ListAdapter<Hero, HeroListViewHolder>(object : DiffUtil.ItemCallback<Hero>() {
    override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
        return false
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroListViewHolder {
        return HeroListViewHolder(
            ItemHeroBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: HeroListViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }
}
