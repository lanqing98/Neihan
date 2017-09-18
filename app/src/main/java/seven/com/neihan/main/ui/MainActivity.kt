package seven.com.neihan.main.ui

import android.support.v4.app.Fragment
import android.content.BroadcastReceiver
import android.content.Intent
import android.graphics.Color
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem
import me.majiajie.pagerbottomtabstrip.item.NormalItemView
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener
import seven.com.neihan.R
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.toolbar_main.*
import me.majiajie.pagerbottomtabstrip.PageNavigationView
import seven.com.neihan.baseClass.BaseActivity
import seven.com.neihan.joke.ui.JokeFragment
import seven.com.neihan.jokeEssence.ui.JokeEssenceFragment
import seven.com.neihan.jokePic.ui.JokePicFragment
import seven.com.neihan.jokeVideo.ui.JokeVideoFragment
import seven.com.neihan.main.model.ContentTypeModel

class MainActivity : BaseActivity() {

    private var localReceiver: BroadcastReceiver? = null
    private var localBroadcastManager: LocalBroadcastManager? = null
    private var nav: NavigationController? = null
    private val jokeEssenceFragment: JokeEssenceFragment by lazy { JokeEssenceFragment.newInstance() }
    private val jokeFragment: JokeFragment by lazy { JokeFragment.newInstance() }
    private val jokePicFragment: JokePicFragment by lazy { JokePicFragment.newInstance() }
    private val jokeVideoFragment: JokeVideoFragment by lazy { JokeVideoFragment.newInstance() }

    override fun initInOnCreat() {
        val tab: PageNavigationView = tab
        nav = tab.custom()
                .addItem(newItem(R.drawable.ic_jokeessence_tabbar_nor,R.drawable.ic_jokeessence_tabbar_select,"精华"))
                .addItem(newItem(R.drawable.ic_joke_tabbar_nor,R.drawable.ic_joke_tabbar_select,"段子"))
                .addItem(newItem(R.drawable.ic_jokepic_tabbar_nor,R.drawable.ic_jokepic_tabbar_select,"图片"))
                .addItem(newItem(R.drawable.ic_jokevideo_tabbar_nor,R.drawable.ic_jokevideo_tabbar_select,"视频"))
                .build()
        val pageView = viewPage as ViewPager
        val list: List<Fragment> = listOf(jokeEssenceFragment, jokeFragment, jokePicFragment, jokeVideoFragment)
        pageView.adapter = ViewPagerAdapter(supportFragmentManager, list)
        nav?.setupWithViewPager(pageView)
        pageView.offscreenPageLimit = nav!!.itemCount
        // 设置tab点击监听
        navigationDidSelect()
        //  本地通知
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        //下载contenType
        ContentTypeModel().onRequestContentType(this, successResult = { ->
            //  发送contentType下载完成广播
            val intent = Intent("com.neihan.main.MainActivity.CONTENTTYPESUCCESS")
            localBroadcastManager?.sendBroadcastSync(intent)
        })
    }

    override fun deInitInOnDestroy() {
        localBroadcastManager?.unregisterReceiver(localReceiver)
    }

    override fun onGetLayout(): Int {
        return R.layout.activity_main
    }

    override fun onGetToolBar(): Toolbar {
        return toolbar
    }

    override fun onSetToolBar() {
        toolBar?.setTitle(R.string.jokeEssenceHome)
    }

    override fun backClick() {
        //没有返回按钮不用实现
    }

    private fun newItem(drawable: Int, checkedDrawable: Int, text: String): BaseTabItem {
        val normalItemView: NormalItemView = NormalItemView(this)
        normalItemView.initialize(drawable, checkedDrawable, text)
        normalItemView.setTextDefaultColor(Color.GRAY)
        normalItemView.setTextCheckedColor(Color.parseColor("#2e8abb"))
        return normalItemView
    }

    private fun navigationDidSelect() {
        nav!!.addTabItemSelectedListener(object: OnTabItemSelectedListener {
            override fun onSelected(index: Int, old: Int) {
                when(index) {
                    0 -> {
                        toolBar?.setTitle(R.string.jokeEssenceHome)
                    }
                    1 -> {
                        toolBar?.setTitle(R.string.jokeHome)
                        if (!jokeFragment.isHasData){
                            jokeFragment.onAutoRefresh()
                        }
                    }
                    2 -> {
                        toolBar?.setTitle(R.string.jokePicHome)
                        if (!jokePicFragment.isHasData) {
                            jokePicFragment.onAutoRefresh()
                        }
                    }
                    3 -> {
                        toolBar?.setTitle(R.string.jokeVideoHome)
                        if (!jokeVideoFragment.isHasData) {
                            jokeVideoFragment.onAutoRefresh()
                        }
                    }
                }
            }

            override fun onRepeat(index: Int) {

            }
        })
    }
}
