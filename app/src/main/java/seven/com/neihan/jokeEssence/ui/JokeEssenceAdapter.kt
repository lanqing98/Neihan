package seven.com.neihan.jokeEssence.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import seven.com.neihan.R

/**
 * Created by Seven on 2017/8/2.
 */
class JokeEssenceAdapter: RecyclerView.Adapter<JokeEssenceAdapter.JokeEssenceViewHolde>() {

    override fun getItemCount(): Int = 20

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): JokeEssenceViewHolde {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.fragment_jokeessence_recycler_item, parent, false)
        return JokeEssenceViewHolde(view)
    }

    override fun onBindViewHolder(holder: JokeEssenceViewHolde?, position: Int) {

    }

    class JokeEssenceViewHolde(var view: View): RecyclerView.ViewHolder(view) {


    }
}