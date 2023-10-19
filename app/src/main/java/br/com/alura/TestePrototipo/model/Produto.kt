package br.com.alura.TestePrototipo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.alura.TestePrototipo.database.converter.Converters
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
data class Produto(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val nome: String,
        val descricao: String,
        val valor: BigDecimal,
        val tipo1: String,
//        val tipo2: String,
//        val tipo3: String,
//        val tipo4: String,
//        val tipo5: String,
        val imagem: String? = null,
        val usuarioId: String? = null,
) : Parcelable
