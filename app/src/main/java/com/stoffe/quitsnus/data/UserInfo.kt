package com.stoffe.quitsnus.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class UserInfo(
    @DocumentId val id: String? = null,
    @ServerTimestamp val createdAt: Date = Date(),
    val dosorPerDag: String = "",
    val prillorPerDosa: String = "",
    val prisPerDosa: String = "",
    val userId: String = "",
    val fails: List<Date> = mutableListOf()
)
