package br.com.alura.TestePrototipo.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.TestePrototipo.database.AppDatabase
import br.com.alura.TestePrototipo.extensions.vaiPara
import br.com.alura.TestePrototipo.model.Usuario
import br.com.alura.TestePrototipo.preferences.dataStore
import br.com.alura.TestePrototipo.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class UsuarioBaseActivity : AppCompatActivity() {

    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }
    private val _usuario: MutableStateFlow<Usuario?> = MutableStateFlow(null)
    protected val usuario: StateFlow<Usuario?> = _usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }

    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect { preferences ->
            preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                buscaUsuario(usuarioId)
            } ?: vaiParaLogin()
        }
    }

    private suspend fun buscaUsuario(usuarioId: String): Usuario? {
        return usuarioDao.buscaPorId(usuarioId).firstOrNull().also { _usuario.value = it }
    }

    suspend fun deslogaUsuario() {
        dataStore.edit { preferences -> preferences.remove(usuarioLogadoPreferences) }
    }

    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java) { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
        finish()
    }

}