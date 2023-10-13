package br.com.alura.TestePrototipo.database.dao

import androidx.room.*
import br.com.alura.TestePrototipo.model.Fornecedor
import kotlinx.coroutines.flow.Flow
@Dao
interface FornecedorDao {

    @Query("SELECT * FROM Fornecedor")
    fun buscaTodos(): Flow<List<Fornecedor>>

    @Query("SELECT * FROM Fornecedor WHERE usuarioId = :usuarioId")
    fun buscaTodosDoUsuario(usuarioId: String) : Flow<List<Fornecedor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg fornecedor: Fornecedor)

    @Delete
    suspend fun remove(fornecedor: Fornecedor)

    @Query("SELECT * FROM Fornecedor WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Fornecedor?>

}