package org.d3if4003.jadwalbolaapp.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotifyWorker(val context: Context, val params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        NotificationHelper(context).createNotification(
            inputData.getString("title").toString(),
            inputData.getString("message").toString()
        )
        
        return Result.success()
    }
}