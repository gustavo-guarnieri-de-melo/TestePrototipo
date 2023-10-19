package br.com.alura.TestePrototipo.ui.activity.produto.tipo

import ListaTiposAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.preferences.produtoDataStore
import br.com.alura.TestePrototipo.preferences.produtoIntegrado
import br.com.alura.TestePrototipo.ui.activity.produto.ProdutoBaseActivity
import br.com.alura.orgs.databinding.ActivityFormularioTipoBinding
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.databinding.ActivityListaTiposBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class ListaTiposActivity : ProdutoBaseActivity() {

    private val adapter = ListaTiposAdapter(context = this)
    private val produtoBinding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val binding by lazy {
        ActivityListaTiposBinding.inflate(layoutInflater)
    }
    private val tipoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.tipoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            launch {
                produto
                    .filterNotNull()
                    .collect { produto ->
//                        buscaTiposProduto(produto.tipo1)
                    }
            }
            produtoDataStore.data.collect{preferences ->
                preferences[produtoIntegrado]?.let { produtoId ->
                    produtoDao.buscaTodosDoUsuario(produtoId).collect{
                        Log.i("ListaTipos", "onCreate: $it ")
                    }
                }

            }
        }
    }

//    private suspend fun buscaTiposProduto(produtoId: String) {
//        tipoDao.buscaTodosDoProduto(produtoId).collect { tipos ->
//            adapter.atualiza(tipos)
//        }
//    }



    private fun vaiParaFormularioTipo() {
        val intent = Intent(this, ActivityFormularioTipoBinding::class.java)
        startActivity(intent)

    }

}

//    private fun configuraRecyclerView() {
//        val recyclerView = binding.activityListaTiposRecyclerView
//        recyclerView.adapter = adapter
//        adapter.quandoClicaNoItem = {
//            val intent = Intent(
//                this,
//                ActivityDetalhesTipoActivityBinding::class.java
//            ).apply {
//                putExtra(CHAVE_PRODUTO_ID, it.id)
//            }
//            startActivity(intent)
//        }
//    }





