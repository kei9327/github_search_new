package kr.wayde.githubsearch.di

import androidx.room.Room
import kr.wayde.githubsearch.BuildConfig
import kr.wayde.githubsearch.ui.favorite.FavoriteViewModel
import kr.wayde.githubsearch.ui.search.UserSearchViewModel
import kr.wayde.githubsearch.util.AppSchedulerProvider
import kr.wayde.githubserach.data.interactor.GithubDataSource
import kr.wayde.githubserach.data.repository.GithubLocal
import kr.wayde.githubserach.data.repository.GithubRemote
import kr.wayde.githubserach.domain.interactor.usercases.*
import kr.wayde.githubserach.domain.repository.GithubRepository
import kr.wayde.githubserach.domain.schdulers.SchedulersProvider
import kr.wayde.githubserach.local.GithubLocalImpl
import kr.wayde.githubserach.local.room.AppDBHelper
import kr.wayde.githubserach.local.room.AppDatabase
import kr.wayde.githubserach.remote.GithubRemoteImpl
import kr.wayde.githubserach.remote.rest.GithubServiceFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    single { AppSchedulerProvider() as SchedulersProvider }
}

var viewModelModule = module {
    single { UserSearchViewModel(get(), get(), get(), get()) }
    single { FavoriteViewModel(get(), get(), get()) }
}

val domainModule = module {
    single {LoadLocalAllUsersUsecase(get(), get())}
    single {InsertUserUsecase(get(), get())}
    single {DeleteUserUsecase(get(), get())}
    single {GithubUserSearchUseCase(get(), get())}
    single {CheckExistedUserUsecase(get(), get())}

}

val dataModule = module {
    single { GithubDataSource(get(), get()) as GithubRepository }

    single { GithubLocalImpl(get()) as GithubLocal }
    single { GithubRemoteImpl(get()) as GithubRemote}

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "github.db")
            .fallbackToDestructiveMigration()
            .build() as AppDatabase
    }

    single {
        AppDBHelper(get())
    }


    single {
        GithubServiceFactory.makeService(
            BuildConfig.DEBUG,
            "https://api.github.com",
            androidContext())
    }

}