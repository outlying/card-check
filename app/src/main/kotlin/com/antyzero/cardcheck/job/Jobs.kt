package com.antyzero.cardcheck.job

import com.antyzero.cardcheck.TriggerConfigurator
import com.antyzero.cardcheck.job.Jobs.Tags.CARD_CHECK
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.Job
import com.firebase.jobdispatcher.Lifetime

class Jobs(private val dispatcher: FirebaseJobDispatcher) {

    private val serviceClass = CardCheckJobService::class.java

    fun scheduleCardCheck() {


        dispatcher.newJobBuilder().apply {
            setTag(CARD_CHECK)
            setService(serviceClass)

            isRecurring = true
            lifetime = Lifetime.FOREVER

            // TODO This should be replaced in future, check TriggerConfigurator for details
            TriggerConfigurator.executionWindow(this, 123, 123)


        }.build().let {
            dispatcher.mustSchedule(it)
        }
    }

    /**
     * Register of possible tags
     */
    enum class Tags {
        CARD_CHECK;

        override fun toString(): String {
            return name
        }
    }

    private fun Job.Builder.setTag(tag: Tags) = this.setTag(tag.name)
}