package seven.com.neihan.joke.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_joke.*
import seven.com.neihan.R
import seven.com.neihan.baseClass.BaseHomeFragment
import seven.com.neihan.joke.model.JokeBean
import seven.com.neihan.joke.model.JokeModel

/**
 * Created by Seven on 2017/7/24.
 */
class JokeFragment : BaseHomeFragment() {


    private var request: JokeModel? = null
    private var localReceiver: JokeFragment.LocalReceiver? = null
    private var localBroadcastManager: LocalBroadcastManager? = null

    companion object {
        fun newInstance(): JokeFragment {
            return JokeFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        localBroadcastManager?.unregisterReceiver(localReceiver)
    }

    override fun initParameterInOnCreate() {
        request = JokeModel()
        registerReceiver()
    }

    override fun initOnViewCreated() {

    }

    override fun getLayoutResouse(): Int {
        return  R.layout.fragment_joke
    }

    override fun getRecycleView(): RecyclerView {
        return recycler
    }

    override fun getAdapte(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return JokeAdapter()
    }

    override fun getDecoration(): RecyclerView.ItemDecoration? {
        return DividerItemDecoration(context, LinearLayout.VERTICAL)
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout {
        return refreshlayout
    }

    override fun refresh() {
        getData(true,result = {data, tip, error ->
            if (data.isNotEmpty()) {
                isHasData = true
                activity.runOnUiThread{
                    val jokeAdpter = recyclerView?.adapter as JokeAdapter
                    jokeAdpter.updataRecyclerViewAdapter(data)
                }
            }else {
                isHasData = false
            }
            refreshLayout?.finishRefresh()
        })
    }

    override fun loadMore() {
        getData(isRefresh = false, result = {data, tip, error ->
            if (data.isNotEmpty()) {
                isHasData = true
                activity.runOnUiThread {
                    val jokeAdpter = recyclerView?.adapter as JokeAdapter
                    jokeAdpter.updataRecyclerViewAdapter(data)
                }
            }else {
                isHasData = false
            }
            refreshLayout?.finishLoadmore()
        })
    }

    //下载数据
    private fun getData(isRefresh: Boolean, result: (data: List<JokeBean.Data.Data>, tip: String, error: String?) -> Unit) {
        request?.download(isRefresh, result)
    }

    //注册广播
    private fun registerReceiver() {
        localBroadcastManager = LocalBroadcastManager.getInstance(activity)
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.neihan.main.MainActivity.CONTENTTYPESUCCESS")
        localReceiver = LocalReceiver()
        localBroadcastManager?.registerReceiver(localReceiver, intentFilter)
    }

    class LocalReceiver: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            //处理广播
        }
    }

}