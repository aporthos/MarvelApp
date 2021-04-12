package com.globant.marvelapp.ui.characters.favourites

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.globant.domain.model.CharactersDto
import com.globant.marvelapp.R
import com.globant.marvelapp.databinding.FragmentFavouritesBinding
import com.globant.marvelapp.ui.characters.CharactersAdapter
import com.globant.marvelapp.ui.common.BaseFragment
import com.globant.shared.interfaces.OnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>(),
    OnClickListener<CharactersDto> {

    private val viewModel: FavouritesViewModel by viewModels()

    private lateinit var charactersAdapter: CharactersAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_favourites

    override fun initializeView() {
        dataBinding().viewModel = viewModel
        lifecycle.addObserver(viewModel)

        charactersAdapter = CharactersAdapter(click = this)
        dataBinding().favouritesRecyclerView.adapter = charactersAdapter
    }

    override fun initObservers() {
        viewModel.charactersList.observe(viewLifecycleOwner, Observer { characters ->
            dataBinding().hasFavourites = characters.isNullOrEmpty()
            charactersAdapter.submitList(characters)
        })
    }

    override fun onClicked(v: View, item: CharactersDto) {
        val idCharacter =
            FavouritesFragmentDirections.actionNavigationFavouritesToNavigationCharacterDetail(idCharacter = item.id)
        v.findNavController().navigate(idCharacter)
    }

    override fun onChecked(v: View, item: CharactersDto) {
        viewModel.toUpdateCharacter(item)
    }
}