package br.com.alura.TestePrototipo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.preferences.dataStore
import br.com.alura.TestePrototipo.preferences.usuarioLogadoPreferences
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.TelaInicialActivityBinding
import kotlinx.coroutines.launch

class TelaInicialActivity : UsuarioBaseActivity() {
    private val binding by lazy { TelaInicialActivityBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Configurar o OnClickListener para o botão de cadastros
        binding.activityTelaInicialCadastros.setOnClickListener {
            vaiParaListaProdutos()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) { R.id.menu_lista_produtos_sair_do_app -> { lifecycleScope.launch { deslogaUsuario() }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun vaiParaListaProdutos() {
        val intent = Intent(this, ListaProdutosActivity::class.java)
        startActivity(intent)
    }

}

