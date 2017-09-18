package seven.com.neihan.joke.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import seven.com.neihan.R
import seven.com.neihan.joke.model.JokeBean
import kotlinx.android.synthetic.main.fragment_joke_recycler_item.view.*

/**
 * Created by Seven on 2017/8/2.
 */
class JokeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<JokeBean.Data.Data> = listOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.fragment_joke_recycler_item, parent, false)
        return JokeFragmentViewHolde(view, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val model = items.elementAt(position)
        val holderView: JokeFragmentViewHolde = holder as JokeFragmentViewHolde
        holderView?.loadData(model)
    }

    class JokeFragmentViewHolde(var view: View, var parent: ViewGroup?): RecyclerView.ViewHolder(view) {
        fun loadData(bean: JokeBean.Data.Data) {
            if (bean.group?.user?.name == null || bean.group?.content == null) {
                parent?.layoutParams?.height = 0
                return
            }else {
                parent?.layoutParams?.height = LinearLayout.LayoutParams.WRAP_CONTENT
            }
            view.userName.text = bean.group?.user?.name ?: "森林"
            view.jokeContent.text = bean.group?.content
        }
    }

    fun updataRecyclerViewAdapter(list: List<JokeBean.Data.Data>){
        items = list
        notifyDataSetChanged()
    }
}