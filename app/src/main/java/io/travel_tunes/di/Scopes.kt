package io.travel_tunes.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TokenInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ChuckInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FirebaseDBInfo