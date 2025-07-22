package org.example.model

data class Product(
    val id: Long,
    val nome: String,
    val preco: Double? = null
)
