package com.niran.nasaapplication.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.niran.nasaapplication.NasaApplication
import com.niran.nasaapplication.R
import com.niran.nasaapplication.databinding.ActivityNasaBinding
import com.niran.nasaapplication.viemwodels.NasaViewModel
import com.niran.nasaapplication.viemwodels.NasaViewModelFactory

class NasaActivity : AppCompatActivity() {

    private lateinit var controller: NavController

    private var _binding: ActivityNasaBinding? = null
    private val binding get() = _binding!!

    val viewModel: NasaViewModel by viewModels {
        NasaViewModelFactory((application as NasaApplication).nasaRepository, application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNasaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        controller = navHostFragment.navController

        setupActionBarWithNavController(controller)

        binding.apply {
            bnvAppNavigator.setupWithNavController(controller)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return controller.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}