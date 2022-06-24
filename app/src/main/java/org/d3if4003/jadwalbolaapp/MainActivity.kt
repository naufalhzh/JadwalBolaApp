package org.d3if4003.jadwalbolaapp

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.d3if4003.jadwalbolaapp.databinding.ActivityMainBinding
import org.d3if4003.jadwalbolaapp.preferences.UIModeManager
import org.d3if4003.jadwalbolaapp.preferences.dataStore
import org.d3if4003.jadwalbolaapp.util.NetworkStateReceiver

class MainActivity : AppCompatActivity(), NetworkStateReceiver.NetworkStateReceiverListener  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var networkStateReceiver: NetworkStateReceiver
    private lateinit var uiModeManager: UIModeManager
    private val viewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        uiModeManager = UIModeManager(dataStore)
        
        uiModeManager.isNightMode.asLiveData().observe(this){
            Log.d("nganug", it.toString())
            if(it != null){
                if(it){
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }
            } else {
                saveState(false)
                viewModel.setNightMode(false)
            }
        }
    
        networkStateReceiver = NetworkStateReceiver()
        networkStateReceiver.addListener(this)
        registerReceiver(networkStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.favoriteFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> showBottomNav(visible = true)
                R.id.favoriteFragment -> showBottomNav(visible = true)
                else -> showBottomNav(visible = false)
            }
            
        }
        
        viewModel.isNightMode.observe(this){
            saveState(it)
        }
    }
    
    fun saveState(boolean: Boolean){
        lifecycleScope.launch {
            uiModeManager.saveData(boolean)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    
    fun showBottomNav(visible: Boolean){
        binding.bottomNavigationView.visibility = if(visible) View.VISIBLE else View.GONE
    }
    
    override fun networkAvailable() {
        viewModel.setNetworkState(true)
    }
    
    override fun networkUnavailable() {
        viewModel.setNetworkState(false)
    }
}