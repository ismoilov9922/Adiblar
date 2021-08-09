package uz.pdp.adiblar.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.pdp.adiblar.ui.PagerFragment

class MyPagerAdapter(
    private val list: List<String>,
    private val context: Context,
    fm: FragmentManager,
) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PagerFragment.newInstance(list[0])
            }
            1 -> {
                PagerFragment.newInstance(list[1])
            }
            2 -> {
                PagerFragment.newInstance(list[2])
            }
            else -> PagerFragment.newInstance(list[0])
        }
    }
}