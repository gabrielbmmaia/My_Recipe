package com.example.myrecipe.ui.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val titulo: String,
    val ingrediente: String,
    val preparo: String,
    val imagemUrl: String? = null
) : Parcelable