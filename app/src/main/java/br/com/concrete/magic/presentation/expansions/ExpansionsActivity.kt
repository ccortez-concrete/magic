package br.com.concrete.magic.presentation.expansions

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.concrete.magic.R
import br.com.concrete.magic.databinding.ActivityExpansionsBinding
import kotlinx.android.synthetic.main.activity_expansions.*
import org.koin.android.viewmodel.ext.android.viewModel


class ExpansionsActivity : AppCompatActivity() {

    private lateinit var activityExpansionsBinding: ActivityExpansionsBinding
    private val mAdapter by lazy { ExpansionsAdapter() }
    private val expansionsViewModel: ExpansionsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityExpansionsBinding = DataBindingUtil.setContentView(this, R.layout.activity_expansions)

        activityExpansionsBinding.expansionsRecyclerView.adapter = mAdapter

        val horizontalDecoration = DividerItemDecoration(
                activityExpansionsBinding.expansionsRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL)
        val horizontalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_divider)
        horizontalDivider?.let {
            horizontalDecoration.setDrawable(it)
        }

        activityExpansionsBinding.expansionsRecyclerView.addItemDecoration(horizontalDecoration)

        expansionsViewModel.getExpansions()

        with(expansionsViewModel) {

            expansionsData.observe(this@ExpansionsActivity, Observer {
                activityExpansionsBinding.expansionsProgressBar.visibility = GONE
                mAdapter.mExpansionList = it

            })

            messageData.observe(this@ExpansionsActivity, Observer {
                Toast.makeText(this@ExpansionsActivity, it, LENGTH_LONG).show()
            })

            showProgressbar.observe(this@ExpansionsActivity, Observer { isVisible ->
                expansions_progress_bar.visibility = if (isVisible) VISIBLE else GONE
            })
        }
    }

    companion object {
        private val TAG = ExpansionsActivity::class.java.name
    }
}
