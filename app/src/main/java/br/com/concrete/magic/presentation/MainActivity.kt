package br.com.concrete.magic.presentation

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.concrete.magic.R
import br.com.concrete.magic.databinding.ActivityMainBinding
import br.com.concrete.magic.helper.BottomNavigationPosition
import br.com.concrete.magic.helper.createFragment
import br.com.concrete.magic.helper.findNavigationPositionById
import br.com.concrete.magic.helper.getTag
import br.com.concrete.magic.presentation.expansions.ExpansionsFragment
import br.com.concrete.magic.utils.active
import br.com.concrete.magic.utils.removeIcons
import br.com.concrete.magic.utils.switchFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.BaselineLayout


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        binding.toolbar.apply {
//            setSupportActionBar(this)
//        }

//        binding.bottomNavigation.removeIcons()
        binding.bottomNavigation.apply {
            // Set a default position
            active(navPosition.position) // Extension function
//            removeIcons()

            // Set a listener for handling selection events on bottom navigation items
            setOnNavigationItemSelectedListener { item ->
                navPosition = findNavigationPositionById(item.itemId)
                switchFragment(navPosition)
            }
        }

        // Add expansion list fragment if this is first creation
        if (savedInstanceState == null) {
            val fragment = ExpansionsFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, ExpansionsFragment.TAG).commit()
        }
    }

    private fun switchFragment(navPosition: BottomNavigationPosition): Boolean {
        return findFragment(navPosition).let {
            supportFragmentManager.switchFragment(it, navPosition.getTag()) // Extension function
        }
    }

    private fun findFragment(position: BottomNavigationPosition): Fragment {
        return supportFragmentManager.findFragmentByTag(position.getTag()) ?: position.createFragment()
    }
}