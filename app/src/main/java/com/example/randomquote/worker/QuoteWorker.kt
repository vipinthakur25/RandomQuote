package com.example.randomquote.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.randomquote.QuoteApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        Log.e("VIPIN_WORKER_INIT", "Worker Called")
        val repository = (context as QuoteApplication).quoteRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.quotesBackground()
        }
        return Result.success()
    }
}