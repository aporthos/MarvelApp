package com.globant.marvelapp.ui.characters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.globant.domain.model.CharactersDto
import com.globant.marvelapp.R
import com.globant.marvelapp.databinding.ItemCharactersBinding
import com.globant.shared.interfaces.OnClickListener
import com.globant.shared.utils.extensions.binding
import com.globant.shared.presentation.iu.common.BaseClickViewHolder

class CharactersAdapter(
    private val click: OnClickListener<CharactersDto>
) : PagedListAdapter<CharactersDto, CharactersAdapter.CharactersViewHolder>(
    CharactersAdapterDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(parent.binding(R.layout.item_characters))
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val repoItem = getItem(position)
        repoItem?.let {
            holder.bind(it, holder.binding, click)
            holder.binding.executePendingBindings()
        }
    }

    inner class CharactersViewHolder(val binding: ItemCharactersBinding) :
        BaseClickViewHolder<CharactersDto, ItemCharactersBinding>(binding = binding) {
        override fun bind(
            item: CharactersDto,
            binding: ItemCharactersBinding,
            click: OnClickListener<CharactersDto>
        ) {
            binding.item = item
            binding.listener = click
        }
    }
}

object CharactersAdapterDiff : DiffUtil.ItemCallback<CharactersDto>() {
    override fun areItemsTheSame(oldItem: CharactersDto, newItem: CharactersDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharactersDto, newItem: CharactersDto): Boolean {
        return oldItem == newItem
    }
}
