package br.com.alura.TestePrototipo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
data class Tipo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val tipo: String,
    val descricao: String,
) : Parcelable
