package br.com.alura.TestePrototipo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey
    val usuario: String,
    val nome: String,
    val senha: String
)
