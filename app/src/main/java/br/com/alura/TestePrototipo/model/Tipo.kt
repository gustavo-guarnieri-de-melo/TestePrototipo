package br.com.alura.TestePrototipo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
data class Tipo(
    @PrimaryKey (autoGenerate = true)
    val tipoId: Int,
    val tipo: String,
    val produtoId: String
)