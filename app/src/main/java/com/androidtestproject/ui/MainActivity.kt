package com.androidtestproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidtestproject.MainApplication
import com.androidtestproject.R
import com.androidtestproject.databinding.ActivityMainBinding
import com.androidtestproject.model.AuthorListItem
import com.androidtestproject.util.Util
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), AuthorViewHolder.EventHAndler {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var job: Job? = null
    private var authorPosition: Int? = null

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val authorAdapter = AuthorAdapter(lifecycle, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        (application as MainApplication).appcomponent.inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        setRecycler()
    }

    private fun setRecycler() {
        activityMainBinding.idAuthorRecyclev.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(this, 2)
        activityMainBinding.idAuthorRecyclev.layoutManager = gridLayoutManager
        activityMainBinding.idAuthorRecyclev.adapter = authorAdapter
        getAuthorList()
        initAdapter()
        activityMainBinding.swipeRefresh.setOnRefreshListener {
            getAuthorList()
            activityMainBinding.swipeRefresh.isRefreshing = false
            authorAdapter.notifyDataSetChanged()
        }
        activityMainBinding.floatingActionButton.setOnClickListener {
            if(Util.isOnline()) {
                activityMainBinding.floatingActionButton.startAnimation(Util.getRotedAnimation(this))
                getAuthorList()
                authorAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this@MainActivity, "${resources.getString(R.string.check_internet)}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun initAdapter() {
        authorAdapter.addLoadStateListener { loadState ->
            activityMainBinding.idAuthorRecyclev.isVisible =loadState.refresh is LoadState.NotLoading
            activityMainBinding.progressBar.isVisible =
            loadState.refresh is LoadState.Loading

        }
    }

    private fun getAuthorList() {
        job?.cancel()
        job = lifecycleScope.launch {
            mainViewModel.getAuthorList().collectLatest {
                authorAdapter.submitData(it)
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onItemClicked(view: View, position: Int, authorListItem: AuthorListItem) {
        this.authorPosition = position
        val intent = AuthorDetailActivity.getStartIntent(this, authorListItem)
        startActivityForResult(intent, AuthorDetailActivity.REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (authorPosition!=null)authorAdapter.notifyItemChanged(authorPosition!!)
    }
}