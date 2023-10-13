package br.com.alura.TestePrototipo.ui.activity.usuario

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.extensions.toast
import br.com.alura.TestePrototipo.extensions.vaiPara
import br.com.alura.TestePrototipo.preferences.dataStore
import br.com.alura.TestePrototipo.preferences.usuarioLogadoPreferences
import br.com.alura.TestePrototipo.ui.activity.TelaInicialActivity
import br.com.alura.orgs.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
        configuraBotaoEntrar()
    }

    private fun configuraBotaoEntrar() {
        binding.activityLoginBotaoEntrar.setOnClickListener {
            val usuario = binding.activityLoginUsuario.text.toString()
            val senha = binding.activityLoginSenha.text.toString()
            autentica(usuario, senha)
        }
    }

    private fun autentica(usuario: String, senha: String) {
        lifecycleScope.launch {
            usuarioDao.autentica(usuario, senha)?.let { usuario ->
                dataStore.edit { preferences ->
                    preferences[usuarioLogadoPreferences] = usuario.usuario
                }
                vaiParaTelaInicial(TelaInicialActivity::class.java)
                finish()
            } ?: toast("Falha na autenticação")
        }
    }

    private fun configuraBotaoCadastrar() {
        binding.activityLoginBotaoCadastrar.setOnClickListener {
            vaiPara(FormularioCadastroUsuarioActivity::class.java)
        }
    }
    private fun vaiParaTelaInicial(TelaInicialActivity: Class<*>) {
        val intent = Intent(this, TelaInicialActivity)
        startActivity(intent)
    }

}