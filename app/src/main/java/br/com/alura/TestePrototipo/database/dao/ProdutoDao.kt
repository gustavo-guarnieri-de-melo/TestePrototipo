package br.com.alura.TestePrototipo.database.dao

import androidx.room.*
import br.com.alura.TestePrototipo.model.Produto
import br.com.alura.TestePrototipo.model.Usuario
import kotlinx.coroutines.flow.Flow
import java.net.URL

@Dao
interface ProdutoDao {

//    @Query("SELECT * FROM Produto")
//    fun buscaTodos(produtoId: String): Flow<List<Produto>>



    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId")
    fun buscaTodosDoUsuario(usuarioId: String) : Flow<List<Produto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg produto: Produto)

    @Delete
    suspend fun remove(produto: Produto)

    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Produto?>


//    @Query("""
//        SELECT * FROM Produto
//        WHERE nome = :nome
//        AND descricao = :descricao
//        AND valor = :valor
//        AND imagem = :imagem
//        AND tipo1 = :tipo1""")
//    suspend fun autentica(
//        nome: String,
//        descricao: String,
//        valor: String,
//        imagem: URL,
//        tipo1: String
//    ): Usuario?
}