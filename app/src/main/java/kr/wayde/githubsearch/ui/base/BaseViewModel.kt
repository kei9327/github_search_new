package kr.wayde.githubsearch.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {
    val mJob = Job()

    override val coroutineContext: CoroutineContext
            = Dispatchers.Main + mJob

    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun getDisposable() = compositeDisposable

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}