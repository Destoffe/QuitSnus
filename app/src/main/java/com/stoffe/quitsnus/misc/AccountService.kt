package com.stoffe.quitsnus.misc

import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String

    val currentUser: Flow<User>

    val hasUser: Boolean

    suspend fun createAnonymousAccount()
    suspend fun authenticate(email: String, password: String)
    suspend fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit)

    suspend fun signOut()

}