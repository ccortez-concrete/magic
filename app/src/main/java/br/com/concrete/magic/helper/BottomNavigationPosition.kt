package br.com.concrete.magic.helper

import androidx.fragment.app.Fragment
import br.com.concrete.magic.R
import br.com.concrete.magic.presentation.expansions.ExpansionsFragment

enum class BottomNavigationPosition(val position: Int, val id: Int) {
    HOME(0, R.id.home)
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.HOME.id -> BottomNavigationPosition.HOME

    else -> BottomNavigationPosition.HOME
}

fun BottomNavigationPosition.createFragment(): Fragment = when (this) {
    BottomNavigationPosition.HOME -> ExpansionsFragment.newInstance()

}

fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.HOME -> ExpansionsFragment.TAG

}

