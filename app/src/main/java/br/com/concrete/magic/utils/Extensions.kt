package br.com.concrete.magic.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import br.com.concrete.magic.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.BaselineLayout
import java.lang.reflect.Field

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    //Internet connectivity check in Android Q
    val networks = connectivityManager.allNetworks
    var hasInternet = false
    if (networks.isNotEmpty()) {
        for (network in networks) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            if (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) hasInternet = true
        }
    }
    return hasInternet
}

/**
 * Make selected position active
 */
fun BottomNavigationView.active(position: Int) {
    menu.getItem(position).isChecked = true
}

private var baselineHeight = 0

fun com.google.android.material.bottomnavigation.BottomNavigationView.removeIcons() {
    val view = this
    val menuView = view.getChildAt(0) as BottomNavigationMenuView
    for (i in 0 until menuView.childCount) {
        val itemView = menuView.getChildAt(i) as BottomNavigationItemView
        val baseline = itemView.getChildAt(1) as BaselineLayout
        val layoutParams = baseline.layoutParams as FrameLayout.LayoutParams
        baselineHeight =
            if (baselineHeight > 0) baselineHeight else (menuView.height + baseline.height) / 2
        layoutParams.height = baselineHeight
        baseline.layoutParams = layoutParams
    }
}

/*fun com.google.android.material.bottomnavigation.BottomNavigationView.disableShiftMode() {
    val view = this
    val menuView = view.getChildAt(0) as com.google.android.material.bottomnavigation.BottomNavigationMenuView
    try {
        val shiftingMode: Field = menuView.javaClass.getDeclaredField("mShiftingMode")
        shiftingMode.setAccessible(true)
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.setAccessible(false)
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i)
                    as com.google.android.material.bottomnavigation.BottomNavigationItemView
            item.setShifting(false)
            // set once again checked value, so view will be updated
            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Log.e("BNVHelper", "Unable to get shift mode field", e)
    } catch (e: IllegalAccessException) {
        Log.e("BNVHelper", "Unable to change value of shift mode", e)
    }
}*/

fun FragmentManager.detach() {
    findFragmentById(R.id.container)?.also {
        beginTransaction().detach(it).commit()
    }
}

fun FragmentManager.attach(fragment: Fragment, tag: String) {
    if (fragment.isDetached) {
        beginTransaction().attach(fragment).commit()
    } else {
        beginTransaction().add(R.id.container, fragment, tag).commit()
    }
    // Set a transition animation for this transaction.
    beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
}

fun FragmentManager.switchFragment(fragment: Fragment, tag: String): Boolean {
    return fragment.let {
        if (it.isAdded) return false
        detach()
        attach(it, tag)

        // Immediately execute transactions
        executePendingTransactions()
    }
}