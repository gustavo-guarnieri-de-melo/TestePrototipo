package br.com.alura.TestePrototipo.database.dao

import androidx.room.*
import br.com.alura.TestePrototipo.model.Cliente
import kotlinx.coroutines.flow.Flow
@Dao
interface ClienteDao {

    @Query("SELECT * FROM Cliente")
    fun buscaTodos(): Flow<List<Cliente>>

    @Query("SELECT * FROM Cliente WHERE usuarioId = :usuarioId")
    fun buscaTodosDoUsuario(usuarioId: String) : Flow<List<Cliente>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg cliente: Cliente)

    @Delete
    suspend fun remove(cliente: Cliente)

    @Query("SELECT * FROM Cliente WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Cliente?>

}