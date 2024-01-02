package com.stoffe.quitsnus.misc

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) :AccountService {
    override suspend fun createAnonymousAccount(onResult: (Throwable?) -> Unit) {
        auth.signInAnonymously()
            .addOnCompleteListener { onResult(it.exception) }
    }

    override suspend fun authenticate(email: String, password: String) {
       auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)

       auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { onResult(it.exception) }
    }
}