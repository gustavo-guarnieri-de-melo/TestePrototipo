package br.com.alura.TestePrototipo.ui.activity.fornecedor

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.database.dao.FornecedorDao
import br.com.alura.TestePrototipo.extensions.tentaCarregarImagem
import br.com.alura.TestePrototipo.model.Fornecedor
import br.com.alura.TestePrototipo.ui.activity.CHAVE_FORNECEDOR_ID
import br.com.alura.TestePrototipo.ui.activity.usuario.UsuarioBaseActivity
import br.com.alura.TestePrototipo.ui.dialog.FormularioImagemDialog
import br.com.alura.orgs.databinding.ActivityFormularioFornecedorBinding
import kotlinx.coroutines.launch

class FormularioFornecedorActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityFormularioFornecedorBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var fornecedorId = 0L
    private val fornecedorDao: FornecedorDao by lazy {
        val db = AppDatabase.instancia(this)
        db.fornecedorDao()
    }
    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar fornecedor"
        configuraBotaoSalvar()
        binding.activityFormularioFornecedorImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem -> url = imagem
                binding.activityFormularioFornecedorImagem.tentaCarregarImagem(url) }
        }
        tentaCarregarFornecedor()

    }

    private fun tentaCarregarFornecedor() {
        fornecedorId = intent.getLongExtra(CHAVE_FORNECEDOR_ID, 0L)
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarFornecedor()
    }

    private fun tentaBuscarFornecedor() {
        lifecycleScope.launch {
            fornecedorDao.buscaPorId(fornecedorId).collect {
                it?.let { fornecedorEncontrado ->
                    title = "Alterar fornecedor"
                    preencheCampos(fornecedorEncontrado)
                }
            }
        }
    }

    private fun preencheCampos(fornecedor: Fornecedor) {
        url = fornecedor.imagem
        binding.activityFormularioFornecedorImagem
            .tentaCarregarImagem(fornecedor.imagem)
        binding.activityFormularioFornecedorNome
            .setText(fornecedor.nome)
        binding.activityFormularioFornecedorSacado
            .setText(fornecedor.sacado)
        binding.activityFormularioFornecedorCpf
            .setText(fornecedor.cpf)
        binding.activityFormularioFornecedorRua
            .setText(fornecedor.rua)
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioFornecedorBotaoSalvar

        botaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                usuario.value?.let { usuario ->
                    val fornecedorNovo = criaFornecedor(usuario.usuario)
                    fornecedorDao.salva(fornecedorNovo)
                    finish()
                }
            }
        }
    }

    private fun criaFornecedor(usuarioId: String): Fornecedor {
        val campoNome = binding.activityFormularioFornecedorNome
        val nome = campoNome.text.toString()
        val campoSacado = binding.activityFormularioFornecedorSacado
        val sacado = campoSacado.text.toString()
        val campoRua = binding.activityFormularioFornecedorRua
        val rua = campoRua.text.toString()
        val campoCpf = binding.activityFormularioFornecedorCpf
        val cpf = campoCpf.text.toString()


        return Fornecedor(
            id = fornecedorId,
            nome = nome,
            sacado = sacado,
            cpf = cpf,
            imagem = url,
            usuarioId = usuarioId,
            rua = rua
        )
    }

}