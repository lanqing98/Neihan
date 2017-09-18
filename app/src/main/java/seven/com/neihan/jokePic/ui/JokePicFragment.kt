package seven.com.neihan.jokePic.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_jokepic.*
import seven.com.neihan.R
import seven.com.neihan.baseClass.BaseHomeFragment
import seven.com.neihan.jokePic.model.JokePicBean
import seven.com.neihan.jokePic.model.JokePicViewModel

/**
 * Created by Seven on 2017/7/24.
 */
class JokePicFragment: BaseHomeFragment() {

    private var jokePicModel: JokePicViewModel? = null

    companion object {
        fun newInstance(): JokePicFragment {
            return JokePicFragment()
        }
    }

    override fun initParameterInOnCreate() {
        onGetJokePicVM()
    }

    override fun initOnViewCreated() {

    }

    override fun getLayoutResouse(): Int {
        return R.layout.fragment_jokepic
    }

    override fun getAdapte(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return JokePicAdapter(this)
    }

    override fun getDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    override fun getRecycleView(): RecyclerView {
        return recycler
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout {
        return refreshlayout
    }

    override fun refresh() {
        onGetData(true,result = {data, tip, error ->
            if (data.isNotEmpty()) {
                isHasData = true
                activity.runOnUiThread{
                    val jokeAdpter = recyclerView?.adapter as JokePicAdapter
                    jokeAdpter.updataRecyclerViewAdapter(data)
                }
            }else {
                isHasData = false
            }
            refreshLayout?.finishRefresh()
        })
    }

    override fun loadMore() {
        onGetData(isRefresh = false, result = {data, tip, error ->
            if (data.isNotEmpty()) {
                isHasData = true
                activity.runOnUiThread {
                    val jokeAdpter = recyclerView?.adapter as JokePicAdapter
                    jokeAdpter.updataRecyclerViewAdapter(data)
                }
            }else {
                isHasData = false
            }
            refreshLayout?.finishLoadmore()
        })
    }

    //设置下载对象
    private fun onGetJokePicVM() {
        val resources = context.resources
        val dm = resources.displayMetrics
        jokePicModel = JokePicViewModel(dm.widthPixels)
    }

    //下载数据
    private fun onGetData(isRefresh: Boolean, result: (data: List<JokePicBean.Data.Data>, tip: String, error: String?) -> Unit) {
        jokePicModel?.download(isRefresh, result)
    }
}