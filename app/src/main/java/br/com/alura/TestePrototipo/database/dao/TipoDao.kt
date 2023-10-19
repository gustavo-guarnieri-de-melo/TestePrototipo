package br.com.alura.TestePrototipo.database.dao

import androidx.room.*
import br.com.alura.TestePrototipo.model.Produto
import br.com.alura.TestePrototipo.model.Tipo
import kotlinx.coroutines.flow.Flow
@Dao
interface TipoDao {

    @Insert
    suspend fun salva(tipo: Tipo)

    @Query("SELECT * FROM Produto WHERE tipo1 = :produtoId")
    fun buscaTodosDoProduto(produtoId: String) : Flow<List<Produto>>

    @Query("SELECT * FROM Tipo WHERE tipo = :tipoId")
    fun buscaPorId(tipoId: String): Flow<Tipo>


//    @Query("SELECT * FROM Tipo WHERE tipoId = :tipoId")
//    fun buscaTodosDoProduto(tipoId: String) : Flow<List<Tipo>>

}