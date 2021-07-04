package biz.ddroid.footballpredictions.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import biz.ddroid.footballpredictions.R
import biz.ddroid.footballpredictions.ui.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

const val ARG_OBJECT = "tab"

class MatchesTabHostFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var matchesTabAdapter: MatchesTabAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches_tab_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController = findNavController()
        if (!userViewModel.isCached()) {
            navController.navigate(R.id.LoginFragment)
        } else {
            userViewModel.getUser()
        }

        matchesTabAdapter = MatchesTabAdapter(this)

        val tabTitleList = listOf(
            getString(R.string.tab_title_matches_new),
            getString(R.string.tab_title_matches_pending),
            getString(R.string.tab_title_matches_completed))

        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = matchesTabAdapter
        viewPager.offscreenPageLimit = 2

        tabLayout = view.findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()
    }
}

class MatchesTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewMatchesFragment()
            1 -> PendingMatchesFragment()
            2 -> CompletedMatchesFragment()
            else -> NewMatchesFragment()
        }
    }
}