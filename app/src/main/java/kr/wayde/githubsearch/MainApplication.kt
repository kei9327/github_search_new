package kr.wayde.githubsearch

import android.app.Application
import android.os.Handler
import android.util.Log
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.stetho.Stetho
import com.uber.rxdogtag.RxDogTag
import io.reactivex.plugins.RxJavaPlugins
import kr.wayde.githubsearch.di.appModule
import kr.wayde.githubsearch.di.dataModule
import kr.wayde.githubsearch.di.domainModule
import kr.wayde.githubsearch.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import timber.log.Timber
import java.io.PrintWriter
import java.io.StringWriter

class MainApplication : Application(), Thread.UncaughtExceptionHandler {
    private var mSystemUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        mSystemUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)

        Stetho.initializeWithDefaults(this)
        Fresco.initialize(this)

        RxDogTag.install()
        RxJavaPlugins.setErrorHandler {
            Log.w("Wayde Debug#", it)
        }

        startKoin {
            androidContext(this@MainApplication)
            loadKoinModules(listOf(appModule, viewModelModule, domainModule, dataModule))
        }
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        val background = mainLooper.thread !== thread
        if (background) {
            Log.e(this.javaClass.simpleName, "Uncaught exception in background thread $thread", ex)
            val handler = Handler(mainLooper)
            handler.post { mSystemUncaughtExceptionHandler?.uncaughtException(thread, ex) }
        } else {
            val sw = StringWriter()
            ex.printStackTrace(PrintWriter(sw))
            val exceptionAsStrting = sw.toString()
            Log.e(this.javaClass.simpleName, "Uncaught exception $exceptionAsStrting")
            mSystemUncaughtExceptionHandler?.uncaughtException(thread, ex)
        }
    }
}