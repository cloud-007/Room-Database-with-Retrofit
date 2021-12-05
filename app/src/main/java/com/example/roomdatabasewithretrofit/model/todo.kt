package com.example.roomdatabasewithretrofit.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "todo_table")
data class Todo (
    val completed: Boolean,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var userId: Int
): Parcelable