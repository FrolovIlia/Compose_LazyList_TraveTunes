package io.travel_tunes.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import io.travel_tunes.di.commonModules.AppBindModule
import io.travel_tunes.di.commonModules.ConstantsModule
import io.travel_tunes.di.commonModules.NetworkModule
import io.travel_tunes.ui.activities.MainActivity
import io.travel_tunes.ui.fragments.payments.PaymentsFragment
import io.travel_tunes.ui.fragments.points.info.PointInfoFragment
import io.travel_tunes.ui.fragments.restore.RestoreFragment
import io.travel_tunes.ui.fragments.routes.list.RoutesFragment
import io.travel_tunes.ui.fragments.routes.route_info.RouteInfoFragment
import io.travel_tunes.ui.fragments.routes.route_map.RouteMapFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: RoutesFragment)
    fun inject(fragment: RouteInfoFragment)
    fun inject(fragment: RouteMapFragment)
    fun inject(fragment: PaymentsFragment)
    fun inject(fragment: PointInfoFragment)
    fun inject(fragment: RestoreFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}

@Module(includes = [NetworkModule::class, AppBindModule::class, ConstantsModule::class])
class AppModule