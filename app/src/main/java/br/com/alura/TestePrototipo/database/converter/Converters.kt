package br.com.alura.TestePrototipo.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {

    @TypeConverter
    fun deDouble(valor: Double?) : BigDecimal {
       return valor?.let { BigDecimal(valor.toString()) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun bigDecimalParaDouble(valor: BigDecimal?) : Double? {
        return valor?.let { valor.toDouble() }
    }
    @TypeConverter
    fun fromList(tipos: List<String>?): String? {
        return tipos?.joinToString(",")
    }

    @TypeConverter
    fun toList(tiposString: String?): List<String>? {
        return tiposString?.split(",")?.map { it }
    }

}