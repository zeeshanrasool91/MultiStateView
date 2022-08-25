package com.kennyc.sample

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kennyc.view.*

class MainActivity : AppCompatActivity(), MultiStateView.StateListener {
    private lateinit var multiStateView: MultiStateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        multiStateView = findViewById(R.id.multiStateView)
        multiStateView.listener = this
        multiStateView.getErrorView()?.findViewById<Button>(R.id.retry)
            ?.setOnClickListener {
                multiStateView.setLoadingView()
                Toast.makeText(applicationContext, "Fetching Data", Toast.LENGTH_SHORT).show()
                multiStateView.postDelayed({ multiStateView.setContentView() }, 3000L)
            }

        val list: ListView = multiStateView.findViewById(R.id.list)

        val data = arrayOfNulls<String>(100)
        for (i in 0..99) {
            data[i] = "Row $i"
        }

        list.adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.error -> {
                multiStateView.setErrorView()
                return true
            }

            R.id.empty -> {
                multiStateView.setEmptyView()
                return true
            }

            R.id.content -> {
                multiStateView.setContentView()
                return true
            }

            R.id.loading -> {
                multiStateView.setLoadingView()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStateChanged(viewState: MultiStateView.ViewState) {
        Log.v("MSVSample", "onStateChanged; viewState: $viewState")
    }
}