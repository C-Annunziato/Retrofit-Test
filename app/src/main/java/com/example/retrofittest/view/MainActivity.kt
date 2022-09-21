package com.example.retrofittest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.retrofittest.R
import com.example.retrofittest.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val itemsAdapter = ListAdapter(arrayListOf())
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel.fetchData()

        val items_list: RecyclerView = findViewById(R.id.items_list)

        items_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemsAdapter
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        val items_list: RecyclerView = findViewById(R.id.items_list)
        val list_error: TextView = findViewById(R.id.list_error)
        val loading_view: ProgressBar = findViewById(R.id.loading_view)

        viewModel.apiResponse.observe(this, Observer { items ->
            items?.let {
                items_list.visibility = View.VISIBLE
                itemsAdapter.updateItems(it)
            }
        })

        viewModel.error.observe(
            this,
            Observer { errorMsg ->
                list_error.visibility = if (errorMsg == null) View.GONE else View.VISIBLE
                list_error.text = "Error\n$errorMsg"
            })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    list_error.visibility = View.GONE
                    items_list.visibility = View.GONE
                }
            }
        })
    }
}