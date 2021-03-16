package br.com.concrete.magic.presentation.expansions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import br.com.concrete.magic.R
import br.com.concrete.magic.databinding.HolderExpansionBinding
import br.com.concrete.magic.domain.model.Expansion
import kotlin.properties.Delegates

class ExpansionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mExpansionList: List<Expansion> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderPostBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_expansion, parent, false
        )
        return PostViewHolder(holderPostBinding)
    }

    override fun getItemCount(): Int = if (mExpansionList.isNullOrEmpty()) 0 else mExpansionList.size

    private fun getItem(position: Int): Expansion = mExpansionList[position]

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).onBind(getItem(position))
    }

    private inner class PostViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun onBind(expansion: Expansion) {
            (viewDataBinding as HolderExpansionBinding).expansion = expansion
        }
    }
}