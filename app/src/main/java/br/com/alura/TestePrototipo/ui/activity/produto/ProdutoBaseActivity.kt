package br.com.alura.TestePrototipo.ui.activity.produto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.extensions.vaiPara
import br.com.alura.TestePrototipo.model.Produto
import br.com.alura.TestePrototipo.model.Tipo
import br.com.alura.TestePrototipo.preferences.produtoDataStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class ProdutoBaseActivity : AppCompatActivity() {

    val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }
    private val _produto: MutableStateFlow<Produto?> = MutableStateFlow(null)
    protected val produto: StateFlow<Produto?> = _produto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            salvaProduto()
        }
    }


//    private suspend fun buscaProduto(testeProdutoId: String): Produto? {
//        return produtoDao.buscaPorId().firstOrNull().also { _produto.value = it }
//    }


    private fun salvaProduto() {
        vaiPara(produtoDataStore::class.java) { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
        finish()
    }

}