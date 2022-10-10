package com.example.myrecipe.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val titulo: String,
    val ingrediente: String,
    val preparo: String,
    val imagemUrl: String? = null
)