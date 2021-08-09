package uz.pdp.adiblar.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import uz.pdp.adiblar.MainActivity
import uz.pdp.adiblar.R
import uz.pdp.adiblar.adapter.MyPagerAdapter
import uz.pdp.adiblar.databinding.CustomTabBinding
import uz.pdp.adiblar.databinding.FragmentHomeBinding
import uz.pdp.adiblar.sharedPreference.YourPreference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var bind: CustomTabBinding
    lateinit var binding: FragmentHomeBinding
    lateinit var categoryList: ArrayList<String>
    lateinit var myPagerAdapter: MyPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        (activity as MainActivity?)?.showBottomNavBar()
        categoryList = ArrayList<String>()
        categoryList.add("Mumtoz adabiyoti")
        categoryList.add("Uzbek adabiyoti")
        categoryList.add("Jahon adabiyoti")
        binding.searchBtn.setOnClickListener {
            findNavController().navigate(R.id.search1Fragment)
        }
        myPagerAdapter = MyPagerAdapter(categoryList, binding.root.context, childFragmentManager)
        binding.viewPager.adapter = myPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tabView = tab.customView
                val card = tabView!!.findViewById<CardView>(R.id.card)
                val tittle = tabView.findViewById<TextView>(R.id.tittle)
                card.setCardBackgroundColor(Color.parseColor("#00B238"))
                tittle.setTextColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tabView = tab.customView
                val card = tabView!!.findViewById<CardView>(R.id.card)
                val tittle = tabView.findViewById<TextView>(R.id.tittle)
                if (YourPreference.getInstance(binding.root.context).getData("theme") == false) {
                    card.setCardBackgroundColor(Color.WHITE)
                    tittle.setTextColor(Color.BLACK)
                } else {
                    card.setCardBackgroundColor(Color.parseColor("#222222"))
                    tittle.setTextColor(Color.WHITE)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
        setTabs()
        return binding.root
    }

    private fun setTabs() {
        val count: Int = binding.tabLayout.tabCount
        for (i in 0 until count) {
            bind = CustomTabBinding.inflate(layoutInflater, null, false)
            bind.tittle.text = categoryList[i]
            if (i == 0) {
                bind.card.setCardBackgroundColor(Color.parseColor("#00B238"))
                bind.tittle.setTextColor(Color.WHITE)
            } else {
                if (YourPreference.getInstance(binding.root.context).getData("theme") == false) {
                    bind.card.setCardBackgroundColor(Color.WHITE)
                    bind.tittle.setTextColor(Color.BLACK)
                } else {
                    bind.card.setCardBackgroundColor(Color.parseColor("#222222"))
                    bind.tittle.setTextColor(Color.WHITE)
                }
            }
            binding.tabLayout.getTabAt(i)?.customView = bind.root
        }
    }

    override fun onResume() {
        super.onResume()

    }
}