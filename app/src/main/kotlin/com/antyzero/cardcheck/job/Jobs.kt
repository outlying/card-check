package com.antyzero.cardcheck.job

import android.util.Log
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
            setReplaceCurrent(true)

            isRecurring = true
            lifetime = Lifetime.FOREVER

            // TODO This should be replaced in future, check TriggerConfigurator for details
            TriggerConfigurator.executionWindow(this, 15 * 60, 20 * 60)


        }.build().let {

            // TODO should we check ?
            if (dispatcher.schedule(it) != FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS) {
                // TODO create proper logger
                Log.w("Jobs", "Unable to schedule job $it")
            }
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

        companion object {
            fun findByName(name: String): Tags {
                values().forEach {
                    if (it.name == name) {
                        return it
                    }
                }
                throw IllegalArgumentException("No enum value found for: $name")
            }
        }
    }

    private fun Job.Builder.setTag(tag: Tags) = this.setTag(tag.name)
}