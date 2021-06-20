package com.eds.a2appstudiointerviewassignment.weblink

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.eds.a2appstudiointerviewassignment.R
import com.eds.a2appstudiointerviewassignment.databinding.ActivityMainBinding
import com.eds.a2appstudiointerviewassignment.dialogs.AddWebLinkDialog
import com.eds.a2appstudiointerviewassignment.dialogs.StatusDialog
import com.eds.a2appstudiointerviewassignment.model.WebLink
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WebLinkActivity: AppCompatActivity() {

    companion object {
        val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WebLinkViewModel by viewModels()
    private lateinit var dialog: StatusDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = StatusDialog( "Parsing the web link", true)
        dialog.isCancelable = true

        setSupportActionBar(binding.toolbar)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, WebLinkListFragment.itSelf(), WebLinkListFragment.TAG)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add -> {
                displayUrlPrompt()
                return true
            }
            R.id.menu_reshuffle -> {
                viewModel.shuffle()
                return true
            }
            R.id.menu_resort -> {
                viewModel.sort()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayUrlPrompt() {
        val dialog = AddWebLinkDialog {
            onClickPositiveButton(it)
        }
        dialog.show(supportFragmentManager, AddWebLinkDialog.TAG)
    }

    private fun displayErrorDialog() {
        val dialog = StatusDialog("Sorry, we can\'t parse the web link", false)
        dialog.show(supportFragmentManager, StatusDialog.TAG)
    }

    private fun displayLoadingDialog() {
        dialog.show(supportFragmentManager, StatusDialog.TAG)
    }

    private fun dismissLoadingDialog() = dialog.dismiss()

    private fun onClickPositiveButton(url: String) {
        GlobalScope.launch {
            displayLoadingDialog()
            val webLink: WebLink? = viewModel.processWebLinkUrl(url)
            dismissLoadingDialog()
            if (webLink == null) {
                displayErrorDialog()
            } else {
                viewModel.addItem(webLink)
            }
        }
    }
}