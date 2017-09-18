package seven.com.neihan.jokeEssence.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import seven.com.neihan.R
import kotlinx.android.synthetic.main.fragment_jokeessence.*

/**
 * Created by Seven on 2017/7/24.
 */
class JokeEssenceFragment : Fragment() {
    //本地广播
    var localBroadcastManager: LocalBroadcastManager? = null
    var localReceiver: LocalReceiver? = null
    var recyclerView: RecyclerView? = null

    companion object {
        fun newInstance(): JokeEssenceFragment {
            return JokeEssenceFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_jokeessence, container, false)
        return root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = recycler
        recyclerView?.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        recyclerView?.adapter = JokeEssenceAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        localBroadcastManager?.unregisterReceiver(localReceiver)
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