package br.com.alura.TestePrototipo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
data class Fornecedor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val nome: String,
    val sacado: String,
    val rua: String,
    val cpf: String,
    val imagem: String? = null,
    val usuarioId: String? = null
) : Parcelable
