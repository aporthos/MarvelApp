package com.globant.marvelapp.ui.characters.list

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.globant.domain.model.CharactersDto
import com.globant.marvelapp.R
import com.globant.marvelapp.databinding.FragmentCharactersBinding
import com.globant.marvelapp.ui.characters.CharactersAdapter
import com.globant.marvelapp.ui.common.BaseFragment
import com.globant.marvelapp.ui.helpers.QueryTextListenerHelper
import com.globant.shared.interfaces.OnClickListener
import com.globant.shared.utils.extensions.hideSoftKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding>(),
    OnClickListener<CharactersDto> {

    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var charactersAdapter: CharactersAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_characters

    override fun initializeView() {
        dataBinding().viewModel = viewModel
        lifecycle.addObserver(viewModel)
        toConfigureSearch()
        charactersAdapter = CharactersAdapter(click = this)
        dataBinding().charactersRecyclerView.adapter = charactersAdapter
    }

    override fun initObservers() {
        viewModel.characterList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                dataBinding().messageTextView.isVisible = true
                dataBinding().messageTextView.text = getString(R.string.message_characters_not_found)
                dataBinding().imageView3.isVisible = true
            } else {
                dataBinding().messageTextView.isVisible = false
                dataBinding().imageView3.isVisible = false
            }
            charactersAdapter.submitList(it)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                showLoader()
            } else {
                hideLoader()
            }
        })

        viewModel.messageErrorInternet.observe(viewLifecycleOwner, Observer { errorInternet ->
            if (errorInternet) {
                dataBinding().messageTextView.text = getString(R.string.message_no_internet)
                dataBinding().imageView3.isVisible = true
                dataBinding().messageTextView.isVisible = true
            }
        })
    }

    private fun toConfigureSearch() {
        dataBinding().toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_order -> {
                    viewModel.toSearchCharacters()
                    hideSoftKeyboard()
                    false
                }
                else -> false
            }
        }
        val searchMenuItem = dataBinding().toolbar.menu.findItem(R.id.action_search)
        val searchView =
            dataBinding().toolbar.menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : QueryTextListenerHelper() {
            override fun executeQuery(query: String) {
                viewModel.search(query)
                hideSoftKeyboard()
            }
        })

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                dataBinding().charactersSwipeRefresh.isEnabled = false
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                dataBinding().charactersSwipeRefresh.isEnabled = true
                viewModel.refresh()
                return true
            }
        })
    }

    override fun onClicked(v: View, item: CharactersDto) {
        val idCharacter =
            CharactersFragmentDirections.actionNavigationHomeToNavigationCharacterDetail(
                idCharacter = item.id
            )
        v.findNavController().navigate(idCharacter)
    }

    override fun onChecked(v: View, item: CharactersDto) {
        viewModel.toUpdateCharacter(item)
    }
}