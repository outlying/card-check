package com.antyzero.cardcheck.job

import com.antyzero.cardcheck.extension.applicationComponent
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

class CardCheckJobService() : JobService(){

    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }

    override fun onStartJob(job: JobParameters): Boolean {
        return false
    }
}