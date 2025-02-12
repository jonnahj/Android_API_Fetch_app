package com.example.fetch_assessment.Entities

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: String,
    val listId: String,
    val name: String
)
