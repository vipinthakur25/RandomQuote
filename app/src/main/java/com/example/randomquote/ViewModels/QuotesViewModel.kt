package com.example.randomquote.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomquote.models.QuoteList
import com.example.randomquote.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    val quotesLiveData: LiveData<QuoteList> get() = quoteRepository._quotesLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            quoteRepository.getQuotes(1)
        }

    }
}