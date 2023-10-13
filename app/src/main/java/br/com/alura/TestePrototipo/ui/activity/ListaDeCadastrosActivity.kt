package br.com.alura.TestePrototipo.ui.activity

import android.content.Intent
import android.os.Bundle
import br.com.alura.TestePrototipo.extensions.vaiPara
import br.com.alura.TestePrototipo.ui.activity.cliente.ListaClientesActivity
import br.com.alura.TestePrototipo.ui.activity.fornecedor.ListaFornecedoresActivity
import br.com.alura.TestePrototipo.ui.activity.produto.ListaProdutosActivity
import br.com.alura.TestePrototipo.ui.activity.usuario.UsuarioBaseActivity
import br.com.alura.orgs.databinding.ActivityListaCadastrosBinding

class ListaDeCadastrosActivity : UsuarioBaseActivity() {
    private val binding by lazy { ActivityListaCadastrosBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.activityListaCadastrosProdutos.setOnClickListener {
            vaiParaListaProdutos()
        }
        setContentView(binding.root)
        binding.activityListaCadastrosFornecedores.setOnClickListener {
            vaiParaListaFornecedores()
        }
        setContentView(binding.root)
        binding.activityListaCadastrosClientes.setOnClickListener {
            vaiParaListaClientes()
        }
    }

//    private fun vaiParaTelaInicial() {
//        vaiPara(vaiParaTelaInicial()::class.java) { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
//        finish()
//    }



    private fun vaiParaListaProdutos() {
        val intent = Intent(this, ListaProdutosActivity::class.java)
        startActivity(intent)
    }
    private fun vaiParaListaFornecedores() {
        val intent = Intent(this, ListaFornecedoresActivity::class.java)
        startActivity(intent)
    }
    private fun vaiParaListaClientes() {
        val intent = Intent(this, ListaClientesActivity::class.java)
        startActivity(intent)
    }

}

