package br.com.alura.TestePrototipo.ui.activity.cliente

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.database.dao.ClienteDao
import br.com.alura.TestePrototipo.extensions.tentaCarregarImagem
import br.com.alura.TestePrototipo.model.Cliente
import br.com.alura.TestePrototipo.ui.activity.CHAVE_CLIENTE_ID
import br.com.alura.TestePrototipo.ui.activity.usuario.UsuarioBaseActivity
import br.com.alura.TestePrototipo.ui.dialog.FormularioImagemDialog
import br.com.alura.orgs.databinding.ActivityFormularioClienteBinding
import kotlinx.coroutines.launch

class FormularioClienteActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityFormularioClienteBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var clienteId = 0L
    private val clienteDao: ClienteDao by lazy {
        val db = AppDatabase.instancia(this)
        db.clienteDao()
    }
    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar cliente"
        configuraBotaoSalvar()
        binding.activityFormularioClienteImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem -> url = imagem
                binding.activityFormularioClienteImagem.tentaCarregarImagem(url) }
        }
        tentaCarregarCliente()

    }

    private fun tentaCarregarCliente() {
        clienteId = intent.getLongExtra(CHAVE_CLIENTE_ID, 0L)
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarCliente()
    }

    private fun tentaBuscarCliente() {
        lifecycleScope.launch {
            clienteDao.buscaPorId(clienteId).collect {
                it?.let { clienteEncontrado ->
                    title = "Alterar cliente"
                    preencheCampos(clienteEncontrado)
                }
            }
        }
    }

    private fun preencheCampos(cliente: Cliente) {
        url = cliente.imagem
        binding.activityFormularioClienteImagem
            .tentaCarregarImagem(cliente.imagem)
        binding.activityFormularioClienteNome
            .setText(cliente.nome)
        binding.activityFormularioClienteDescricao
            .setText(cliente.descricao)
        binding.activityFormularioClienteCpf
            .setText(cliente.cpf)
        binding.activityFormularioClienteTipo
            .setText(cliente.tipo)
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioClienteBotaoSalvar

        botaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                usuario.value?.let { usuario ->
                    val clienteNovo = criaCliente(usuario.usuario)
                    clienteDao.salva(clienteNovo)
                    finish()
                }
            }
        }
    }

    private fun criaCliente(usuarioId: String): Cliente {
        val campoNome = binding.activityFormularioClienteNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormularioClienteDescricao
        val descricao = campoDescricao.text.toString()
        val campoTipo = binding.activityFormularioClienteTipo
        val tipo = campoTipo.text.toString()
        val campoCpf = binding.activityFormularioClienteCpf
        val cpf = campoCpf.text.toString()




        return Cliente(
            id = clienteId,
            nome = nome,
            descricao = descricao,
            cpf = cpf,
            imagem = url,
            usuarioId = usuarioId,
            tipo = tipo
        )
    }

}