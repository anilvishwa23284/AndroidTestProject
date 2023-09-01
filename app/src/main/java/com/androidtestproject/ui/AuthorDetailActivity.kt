package com.androidtestproject.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.androidtestproject.MainApplication
import com.androidtestproject.R
import com.androidtestproject.model.AuthorListItem
import com.bumptech.glide.Glide
import javax.inject.Inject

class AuthorDetailActivity : AppCompatActivity() {


    private lateinit var authorDetailViewModel: AuthorDetailViewModel

    @Inject
     lateinit var mainViewModelFactory: MainViewModelFactory


    companion object {
        const val REQUEST_CODE = 121
        const val AUTHOR_DETAIL = "author_detail"

        fun getStartIntent(context: Context, authorListItem: AuthorListItem): Intent {
            val intent = Intent(context, AuthorDetailActivity::class.java).apply {
                putExtra(AUTHOR_DETAIL, authorListItem)
            }
            return intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
    }

    private val imageView: ImageView
        get() = findViewById(R.id.author_detail)

    private val textauthorName:TextView
    get() = findViewById(R.id.autherName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }
        (application as MainApplication).appcomponent.inject(this)
        setContentView(R.layout.author_layout)
        authorDetailViewModel =
            ViewModelProvider(this, mainViewModelFactory).get(AuthorDetailViewModel::class.java)
        if (intent.hasExtra(AUTHOR_DETAIL)) {
            val authordetail = intent.getSerializableExtra(AUTHOR_DETAIL) as AuthorListItem
            authorDetailViewModel.setAuthorDetail(authordetail)
        }
        authorDetailViewModel.authorDetail.observe(this, Observer {
            textauthorName.text =it.author
            Glide.with(this)
                .load(it.download_url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        })
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(AUTHOR_DETAIL, authorDetailViewModel.authorDetail.value)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


}