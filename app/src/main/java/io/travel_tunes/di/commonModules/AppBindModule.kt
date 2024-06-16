package io.travel_tunes.di.commonModules

import dagger.Binds
import dagger.Module
import io.travel_tunes.data.repository.DefaultRepository
import io.travel_tunes.data.repository.DefaultRepositoryImpl

@Module
interface AppBindModule {

    @Suppress("FunctionName")
    @Binds
    fun bindDefaultRepositoryImpl_to_DefaultRepository(
        defaultRepositoryImpl: DefaultRepositoryImpl
    ): DefaultRepository

//    companion object {
//        @Provides
//        fun provideDatabase(context: Context, appScope: CoroutineScope): InterestingPointDatabase {
//            return getDatabase(context, appScope)
//        }
//    }
}