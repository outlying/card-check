package com.antyzero.cardcheck.job

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

class CardCheckJobService() : JobService() {

    override fun onStartJob(job: JobParameters): Boolean {
        return false
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }
}