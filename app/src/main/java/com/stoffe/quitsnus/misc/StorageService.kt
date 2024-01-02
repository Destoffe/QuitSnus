package com.stoffe.quitsnus.misc

import com.stoffe.quitsnus.data.UserInfo
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val userInfo: Flow<List<UserInfo>>
    suspend fun getUserInfo(userInfoID: String): UserInfo?
    suspend fun save(userInfo: UserInfo): String
    suspend fun update(userInfo: UserInfo)
    suspend fun delete(userInfoID: String)

}