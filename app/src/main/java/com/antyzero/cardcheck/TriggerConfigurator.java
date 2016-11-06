package com.antyzero.cardcheck;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;

import org.jetbrains.annotations.NotNull;

/**
 * TODO remove as soon as Firebase job dispatcher provides fix for
 * https://github.com/firebase/firebase-jobdispatcher-android/issues/5
 *
 * This allows to setup proper trigger via Java
 */
public class TriggerConfigurator {

    public static void executionWindow(@NotNull Job.Builder builder, int i, int i1) {
        builder.setTrigger(Trigger.executionWindow(i, i1));
    }
}
