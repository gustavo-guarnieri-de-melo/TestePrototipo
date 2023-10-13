package br.com.alura.TestePrototipo.ui.activity.fornecedor

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.ui.activity.CHAVE_FORNECEDOR_ID
import br.com.alura.TestePrototipo.ui.activity.usuario.UsuarioBaseActivity
import br.com.alura.TestePrototipo.ui.recyclerview.adapter.ListaFornecedoresAdapter
import br.com.alura.orgs.databinding.ActivityListaFornecedoresBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class ListaFornecedoresActivity : UsuarioBaseActivity() {

    private val adapter = ListaFornecedoresAdapter(context = this)
    private val binding by lazy {
        ActivityListaFornecedoresBinding.inflate(layoutInflater)
    }
    private val fornecedorDao by lazy {
        val db = AppDatabase.instancia(this)
        db.fornecedorDao()
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
                        buscaFornecedoresUsuario(usuario.usuario)
                    }
            }
        }
    }

    private suspend fun buscaFornecedoresUsuario(usuarioId: String) {
        fornecedorDao.buscaTodosDoUsuario(usuarioId).collect { fornecedores ->
            adapter.atualiza(fornecedores)
        }
    }

    private fun configuraFab() {
        val fab = binding.activityListaFornecedorFab
        fab.setOnClickListener {
            vaiParaFormularioFornecedores()
        }
    }

    private fun vaiParaFormularioFornecedores() {
        val intent = Intent(this, FormularioFornecedorActivity::class.java)
        startActivity(intent)
    }


    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaFornecedorRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesFornecedorActivity::class.java
            ).apply {
                putExtra(CHAVE_FORNECEDOR_ID, it.id)
            }
            startActivity(intent)
        }
    }

}