package br.com.alura.TestePrototipo.ui.activity.fornecedor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.extensions.formataParaMoedaBrasileira
import br.com.alura.TestePrototipo.extensions.tentaCarregarImagem
import br.com.alura.TestePrototipo.model.Fornecedor
import br.com.alura.TestePrototipo.ui.activity.CHAVE_FORNECEDOR_ID
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityDetalhesFornecedorBinding
import kotlinx.coroutines.launch

class DetalhesFornecedorActivity : AppCompatActivity() {

    private var fornecedorId: Long = 0L
    private var fornecedor: Fornecedor? = null

    private val binding by lazy {
        ActivityDetalhesFornecedorBinding.inflate(layoutInflater)
    }

    private val fornecedorDao by lazy {
        AppDatabase.instancia(this).fornecedorDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarFornecedor()
    }

    override fun onResume() {
        super.onResume()
        buscaFornecedor()
    }

    private fun buscaFornecedor() {
        lifecycleScope.launch {
            fornecedorDao.buscaPorId(fornecedorId).collect { fornecedorEncontrado ->
                fornecedor = fornecedorEncontrado
                fornecedor?.let {
                    preencheCampos(it)
                } ?: finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_fornecedor, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_fornecedor_remover -> {
                fornecedor?.let {
                    lifecycleScope.launch {
                        fornecedorDao.remove(it)
                        finish()
                    }
                }

            }
            R.id.menu_detalhes_fornecedor_editar -> {
                Intent(this, FormularioFornecedorActivity::class.java).apply {
                    putExtra(CHAVE_FORNECEDOR_ID, fornecedorId)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarFornecedor() {
        fornecedorId = intent.getLongExtra(CHAVE_FORNECEDOR_ID, 0L)
    }

    private fun preencheCampos(fornecedorCarregado: Fornecedor) {
        with(binding) {
            activityDetalhesFornecedorImagem.tentaCarregarImagem(fornecedorCarregado.imagem)
            activityDetalhesFornecedorNome.text = fornecedorCarregado.nome
            activityDetalhesFornecedorSacado.text = fornecedorCarregado.sacado
            activityDetalhesFornecedorRua.text = fornecedorCarregado.rua
            activityDetalhesFornecedorCpf.text = formatarCpf(fornecedorCarregado.cpf)
//                .formataParaMoedaBrasileira()
        }
    }
    private fun formatarCpf(cpf: String): String {
        val regex = "(\\d{3})(\\d{3})(\\d{3})(\\d{2})".toRegex()
        return cpf.replace(regex, "$1.$2.$3-$4")
    }

}