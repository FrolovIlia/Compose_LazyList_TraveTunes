package io.travel_tunes.di.commonModules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.travel_tunes.R
import io.travel_tunes.di.FirebaseDBInfo

@Module
class ConstantsModule {

    @FirebaseDBInfo
    @Provides
    fun provideFirebaseDBPath(context: Context): String {
        return context.resources.getString(R.string.fb_db_path)
    }
}