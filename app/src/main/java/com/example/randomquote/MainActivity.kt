package com.example.randomquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.randomquote.ViewModels.QuotesViewModel
import com.example.randomquote.ViewModels.QuotesViewModelFactory
import com.example.randomquote.api.QuoteService
import com.example.randomquote.api.RetrofitHelper
import com.example.randomquote.repository.QuoteRepository

class MainActivity : AppCompatActivity() {
    lateinit var quotesViewModel: QuotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val repository = (application as QuoteApplication).quoteRepository

        quotesViewModel = ViewModelProvider(this, QuotesViewModelFactory(repository)).get(QuotesViewModel::class.java)

        quotesViewModel.quotesLiveData.observe(this, {
           Toast.makeText(this, ""+it.results.size.toString(), Toast.LENGTH_SHORT).show()
        })
    }


}