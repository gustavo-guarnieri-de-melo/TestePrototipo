package br.com.alura.TestePrototipo.ui.activity.cliente

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.ui.activity.CHAVE_CLIENTE_ID
import br.com.alura.TestePrototipo.ui.activity.usuario.UsuarioBaseActivity
import br.com.alura.TestePrototipo.ui.recyclerview.adapter.ListaClientesAdapter
import br.com.alura.orgs.databinding.ActivityListaClientesBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class ListaClientesActivity : UsuarioBaseActivity() {

    private val adapter = ListaClientesAdapter(context = this)
    private val binding by lazy {
        ActivityListaClientesBinding.inflate(layoutInflater)
    }
    private val clienteDao by lazy {
        val db = AppDatabase.instancia(this)
        db.clienteDao()
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
                        buscaClientesUsuario(usuario.usuario)
                    }
            }
        }
    }

    private suspend fun buscaClientesUsuario(usuarioId: String) {
        clienteDao.buscaTodosDoUsuario(usuarioId).collect { clientes ->
            adapter.atualiza(clientes)
        }
    }

    private fun configuraFab() {
        val fab = binding.activityListaClienteFab
        fab.setOnClickListener {
            vaiParaFormularioClientes()
        }
    }

    private fun vaiParaFormularioClientes() {
        val intent = Intent(this, FormularioClienteActivity::class.java)
        startActivity(intent)
    }


    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaCadastrosClienteRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesClienteActivity::class.java
            ).apply {
                putExtra(CHAVE_CLIENTE_ID, it.id)
            }
            startActivity(intent)
        }
    }

}