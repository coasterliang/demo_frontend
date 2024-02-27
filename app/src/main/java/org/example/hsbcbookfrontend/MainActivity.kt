package org.example.hsbcbookfrontend

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.therouter.TheRouter
import com.therouter.router.Route
import org.example.hsbcbookfrontend.databinding.ActivityMainBinding
import org.example.hsbcbookfrontend.viewmodel.BookViewModel

@Route(path = "main")
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookViewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TheRouter.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

        bookViewModel =
            ViewModelProvider(this)[BookViewModel::class.java]
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Add Record", Snackbar.LENGTH_LONG)
                .setAction("Action", object : OnClickListener {
                    override fun onClick(v: View?) {
                        val fragment: Fragment? = TheRouter.build("updatepage").createFragment()
                        if (fragment != null) {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, fragment)
                                .addToBackStack(null)
                                .commitAllowingStateLoss()
                        }
                    }
                }).show()
        }

        val _fragment: Fragment? = TheRouter.build("listpage").createFragment()
        if (_fragment != null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment_content_main, _fragment).addToBackStack(null)
                .commitAllowingStateLoss()
        }
        LiveEventBus.get<Int>("openmodify").observe(this) {

            val fragment: Fragment? = TheRouter.build("updatepage").withString("id", it.toString()).createFragment()
            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, fragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
        LiveEventBus.get<Int>("delete").observe(this) {
            bookViewModel.deleteBook(it)

        }
    }
}