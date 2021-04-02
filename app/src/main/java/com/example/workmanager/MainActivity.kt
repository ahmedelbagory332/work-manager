package com.example.workmanager

 import android.content.ContentValues.TAG
 import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
 import android.util.Log
 import android.view.View
import android.widget.TextView
 import androidx.lifecycle.Observer
 import androidx.work.*

class MainActivity : AppCompatActivity() {
    lateinit var  tv: TextView
    lateinit var  oneTimeWorkRequest: OneTimeWorkRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.textView)

        val inputData = Data.Builder()
                .putString("firstName", "ahmed")
                .putString("lastName", " nader")
                .build()

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

          oneTimeWorkRequest = OneTimeWorkRequestBuilder<UserDataWorker>()
                .setConstraints(constraints)
                .setInputData(inputData)
                .build()

        WorkManager.getInstance(applicationContext)
                .getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this, Observer { workInfo ->
                   if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                       tv.text = workInfo.outputData.getString("fullName")
                    } else if (workInfo.state == WorkInfo.State.RUNNING)  {
                       tv.text = "RUNNING"
                    } else if (workInfo.state == WorkInfo.State.FAILED)  {
                       tv.text = "${workInfo.outputData.getString("fullName")} FAILED"
                   }



                 })

    }

     fun start(view: View) {

         // to start work once
        WorkManager.getInstance(applicationContext).enqueue(oneTimeWorkRequest)

         // to start work again
//         WorkManager.getInstance(applicationContext).beginUniqueWork("UserDataWorker",
//                 ExistingWorkPolicy.REPLACE,
//                 oneTimeWorkRequest).enqueue()


    }
}