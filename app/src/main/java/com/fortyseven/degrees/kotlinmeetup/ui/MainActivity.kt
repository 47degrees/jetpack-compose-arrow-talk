package com.fortyseven.degrees.kotlinmeetup.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.fortyseven.degrees.kotlinmeetup.R
import com.fortyseven.degrees.kotlinmeetup.ui.basics.Greeting

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        setupNavigationComponent()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme(colors = lightThemeColors) {
//                //Greeting(name = "Kotlin folks!")
//                InboxExample()
//            }
//        }
//    }

    private fun setupNavigationComponent() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setupActionBarWithNavController(this, navHostFragment.navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(this, R.id.nav_host_fragment).navigateUp()
}

@Preview
@Composable
fun DefaultPreview() =
    MaterialTheme {
        Greeting("Kotlin folks")
    }
