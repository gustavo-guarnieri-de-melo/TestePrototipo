package br.com.alura.TestePrototipo.ui.activity.produto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.extensions.formataParaMoedaBrasileira
import br.com.alura.TestePrototipo.extensions.tentaCarregarImagem
import br.com.alura.TestePrototipo.model.Produto
import br.com.alura.TestePrototipo.ui.activity.CHAVE_PRODUTO_ID
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.databinding.ActivityFormularioTipoBinding
import kotlinx.coroutines.launch

class DetalhesProdutoActivity : AppCompatActivity() {

    private var produtoId: Long = 0L
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }


    override fun openContextMenu(view: View?) {
        super.openContextMenu(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Detalhes do produto"
        setContentView(binding.root)
        tentaCarregarProduto()


    }

    override fun onResume() {
        super.onResume()
        buscaProduto()
    }

    private fun buscaProduto() {
        lifecycleScope.launch {
            produtoDao.buscaPorId(produtoId).collect { produtoEncontrado ->
                produto = produtoEncontrado
                produto?.let {
                    preencheCampos(it)
                } ?: finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_produto_remover -> {
                produto?.let {
                    lifecycleScope.launch {
                        produtoDao.remove(it)
                        finish()
                    }
                }

            }
            R.id.menu_detalhes_produto_editar -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra(CHAVE_PRODUTO_ID, produtoId)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }


    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            activityDetalhesProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem)
            activityDetalhesProdutoNome.text = produtoCarregado.nome
            activityDetalhesProdutoDescricao.text = produtoCarregado.descricao
            activityDetalhesProdutoValor.text = produtoCarregado.valor.formataParaMoedaBrasileira()

            if (!produtoCarregado.tipo1.isNullOrEmpty()){
                activityDetalhesProdutoTipo1.text = produtoCarregado.tipo1
                activityDetalhesProdutoTipo1.visibility = View.VISIBLE
            }else{
                activityDetalhesProdutoTipo1.visibility = View.GONE
            }

            if (!produtoCarregado.tipo2.isNullOrEmpty()){
                activityDetalhesProdutoTipo2.text = produtoCarregado.tipo2
                activityDetalhesProdutoTipo2.visibility = View.VISIBLE
            }else{
                activityDetalhesProdutoTipo2.visibility = View.GONE
            }

            if (!produtoCarregado.tipo3.isNullOrEmpty()) {
                activityDetalhesProdutoTipo3.text = produtoCarregado.tipo3
                activityDetalhesProdutoTipo3.visibility = View.VISIBLE
            } else {
                activityDetalhesProdutoTipo3.visibility = View.GONE
            }

            if (!produtoCarregado.tipo4.isNullOrEmpty()){
                activityDetalhesProdutoTipo4.text = produtoCarregado.tipo4
                activityDetalhesProdutoTipo4.visibility = View.VISIBLE
            }else{
                activityDetalhesProdutoTipo4.visibility = View.GONE
            }

            if (!produtoCarregado.tipo5.isNullOrEmpty()){
                activityDetalhesProdutoTipo5.text = produtoCarregado.tipo5
                activityDetalhesProdutoTipo5.visibility = View.VISIBLE
            }else{
                activityDetalhesProdutoTipo5.visibility = View.GONE
            }

        }
    }

}

