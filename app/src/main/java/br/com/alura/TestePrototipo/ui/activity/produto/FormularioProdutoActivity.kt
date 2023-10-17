package br.com.alura.TestePrototipo.ui.activity.produto

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.database.dao.ProdutoDao
import br.com.alura.TestePrototipo.extensions.tentaCarregarImagem
import br.com.alura.TestePrototipo.model.Produto
import br.com.alura.TestePrototipo.ui.activity.CHAVE_PRODUTO_ID
import br.com.alura.TestePrototipo.ui.activity.usuario.UsuarioBaseActivity
import br.com.alura.TestePrototipo.ui.dialog.FormularioImagemDialog
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.alura.orgs.databinding.ActivityFormularioTipoBinding
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormularioProdutoActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private val tipo by lazy {
        ActivityFormularioTipoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao: ProdutoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }
    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val botaoAdicionarTipos = findViewById<Button>(R.id.activity_formulario_produto_botao_adicionar_tipo)
        botaoAdicionarTipos.setOnClickListener {
            exibirDialogAdicionarTipo()
        }
        title = "Cadastrar produto"
        configuraBotaoSalvar()
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem -> url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url) }
        }
        tentaCarregarProduto()

    }
    private fun exibirDialogAdicionarTipo() {
        val dialogView = layoutInflater.inflate(R.layout.activity_formulario_tipo, null)

        // Declare uma variável para armazenar o objeto de diálogo
        var alertDialog: AlertDialog? = null

        alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Salvar") { _, _ ->
                val editTextTipo = dialogView.findViewById<Button>(R.id.activity_formulario_tipo_botao_adicionar) // Corrija aqui para obter o EditText
                val novoTipo = editTextTipo.text.toString()

                // Feche o diálogo usando a variável alertDialog
                alertDialog?.dismiss()
            }
            .setNegativeButton("Cancelar") { _, _ ->
                // Se o usuário cancelar, também feche o diálogo usando a variável alertDialog
                alertDialog?.dismiss()
            }
            .create()

        alertDialog.show()
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        lifecycleScope.launch {
            produtoDao.buscaPorId(produtoId).collect {
                it?.let { produtoEncontrado ->
                    title = "Alterar produto"
                    preencheCampos(produtoEncontrado)
                }
            }
        }
    }


    private fun preencheCampos(produto: Produto) {
        url = produto.imagem
        binding.activityFormularioProdutoImagem
            .tentaCarregarImagem(produto.imagem)
        binding.activityFormularioProdutoNome
            .setText(produto.nome)
        binding.activityFormularioProdutoDescricao
            .setText(produto.descricao)
        binding.activityFormularioProdutoValor
            .setText(produto.valor.toPlainString())
        tipo.activityFormularioTipoTipo1
            .setText(produto.tipo1)
        tipo.activityFormularioTipoTipo2
            .setText(produto.tipo2)
        tipo.activityFormularioTipoTipo3
            .setText(produto.tipo3)
        tipo.activityFormularioTipoTipo4
            .setText(produto.tipo4)
        tipo.activityFormularioTipoTipo5
            .setText(produto.tipo5)

    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar

        botaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                usuario.value?.let { usuario ->
                    val produtoNovo = criaProduto(usuario.usuario)
                    produtoDao.salva(produtoNovo)
                    finish()
                }
            }
        }
    }

    private fun criaProduto(usuarioId: String): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }
        val campoTipo1 = tipo.activityFormularioTipoTipo1
        val tipo1 = campoTipo1.text.toString()
        val campoTipo2 = tipo.activityFormularioTipoTipo2
        val tipo2 = campoTipo2.text.toString()
        val campoTipo3 = tipo.activityFormularioTipoTipo3
        val tipo3 = campoTipo3.text.toString()
        val campoTipo4 = tipo.activityFormularioTipoTipo4
        val tipo4 = campoTipo4.text.toString()
        val campoTipo5 = tipo.activityFormularioTipoTipo5
        val tipo5 = campoTipo5.text.toString()
        val tipos = mutableListOf<String>()

        return Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url,
            usuarioId = usuarioId,
            tipo1 = tipo1,
            tipo2 = tipo2,
            tipo3 = tipo3,
            tipo4 = tipo4,
            tipo5 = tipo5,
        )

    }

}


//            tipo1 = tipo1,
//            tipo2 = tipo2,
//            tipo3 = tipo3,
//            tipo4 = tipo4,
//            tipo5 = tipo5