package br.com.alura.TestePrototipo.ui.activity.produto

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.ui.activity.CHAVE_PRODUTO_ID
import br.com.alura.TestePrototipo.ui.activity.usuario.UsuarioBaseActivity
import br.com.alura.TestePrototipo.ui.recyclerview.adapter.ListaProdutosAdapter
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class ListaProdutosActivity : UsuarioBaseActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        lifecycleScope.launch {
            launch {
                usuario
                    .filterNotNull()
                    .collect { usuario ->
                        buscaProdutosUsuario(usuario.usuario)
                    }
            }
        }
    }

    private suspend fun buscaProdutosUsuario(usuarioId: String) {
        produtoDao.buscaTodosDoUsuario(usuarioId).collect { produtos ->
            adapter.atualiza(produtos)
        }
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }



    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }
}


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_lista_cadastros, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_lista_produtos_sair_do_app -> {
//                lifecycleScope.launch {
//                    deslogaUsuario()
//                }
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }