package br.com.concrete.magic.presentation.expansions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import br.com.concrete.magic.R
import br.com.concrete.magic.databinding.HolderExpansionBinding
import br.com.concrete.magic.databinding.HolderExpansionLetterBinding
import br.com.concrete.magic.domain.model.Expansion
import br.com.concrete.magic.domain.model.ExpansionView
import br.com.concrete.magic.domain.model.ExpansionViewType
import kotlin.properties.Delegates

class ExpansionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mExpansionList: List<ExpansionView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ExpansionViewType.SECTION.ordinal) {

            val holderExpansionLetterBinding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context), R.layout.holder_expansion_letter,
                    parent, false
            )
            return ExpansionLetterViewHolder(holderExpansionLetterBinding)

        } else {

            val holderExpansionBinding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context), R.layout.holder_expansion, parent, false
            )
            return ExpansionViewHolder(holderExpansionBinding)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return mExpansionList.get(position).viewType.ordinal
    }

    override fun getItemCount(): Int =
            if (mExpansionList.isNullOrEmpty()) 0
                    else
            mExpansionList.size

    private fun getItem(position: Int): ExpansionView = mExpansionList.get(position)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.getItemViewType() == ExpansionViewType.ITEM.ordinal) {
            (holder as ExpansionViewHolder).onBind(getItem(position))
        } else if (holder.getItemViewType() == ExpansionViewType.SECTION.ordinal) {
            (holder as ExpansionLetterViewHolder).onBind(getItem(position))
        }
    }

    private inner class ExpansionViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun onBind(expansion: ExpansionView) {
            (viewDataBinding as HolderExpansionBinding).expansionView = expansion
        }
    }

    private inner class ExpansionLetterViewHolder(private val viewDataBinding: ViewDataBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {

        fun onBind(expansion: ExpansionView) {
            (viewDataBinding as HolderExpansionLetterBinding).expansionView = expansion
        }
    }
}