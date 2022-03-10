package com.example.newbasicstructure.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newbasicstructure.R
import com.example.newbasicstructure.core.uI.BaseActivity
import com.example.newbasicstructure.data.local.IntroScreenData
import com.example.newbasicstructure.databinding.ActivityMainBinding
import com.example.newbasicstructure.ui.adapter.IntroAdapter
import com.example.newbasicstructure.viewmodel.DemoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val homeViewModel: DemoViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val linearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    private val items: ArrayList<IntroScreenData?> = ArrayList()
    private var adapter: IntroAdapter? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserver()
        init()
        clickListener()
    }

    private fun clickListener() {
        binding.btnGetStarted.setOnClickListener {
            if (binding.btnGetStarted.text.toString()
                    .equals(resources.getString(R.string.finish), true)
            ) {
                startActivity(Intent(this, TimerScreen::class.java))
            }
        }
    }

    private fun callAPI() {
        lifecycleScope.launch {
            homeViewModel.getSampleData()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserver() {
        homeViewModel.introScreenDataList.observe(this) {
            if (!it.isNullOrEmpty()) {
                items.clear()
                items.addAll(it)
                adapter?.notifyDataSetChanged()
            }
        }
    }

    private val introScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val pastVisibleItems = linearLayoutManager.findLastCompletelyVisibleItemPosition()
            if (pastVisibleItems >= items.size - 1) {
                binding.btnGetStarted.text = resources.getString(R.string.finish)

            } else {
                binding.btnGetStarted.text = resources.getString(R.string.get_started)
            }
        }
    }

    private fun init() {
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = IntroAdapter(this, items)
        binding.recyclerView.adapter = adapter
        // PagerSnapHelper recyclerview behave like viewpager
        PagerSnapHelper().attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.addOnScrollListener(introScrollListener)
        callAPI()
    }

}