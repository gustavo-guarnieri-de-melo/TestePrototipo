package br.com.alura.TestePrototipo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.TestePrototipo.database.converter.Converters
import br.com.alura.TestePrototipo.database.dao.FornecedorDao
import br.com.alura.TestePrototipo.database.dao.ProdutoDao
import br.com.alura.TestePrototipo.database.dao.UsuarioDao
import br.com.alura.TestePrototipo.model.Fornecedor
import br.com.alura.TestePrototipo.model.Produto
import br.com.alura.TestePrototipo.model.Usuario

@Database(
    entities = [
        Produto::class,
        Usuario::class,
        Fornecedor::class
    ],
    version = 4,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fornecedorDao(): FornecedorDao
    abstract fun produtoDao(): ProdutoDao

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3,
                MIGRATION_3_4
            ).build().also {
                db = it
            }
        }
    }
}