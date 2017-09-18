package seven.com.neihan.main.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Seven on 2017/7/24.
 */
class ViewPagerAdapter(var fm: FragmentManager?, var fragments:List<Fragment>) : FragmentPagerAdapter(fm) {


    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}