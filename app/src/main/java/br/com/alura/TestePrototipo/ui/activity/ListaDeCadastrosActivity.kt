package br.com.alura.TestePrototipo.ui.activity

import android.content.Intent
import android.os.Bundle
import br.com.alura.TestePrototipo.extensions.vaiPara
import br.com.alura.TestePrototipo.ui.activity.produto.ListaProdutosActivity
import br.com.alura.TestePrototipo.ui.activity.usuario.UsuarioBaseActivity
import br.com.alura.orgs.databinding.ActivityListaCadastrosBinding

class ListaDeCadastrosActivity : UsuarioBaseActivity() {
    private val binding by lazy { ActivityListaCadastrosBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Configurar o OnClickListener para o botão de cadastros
        binding.activityListaCadastrosProdutos.setOnClickListener {
            vaiParaListaProdutos()
        }
    }
    private fun vaiParaTelaInicial() {
        vaiPara(vaiParaTelaInicial()::class.java) { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
        finish()
    }



    private fun vaiParaListaProdutos() {
        val intent = Intent(this, ListaProdutosActivity::class.java)
        startActivity(intent)
    }

}

