package com.azhapps.listapp.lists.model

import java.io.Serializable

data class Category(
    val name: String,
    val id: Int = -1,
) : Serializable