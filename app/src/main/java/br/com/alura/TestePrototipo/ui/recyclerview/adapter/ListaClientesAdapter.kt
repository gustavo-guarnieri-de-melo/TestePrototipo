package br.com.alura.TestePrototipo.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.TestePrototipo.model.Cliente
import br.com.alura.orgs.databinding.ClienteItemBinding

class ListaClientesAdapter(
    private val context: Context,
    clientes: List<Cliente> = emptyList(),
    var quandoClicaNoItem: (cliente: Cliente) -> Unit = {}
) : RecyclerView.Adapter<ListaClientesAdapter.ViewHolder>() {

    private val clientes = clientes.toMutableList()

    inner class ViewHolder(private val binding: ClienteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var cliente: Cliente

        init {
            itemView.setOnClickListener {
                if (::cliente.isInitialized) {
                    quandoClicaNoItem(cliente)
                }
            }
        }

        fun vincula(cliente: Cliente) {
            this.cliente = cliente
            val nome = binding.clienteItemNome
            nome.text = cliente.nome
            val descricao = binding.clienteItemDescricao
            descricao.text = cliente.descricao
            val id = binding.forncedorItemValor
            id.text = cliente.id.toString()

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ClienteItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaClientesAdapter.ViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.vincula(cliente)
    }





    override fun getItemCount(): Int = clientes.size

    fun atualiza(clientes: List<Cliente>) {
        this.clientes.clear()
        this.clientes.addAll(clientes)
        notifyDataSetChanged()
    }

}
