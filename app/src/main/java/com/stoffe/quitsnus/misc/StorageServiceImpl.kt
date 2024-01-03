package com.stoffe.quitsnus.misc

import androidx.core.os.trace
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.dataObjects
import com.stoffe.quitsnus.data.UserInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService,
): StorageService
{

    private val collection get() = firestore.collection(USER_INFO)
        .whereEqualTo(USER_ID_FIELD,auth.currentUser)
    @OptIn(ExperimentalCoroutinesApi::class)
    override val userInfo: Flow<List<UserInfo>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore
                    .collection(USER_INFO)
                    .whereEqualTo(USER_ID_FIELD, user.id)
                    .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }

    override suspend fun getUserInfo(userInfoID: String): UserInfo? =
        firestore.collection(USER_INFO).document(userInfoID).get().await().toObject(UserInfo::class.java)


    override val userInfoTest: Flow<UserInfo?>
        get() = flow {
            auth.currentUser.collect { user ->
                val userInfo = firestore
                    .collection(USER_INFO)
                    .document()
                    .get()
                    .await()
                    .toObject(UserInfo::class.java)
                emit(userInfo)
            }
        }


    override suspend fun save(userInfo: UserInfo): String =
        trace(SAVE_USER_INFO_TRACE) {
            val updatedTask = userInfo.copy(userId = auth.currentUserId)
            firestore.collection(USER_INFO).add(updatedTask).await().id
        }

    override suspend fun update(userInfo: UserInfo) {
        trace(UPDATE_USER_INFO_TRACE) {
            firestore.collection(USER_INFO).document(userInfo.id!!).set(userInfo).await()
        }
    }

    override suspend fun delete(collectionID: String) {
        firestore.collection(USER_INFO).document(collectionID).delete().await()
    }


    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val COMPLETED_FIELD = "completed"
        private const val PRIORITY_FIELD = "priority"
        private const val FLAG_FIELD = "flag"
        private const val CREATED_AT_FIELD = "createdAt"
        private const val USER_INFO = "userInfo"
        private const val SAVE_USER_INFO_TRACE = "saveUserInfo"
        private const val UPDATE_USER_INFO_TRACE = "updateUserInfo"
    }
}