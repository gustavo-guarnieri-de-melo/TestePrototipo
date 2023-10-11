import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.TestePrototipo.ui.activity.FormularioProdutoActivity
import br.com.alura.orgs.databinding.TelaInicialActivityBinding

class TelaInicialActivity : AppCompatActivity() {
    private val binding by lazy {
        TelaInicialActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Configurar o OnClickListener para o botão de cadastros
        binding.activityTelaInicialCadastros.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }
}
