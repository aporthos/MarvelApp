package com.globant.marvelapp.ui.characters.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.globant.domain.model.CharactersDto
import com.globant.marvelapp.R
import com.globant.marvelapp.databinding.FragmentCharacterDetailBinding
import com.globant.marvelapp.ui.common.BaseFragment
import com.globant.shared.utils.extensions.browse
import com.globant.shared.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding>() {

    private val viewModel: CharacterDetailViewModel by viewModels()

    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun getLayoutRes(): Int = R.layout.fragment_character_detail

    override fun initializeView() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        dataBinding().toolbar.setupWithNavController(navController, appBarConfiguration)

        dataBinding().toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_save -> {
                    viewModel.saveCharacter()
                    true
                }
                else -> false
            }
        }

        setHasOptionsMenu(true)
        dataBinding().viewModel = viewModel
        lifecycle.addObserver(viewModel)
        viewModel.initSearch(args.idCharacter)
    }

    override fun initObservers() {
        viewModel.character.observe(viewLifecycleOwner, Observer {
            val result = it ?: return@Observer
            resultCharacterSearch(result)
        })
        viewModel.link.observe(viewLifecycleOwner, Observer {
            validateLink(it)
        })

        viewModel.messageError.observe(viewLifecycleOwner, Observer {
            toast(getString(it))
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                showLoader()
            } else {
                hideLoader()
            }
        })
    }

    private fun resultCharacterSearch(character: CharactersDto) {
        val toolbar = dataBinding().toolbar.menu
        toolbar.findItem(R.id.action_save).isVisible = !character.isFavourite
        dataBinding().character = character
    }

    private fun validateLink(link: String?) {
        link?.let {
            browse(link)
        } ?: run {
            toast(R.string.message_link_invalid)
        }
    }
}