package com.example.randomquote

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.randomquote.api.QuoteService
import com.example.randomquote.api.RetrofitHelper
import com.example.randomquote.db.QuoteDatabase
import com.example.randomquote.repository.QuoteRepository
import com.example.randomquote.worker.QuoteWorker
import java.util.concurrent.TimeUnit

class QuoteApplication : Application() {
    lateinit var quoteRepository: QuoteRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
        setupWorker()
    }

    private fun setupWorker() {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = PeriodicWorkRequest.Builder(QuoteWorker::class.java, 6000, TimeUnit.MILLISECONDS)
            .setConstraints(constraint)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun initialize() {
        val service = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val database = QuoteDatabase.getDatabase(applicationContext)
        quoteRepository = QuoteRepository(service, database, applicationContext)
    }
}