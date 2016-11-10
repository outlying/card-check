package com.antyzero.cardcheck.job

import com.antyzero.cardcheck.TriggerConfigurator
import com.antyzero.cardcheck.dsl.extension.abs
import com.antyzero.cardcheck.dsl.extension.betweenWithMidnight
import com.antyzero.cardcheck.dsl.extension.tag
import com.antyzero.cardcheck.job.Jobs.Tags.CARD_CHECK
import com.antyzero.cardcheck.logger.Logger
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.Job
import com.firebase.jobdispatcher.Lifetime
import org.threeten.bp.LocalTime
import org.threeten.bp.temporal.ChronoUnit

class Jobs(private val dispatcher: FirebaseJobDispatcher, private val logger: Logger) {

    private val serviceClass = CardCheckJobService::class.java

    fun scheduleCardCheck(scheduleTime: LocalTime = LocalTime.of(4, 0), duration: Int = 3600) {

        val windowStart = ChronoUnit.SECONDS.betweenWithMidnight(LocalTime.now(), scheduleTime).abs().toInt()
        val windowEnd = windowStart.plus(duration).toInt()

        dispatcher.newJobBuilder().apply {
            setTag(CARD_CHECK)
            setService(serviceClass)
            setReplaceCurrent(true)

            isRecurring = true
            lifetime = Lifetime.FOREVER

            TriggerConfigurator.executionWindow(this, windowStart, windowEnd)

        }.build().let {

            if (dispatcher.schedule(it) != FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS) {
                logger.w(tag(), "Unable to schedule card check")
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