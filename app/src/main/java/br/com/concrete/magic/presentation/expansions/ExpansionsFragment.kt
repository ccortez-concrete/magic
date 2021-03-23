package br.com.concrete.magic.presentation.expansions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.concrete.magic.R
import br.com.concrete.magic.databinding.FragmentExpansionsBinding
import br.com.concrete.magic.utils.isNetworkAvailable
import kotlinx.android.synthetic.main.fragment_expansions.*
import org.koin.android.viewmodel.ext.android.viewModel

class ExpansionsFragment : Fragment() {

    private lateinit var fragmentExpansionsBinding: FragmentExpansionsBinding
    private var mAdapter: ExpansionsAdapter? = ExpansionsAdapter()
    private val expansionsViewModel: ExpansionsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        fragmentExpansionsBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_expansions, container, false)

        fragmentExpansionsBinding.postsRecyclerView.adapter = mAdapter

        val horizontalDecoration = DividerItemDecoration(
            fragmentExpansionsBinding.postsRecyclerView.getContext(),
            DividerItemDecoration.VERTICAL)
        val horizontalDivider = ContextCompat.getDrawable(requireActivity(), R.drawable.horizontal_divider)
        horizontalDecoration.setDrawable(horizontalDivider!!)
        fragmentExpansionsBinding.postsRecyclerView.addItemDecoration(horizontalDecoration)

        if (requireActivity().isNetworkAvailable()) {
            expansionsViewModel.getExpansions()
        } else {
            Toast.makeText(
                requireActivity(),
                getString(R.string.no_internet_connection),
                LENGTH_SHORT
            ).show()
        }

        with(expansionsViewModel) {

            expansionsData.observe(this@ExpansionsFragment, Observer {
                fragmentExpansionsBinding.postsProgressBar.visibility = GONE
                mAdapter?.mExpansionList = it

            })

            messageData.observe(this@ExpansionsFragment, Observer {
                Toast.makeText(requireActivity(), it, LENGTH_LONG).show()
            })

            showProgressbar.observe(this@ExpansionsFragment, Observer { isVisible ->
                posts_progress_bar.visibility = if (isVisible) VISIBLE else GONE
            })
        }
        return fragmentExpansionsBinding!!.getRoot()
    }

    override fun onDestroy() {
        mAdapter = null
        super.onDestroy()
    }

    companion object {
        val TAG = ExpansionsFragment::class.java.name
    }
}
