package kr.wayde.githubsearch.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.wayde.githubserach.domain.schdulers.SchedulersProvider

class AppSchedulerProvider : SchedulersProvider {

    override fun io(): Scheduler = Schedulers.io()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}
