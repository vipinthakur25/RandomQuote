package com.example.randomquote.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.randomquote.api.QuoteService
import com.example.randomquote.db.QuoteDatabase
import com.example.randomquote.models.QuoteList
import com.example.randomquote.utils.NetworkUtils

class QuoteRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val applicationContext: Context
) {

    private val quotesLiveData = MutableLiveData<QuoteList>()

    val _quotesLiveData: LiveData<QuoteList> get() = quotesLiveData

    suspend fun getQuotes(pages: Int) {

        if (NetworkUtils.isNetworkAvailable(applicationContext)) {
            val result = quoteService.getQuotes(pages)
            if (result?.body() != null) {
                quoteDatabase.quotesDao().addQuote(result.body()!!.results)
                quotesLiveData.postValue(result.body())
            }
        } else {
            val quotes = quoteDatabase.quotesDao().getQuotes()
            val quoteList = QuoteList(1, 1, 1, quotes, 1, 1)
            quotesLiveData.postValue(quoteList)
        }


    }

    suspend fun quotesBackground(){
        val randomNumber = (Math.random() *10).toInt()
        val result = quoteService.getQuotes(randomNumber)
        if (result?.body() !=null){
            quoteDatabase.quotesDao().addQuote(result.body()!!.results)
        }

    }
}