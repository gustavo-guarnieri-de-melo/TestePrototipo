package br.com.alura.TestePrototipo.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.TestePrototipo.model.Fornecedor
import br.com.alura.TestePrototipo.model.Produto
import br.com.alura.orgs.databinding.FornecedorItemBinding
import br.com.alura.orgs.databinding.ProdutoItemBinding

class ListaFornecedoresAdapter(
    private val context: Context,
    fornecedores: List<Fornecedor> = emptyList(),
    var quandoClicaNoItem: (fornecedor: Fornecedor) -> Unit = {}
) : RecyclerView.Adapter<ListaFornecedoresAdapter.ViewHolder>() {

    private val fornecedores = fornecedores.toMutableList()

    inner class ViewHolder(private val binding: FornecedorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var fornecedor: Fornecedor

        init {
            itemView.setOnClickListener {
                if (::fornecedor.isInitialized) {
                    quandoClicaNoItem(fornecedor)
                }
            }
        }

        fun vincula(fornecedor: Fornecedor) {
            this.fornecedor = fornecedor
            val nome = binding.fornecedorItemNome
            nome.text = fornecedor.nome
            val descricao = binding.fornecedorItemDescricao
            descricao.text = fornecedor.descricao
            val id = binding.forncedorItemValor
            id.text = fornecedor.id.toString()
//            val valor = binding.produtoItemValor
//            val valorEmMoeda: String = produto.valor
//                .formataParaMoedaBrasileira()
//            valor.text = valorEmMoeda
//
//            val visibilidade = if (produto.imagem != null) {
//                View.VISIBLE
//            } else {
//                View.GONE
//            }
//
//            binding.imageView.visibility = visibilidade
//
//            binding.imageView.tentaCarregarImagem(produto.imagem)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaFornecedoresAdapter.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = FornecedorItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaFornecedoresAdapter.ViewHolder, position: Int) {
        val fornecedor = fornecedores[position]
        holder.vincula(fornecedor)
    }





    override fun getItemCount(): Int = fornecedores.size

    fun atualiza(fornecedores: List<Fornecedor>) {
        this.fornecedores.clear()
        this.fornecedores.addAll(fornecedores)
        notifyDataSetChanged()
    }

}
