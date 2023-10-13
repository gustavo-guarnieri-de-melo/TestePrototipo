package br.com.alura.TestePrototipo.ui.activity.usuario

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.extensions.toast
import br.com.alura.TestePrototipo.model.Usuario
import br.com.alura.orgs.databinding.ActivityFormularioCadastroUsuarioBinding
import kotlinx.coroutines.launch

class FormularioCadastroUsuarioActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }
    private val dao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
    }

    private fun configuraBotaoCadastrar() {
        binding.activityFormularioCadastroBotaoCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            if (novoUsuario.usuario.isNotEmpty() && novoUsuario.nome.isNotEmpty() && novoUsuario.senha.isNotEmpty()) {
                cadastra(novoUsuario)
            } else {
                toast("Preencha todos os campos")
            }
        }
    }

    private fun cadastra(usuario: Usuario) {
        lifecycleScope.launch {
            try {
                dao.salva(usuario)
                finish()
            } catch (e: Exception) {
                Log.e("CadastroUsuario", "configuraBotaoCadastrar: ", e)
                toast("Falha ao cadastrar usuário")
            }
        }
    }

    private fun criaUsuario(): Usuario {
        val usuario = binding.activityFormularioCadastroUsuario.text.toString()
        val nome = binding.activityFormularioCadastroNome.text.toString()
        val senha = binding.activityFormularioCadastroSenha.text.toString()
        return Usuario(usuario, nome, senha)
    }

}