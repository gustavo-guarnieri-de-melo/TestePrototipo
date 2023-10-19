

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.TestePrototipo.model.Produto
import br.com.alura.TestePrototipo.model.Tipo
import br.com.alura.orgs.databinding.TipoItemBinding


class ListaTiposAdapter(
    private val context: Context,
    tipos: List<Tipo> = emptyList(),
    var quandoClicaNoItem: (tipo: Tipo) -> Unit = {}
) : RecyclerView.Adapter<ListaTiposAdapter.ViewHolder>() {

    private val tipos = tipos.toMutableList()


    inner class ViewHolder(private val binding: TipoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var tipo: Tipo

        init {
            itemView.setOnClickListener {
                if (::tipo.isInitialized) {
                    quandoClicaNoItem(tipo)
                }
            }
        }

        fun vincula(tipo: Tipo) {
            this.tipo = tipo
            val nome = binding.tipoItemNome
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = TipoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tipo = tipos[position]
        holder.vincula(tipo)
    }

    override fun getItemCount(): Int = tipos.size

//    fun atualiza(tipos: List<Produto>) {
//        this.tipos.clear()
//        this.tipos.addAll(tipos)
//        notifyDataSetChanged()
//    }

}

