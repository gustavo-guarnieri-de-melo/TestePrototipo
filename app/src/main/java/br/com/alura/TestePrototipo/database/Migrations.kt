package br.com.alura.TestePrototipo.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `Usuario` (
            `id` TEXT NOT NULL, 
            `nome` TEXT NOT NULL, 
            `senha` TEXT NOT NULL, PRIMARY KEY(`id`))
            """)
    }

}

val MIGRATION_2_3 = object : Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Produto ADD COLUMN 'usuarioId' TEXT")
    }

}
val MIGRATION_3_4 = object : Migration(3, 4) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Produto ADD COLUMN tipo TEXT NOT NULL DEFAULT ''")
    }

}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Crie a nova tabela Fornecedor
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `Fornecedor` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`nome` TEXT, " +
                    "`descricao` TEXT, " +
                    "`tipo` TEXT, " +
                    "`valor` REAL, " +
                    "`imagem` TEXT, " +
                    "`usuarioId` TEXT)"
        )

        // Copie os dados da tabela antiga para a nova tabela
        database.execSQL(
            "INSERT INTO `Fornecedor` (`id`, `nome`, `descricao`, `tipo`, `valor`, `imagem`, `usuarioId`) " +
                    "SELECT `id`, `nome`, `descricao`, `tipo`, `valor`, `imagem`, `usuarioId` FROM `Fornecedor`"
        )

        // Remova a tabela antiga se necessário
//        database.execSQL("DROP TABLE IF EXISTS `Fornecedor`")
    }
}

val MIGRATION_5_6 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Crie a nova tabela Fornecedor
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `Cliente` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`nome` TEXT, " +
                    "`descricao` TEXT, " +
                    "`tipo` TEXT, " +
                    "`valor` REAL, " +
                    "`imagem` TEXT, " +
                    "`usuarioId` TEXT)"
        )

        // Copie os dados da tabela antiga para a nova tabela
        database.execSQL(
            "INSERT INTO `Cliente` (`id`, `nome`, `descricao`, `tipo`, `valor`, `imagem`, `usuarioId`) " +
                    "SELECT `id`, `nome`, `descricao`, `tipo`, `valor`, `imagem`, `usuarioId` FROM `Cliente`"
        )

        // Remova a tabela antiga se necessário
//        database.execSQL("DROP TABLE IF EXISTS `Cliente`")
    }
}


