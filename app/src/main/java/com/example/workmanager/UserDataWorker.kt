package com.example.workmanager

import android.content.Context
import android.os.Handler
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class UserDataWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {

        val firstName = inputData.getString("firstName")
        val lastName = inputData.getString("lastName")
        val fullName = uploadUserData(firstName, lastName)

       Thread.sleep(3000)

        val outputData = workDataOf("fullName" to fullName)
        return Result.failure(outputData)
    }

    private fun uploadUserData(firstName: String?, lastName: String?): String {
       return firstName+lastName
    }
}