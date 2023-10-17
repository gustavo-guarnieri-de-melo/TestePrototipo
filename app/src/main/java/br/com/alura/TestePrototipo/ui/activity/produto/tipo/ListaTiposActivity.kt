package br.com.alura.TestePrototipo.ui.activity.produto.tipo

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.ui.activity.usuario.UsuarioBaseActivity
import br.com.alura.TestePrototipo.ui.recyclerview.adapter.ListaTipoAdapter
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class ListaTiposActivity : UsuarioBaseActivity() {

    private val adapter = ListaTipoAdapter(context = this)
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private val tipoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            launch {
                usuario
                    .filterNotNull()
                    .collect { usuario ->
//                        buscaTiposUsuario(usuario.usuario)
                    }
            }
        }

    }

}






