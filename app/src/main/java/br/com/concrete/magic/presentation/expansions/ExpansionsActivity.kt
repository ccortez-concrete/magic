package br.com.concrete.magic.presentation.expansions

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.concrete.magic.R
import br.com.concrete.magic.databinding.ActivityExpansionsBinding
import br.com.concrete.magic.utils.isNetworkAvailable
import kotlinx.android.synthetic.main.activity_expansions.*
import org.koin.android.viewmodel.ext.android.viewModel


class ExpansionsActivity : AppCompatActivity() {

    private lateinit var activityExpansionsBinding: ActivityExpansionsBinding
    private var mAdapter: ExpansionsAdapter? = ExpansionsAdapter()
    private val expansionsViewModel: ExpansionsViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityExpansionsBinding = DataBindingUtil.setContentView(this, R.layout.activity_expansions)

        activityExpansionsBinding.postsRecyclerView.adapter = mAdapter

        val horizontalDecoration = DividerItemDecoration(
                activityExpansionsBinding.postsRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL)
        val horizontalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_divider)
        horizontalDecoration.setDrawable(horizontalDivider!!)
        activityExpansionsBinding.postsRecyclerView.addItemDecoration(horizontalDecoration)

        if (isNetworkAvailable()) {
            expansionsViewModel.getExpansions()
        } else {
            Toast.makeText(
                    this,
                    getString(R.string.no_internet_connection),
                    LENGTH_SHORT
            ).show()
        }

        with(expansionsViewModel) {

            expansionsData.observe(this@ExpansionsActivity, Observer {
                activityExpansionsBinding.postsProgressBar.visibility = GONE
                mAdapter?.mExpansionList = it.sets
            })

            messageData.observe(this@ExpansionsActivity, Observer {
                Toast.makeText(this@ExpansionsActivity, it, LENGTH_LONG).show()
            })

            showProgressbar.observe(this@ExpansionsActivity, Observer { isVisible ->
                posts_progress_bar.visibility = if (isVisible) VISIBLE else GONE
            })
        }
    }


    override fun onDestroy() {
        mAdapter = null
        super.onDestroy()
    }

    companion object {
        private val TAG = ExpansionsActivity::class.java.name
    }
}
