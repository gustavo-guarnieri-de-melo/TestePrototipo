package br.com.alura.TestePrototipo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
data class Produto(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val nome: String,
        val descricao: String,
        val tipo: String,
        val valor: BigDecimal,
        val imagem: String? = null,
        val usuarioId: String? = null
) : Parcelable
