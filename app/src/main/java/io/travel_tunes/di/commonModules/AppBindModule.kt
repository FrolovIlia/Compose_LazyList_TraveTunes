package io.travel_tunes.di.commonModules

import dagger.Binds
import dagger.Module
import io.travel_tunes.data.repository.DefaultRepository
import io.travel_tunes.data.repository.DefaultRepositoryImpl
import io.travel_tunes.data.repository.PaymentsRepository
import io.travel_tunes.data.repository.PaymentsRepositoryImpl

@Module
interface AppBindModule {

    @Suppress("FunctionName")
    @Binds
    fun bindDefaultRepositoryImpl_to_DefaultRepository(
        defaultRepositoryImpl: DefaultRepositoryImpl
    ): DefaultRepository

    @Suppress("FunctionName")
    @Binds
    fun bindPaymentsRepositoryImpl_to_PaymentsRepository(
        paymentsRepositoryImpl: PaymentsRepositoryImpl
    ): PaymentsRepository

//    companion object {
//        @Provides
//        fun provideDatabase(context: Context, appScope: CoroutineScope): InterestingPointDatabase {
//            return getDatabase(context, appScope)
//        }
//    }
}