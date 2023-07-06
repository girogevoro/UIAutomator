package com.geekbrains.tests

import io.reactivex.rxjava3.core.Scheduler


internal interface SchedulerProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
}