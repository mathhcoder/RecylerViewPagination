package android.kotlin.recylerviewpagination


//import android.R
import android.kotlin.recylerviewpagination.R
import android.kotlin.recylerviewpagination.PaginationListener.Companion.PAGE_START
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import butterknife.BindView
import butterknife.ButterKnife
import android.kotlin.recylerviewpagination.PaginationListener
import android.util.Log

import com.androidwave.recyclerviewpagination.PostRecyclerAdapter
import java.util.*


class MainActivity : AppCompatActivity(), OnRefreshListener {

    var mRecyclerView: RecyclerView? = null

    var swipeRefresh: SwipeRefreshLayout? = null
    private var adapter: PostRecyclerAdapter? = null
    private var currentPage = PAGE_START
    private var isLastPage = false
    private val totalPage = 1000
    private var isLoading = false
    var itemCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh = findViewById(R.id.swipeRefresh)
        mRecyclerView = findViewById(R.id.recyclerView)


        swipeRefresh!!.setOnRefreshListener(this)
        mRecyclerView!!.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.layoutManager = layoutManager
        adapter = PostRecyclerAdapter(ArrayList())
        mRecyclerView!!.adapter = adapter

        doApiCall()

        mRecyclerView!!.addOnScrollListener(object : PaginationListener(layoutManager) {
            override fun loadMoreItems() {
                Log.e("ENENT" , "MainAc loading More Items ")
                isLoading = true
                currentPage++
                doApiCall()
            }

            override val isLastPage: Boolean
                get() {
                   return currentPage > 100
                }
            override var isLoading: Boolean = false
                get() {
                    return false
                }
        })
    }

    private fun doApiCall() {
        val items = ArrayList<PostItem>()
        Handler().postDelayed({
            Log.e("ENENT" , "Main Ac cdoApiCall")
            for (i in 0..9) {
                itemCount++
                val postItem = PostItem()
                postItem.title = "getString(R.string.text_title)" + itemCount
                postItem.description = "getString(R.string.text_description)"
                items.add(postItem)
            }

            if (currentPage != PAGE_START) adapter!!.removeLoading()
            adapter!!.addItems(items)
            swipeRefresh!!.isRefreshing = false


            if (currentPage < totalPage) {
                Log.e("ENENT" , "MainAc  $currentPage < $totalPage")
                adapter!!.addLoading()
            } else {
                Log.e("ENENT" , "MainAc  $currentPage >= $totalPage")
                isLastPage = true
            }
            isLoading = false
        }, 1500)
    }

    override fun onRefresh() {
        Log.e("ENENT" , "Ac Main onRefresh")
        itemCount = 0
        currentPage = PAGE_START
        isLastPage = false
        adapter!!.clear()
        doApiCall()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}