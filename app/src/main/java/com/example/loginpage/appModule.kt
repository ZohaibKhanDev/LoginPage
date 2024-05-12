package com.example.loginpage

import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {
    single {
        FirebaseAuth.getInstance()
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    single {
        AuthViewModel(get())
    }
}