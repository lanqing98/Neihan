package seven.com.neihan.baseClass

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

/**
 * Created by Seven on 2017/8/17.
 */
abstract class BaseHomeFragment : Fragment() {

    protected var rootView: View? = null
    protected var refreshLayout: SmartRefreshLayout? = null
    protected var recyclerView: RecyclerView? = null
    var isHasData: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParameterInOnCreate()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater?.inflate(getLayoutResouse(), container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetRecyclerView()
        onSetRefresh()
        initOnViewCreated()

    }

    //设置刷新
    private fun onSetRefresh() {
        refreshLayout = getSmartRefreshLayout()
        if (refreshLayout == null) return
        refreshLayout?.setRefreshHeader(MaterialHeader(activity))
        refreshLayout?.setRefreshFooter(BallPulseFooter(activity).setSpinnerStyle(SpinnerStyle.Scale))
        refreshLayout?.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(refreshlayout: RefreshLayout?) {
                refresh()
            }
        })
        refreshLayout?.setOnLoadmoreListener(object : OnLoadmoreListener {
            override fun onLoadmore(refreshlayout: RefreshLayout?) {
                loadMore()
            }
        })
    }

    fun onAutoRefresh() {
        refreshLayout?.autoRefresh()
    }

    // 设置recyclerView
    private fun onSetRecyclerView() {
        recyclerView = getRecycleView()
        recyclerView?.adapter = getAdapte()
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        if (getDecoration() != null) {
            recyclerView?.addItemDecoration(getDecoration())
        }
    }

    // 初始化一些参数
    abstract fun initParameterInOnCreate()
    //初始化View
    abstract fun initOnViewCreated()
    //获取布局
    abstract fun getLayoutResouse(): Int
    //获取recycleViewAdapte
    abstract fun getAdapte(): RecyclerView.Adapter<RecyclerView.ViewHolder>
    // 设置RecyclerView的Decoration
    abstract fun getDecoration(): RecyclerView.ItemDecoration?
    // 获取rectycleView
    abstract fun getRecycleView(): RecyclerView
    // 获取刷新layout
    abstract fun getSmartRefreshLayout(): SmartRefreshLayout
    // 刷新结果
    abstract fun refresh()
    // 上拉加载结果
    abstract fun loadMore()
}