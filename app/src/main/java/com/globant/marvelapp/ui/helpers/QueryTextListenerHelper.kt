package com.globant.marvelapp.ui.helpers

import androidx.appcompat.widget.SearchView

abstract class QueryTextListenerHelper : SearchView.OnQueryTextListener {
    override fun onQueryTextChange(query: String?): Boolean = false

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            executeQuery(query)
        }
        return false
    }

    abstract fun executeQuery(query: String)
}