package br.com.concrete.magic.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.concrete.magic.R
import br.com.concrete.magic.presentation.expansions.ExpansionsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add expansion list fragment if this is first creation
        if (savedInstanceState == null) {
            val fragment = ExpansionsFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, ExpansionsFragment.TAG).commit()
        }
    }
}