package com.example.delawebtestapp.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.delawebtestapp.R
import com.example.delawebtestapp.presentation.lisrt.ListNewsFragment
import com.example.delawebtestapp.presentation.news.NewsFragment

class MainActivity : AppCompatActivity(), FragmentListNewsInteractor, FragmentNewsInteractor {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ListNewsFragment()).commit()
        }
    }

    override fun onOpenNews(index: Int) {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.animator.to_left_in,
            R.animator.to_left_out,
            R.animator.to_right_in,
            R.animator.to_right_out
        ).replace(R.id.container, NewsFragment.newInstance(index))
            .addToBackStack(null).commit()
    }

    override fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)

        packageManager?.let {
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.error_open_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}