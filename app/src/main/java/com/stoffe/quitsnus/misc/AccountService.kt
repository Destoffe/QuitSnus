package com.stoffe.quitsnus.misc

interface AccountService {
    suspend fun createAnonymousAccount(onResult: (Throwable?) -> Unit)
    suspend fun authenticate(email: String, password: String)
    suspend fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit)

}