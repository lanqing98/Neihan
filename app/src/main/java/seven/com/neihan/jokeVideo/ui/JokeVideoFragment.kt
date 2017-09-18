package seven.com.neihan.jokeVideo.ui

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_jokevideo.*
import seven.com.neihan.R
import seven.com.neihan.baseClass.BaseHomeFragment
import seven.com.neihan.jokeVideo.model.JokeVideoBean
import seven.com.neihan.jokeVideo.model.JokeVideoModel
import seven.com.neihan.utils.NeiHanUtil

/**
 * Created by Seven on 2017/7/24.
 */
class JokeVideoFragment : BaseHomeFragment() {

    private var items: List<JokeVideoBean.Data.Data> = listOf()
    private var model: JokeVideoModel? = null

    companion object {
        fun newInstance(): JokeVideoFragment {
            return JokeVideoFragment()
        }
    }

    override fun getLayoutResouse(): Int {
        return R.layout.fragment_jokevideo
    }

    override fun initParameterInOnCreate() {
        model = JokeVideoModel(NeiHanUtil.getScrenWidth(context))
    }

    override fun initOnViewCreated() {

    }

    override fun getRecycleView(): RecyclerView {
        return video_recycler
    }

    override fun getDecoration(): RecyclerView.ItemDecoration? {
        return DividerItemDecoration(context, LinearLayout.VERTICAL)
    }

    override fun getAdapte(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return JokeVideoAdapter(this) as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun getSmartRefreshLayout(): SmartRefreshLayout {
        return video_refreshlayout
    }


    override fun loadMore() {
        download(false, result = {list, tip, error ->
            if (list.isNotEmpty()) {
                isHasData = true
                items
                activity.runOnUiThread{
                    val jokeAdpter = recyclerView?.adapter as JokeVideoAdapter
                    jokeAdpter.updataRecyclerViewAdapter(list)
                }
            }else {
                isHasData = false
            }
            refreshLayout?.finishRefresh()
        })
    }

    override fun refresh() {
        download(true, result = {list, tip, error ->
            if (list.isNotEmpty()) {
                isHasData = true
                activity.runOnUiThread{
                    val jokeAdpter = recyclerView?.adapter as JokeVideoAdapter
                    jokeAdpter.updataRecyclerViewAdapter(list)
                }
            }else {
                isHasData = false
            }
            refreshLayout?.finishRefresh()
        })
    }

    private fun download(isRefresh: Boolean, result: (List<JokeVideoBean.Data.Data>, String, String?) -> Unit) {
        model?.requestDownload(isRefresh, result)
    }
}