package br.com.alura.TestePrototipo.ui.activity.cliente

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.extensions.tentaCarregarImagem
import br.com.alura.TestePrototipo.model.Cliente
import br.com.alura.TestePrototipo.ui.activity.CHAVE_CLIENTE_ID
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityDetalhesClienteBinding
import kotlinx.coroutines.launch

class DetalhesClienteActivity : AppCompatActivity() {

    private var clienteId: Long = 0L
    private var cliente: Cliente? = null
    private val binding by lazy {
        ActivityDetalhesClienteBinding.inflate(layoutInflater)
    }
    private val clienteDao by lazy {
        AppDatabase.instancia(this).clienteDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarCliente()
    }

    override fun onResume() {
        super.onResume()
        buscaCliente()
    }

    private fun buscaCliente() {
        lifecycleScope.launch {
            clienteDao.buscaPorId(clienteId).collect { clienteEncontrado ->
                cliente = clienteEncontrado
                cliente?.let {
                    preencheCampos(it)
                } ?: finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_cliente, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_cliente_remover -> {
                cliente?.let {
                    lifecycleScope.launch {
                        clienteDao.remove(it)
                        finish()
                    }
                }

            }
            R.id.menu_detalhes_cliente_editar -> {
                Intent(this, FormularioClienteActivity::class.java).apply {
                    putExtra(CHAVE_CLIENTE_ID, clienteId)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarCliente() {
        clienteId = intent.getLongExtra(CHAVE_CLIENTE_ID, 0L)
    }

    private fun preencheCampos(clienteCarregado: Cliente) {
        with(binding) {
            activityDetalhesClienteImagem.tentaCarregarImagem(clienteCarregado.imagem)
            activityDetalhesClienteNome.text = clienteCarregado.nome
            activityDetalhesClienteDescricao.text = clienteCarregado.descricao
            activityDetalhesClienteTipo.text = clienteCarregado.tipo
            activityDetalhesClienteValor.text = clienteCarregado.cpf
        }
    }

}