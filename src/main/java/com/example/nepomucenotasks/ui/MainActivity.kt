package com.example.nepomucenotasks.ui

import android.content.Intent
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.nepomucenotasks.R
import com.example.nepomucenotasks.data.comom.SecurityPreferences
import com.example.nepomucenotasks.data.constants.SharedPreferencesConstantes
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mSecurityPreferences = SecurityPreferences(this)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toogle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawerLayout.setDrawerListener(toogle)
        toogle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        startDefaultFragment()
        formatUserName()
        formatDate()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)

        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_done -> fragment = TaskListFragment.newInstance(SharedPreferencesConstantes.Taskfilter.Complete)
            R.id.nav_todo -> fragment = TaskListFragment.newInstance(SharedPreferencesConstantes.Taskfilter.Todo)
            R.id.nav_logout -> {
                handlerLogout()
                return false
            }
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frameContent, fragment!!).commit()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun handlerLogout() {
        mSecurityPreferences.removeStoredString(SharedPreferencesConstantes.Key.USER_ID)
        mSecurityPreferences.removeStoredString(SharedPreferencesConstantes.Key.USER_NAME)
        mSecurityPreferences.removeStoredString(SharedPreferencesConstantes.Key.USER_EMAIL)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun startDefaultFragment() {
        val fragment: Fragment = TaskListFragment.newInstance(SharedPreferencesConstantes.Taskfilter.Complete)
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()

    }

    private fun formatUserName() {
        val str = "Olá, ${mSecurityPreferences.getStoredString(SharedPreferencesConstantes.Key.USER_NAME)}!"
        txtHello.text = str
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val header = navigationView.getHeaderView(0)
        val name = header.findViewById<TextView>(R.id.textName)
        val email = header.findViewById<TextView>(R.id.textEmail)

        name.text = mSecurityPreferences.getStoredString(SharedPreferencesConstantes.Key.USER_NAME)
        email.text = mSecurityPreferences.getStoredString(SharedPreferencesConstantes.Key.USER_EMAIL)


    }

    private fun formatDate() {

        val c = Calendar.getInstance()

        val days = arrayOf("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado")
        val months = arrayOf("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novemembro", "Dezembro")

        val str = "${days[c.get(Calendar.DAY_OF_WEEK) - 1]}, ${c.get(Calendar.DAY_OF_MONTH)} de ${months[c.get(Calendar.MONTH)]}"
        txtDateDescription.text = str

    }

}
