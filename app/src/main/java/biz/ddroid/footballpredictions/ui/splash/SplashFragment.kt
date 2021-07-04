package biz.ddroid.footballpredictions.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import biz.ddroid.footballpredictions.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate with SingleLiveEvent from Splash screen
        splashViewModel.splashNavCommand.observe(viewLifecycleOwner, { splashNavCommand ->
            val navController = Navigation.findNavController(requireActivity(), R.id.activity_root__fragment__nav_host)

            val mainGraph = navController.navInflater.inflate(R.navigation.app_nav_graph)

            // Way to change first screen at runtime.
            mainGraph.startDestination = when (splashNavCommand) {
                SplashNavCommand.NAVIGATE_TO_MAIN -> R.id.MainFragment
                SplashNavCommand.NAVIGATE_TO_LOGIN -> R.id.LoginFragment
                null -> throw IllegalArgumentException("Illegal splash navigation command")
            }

            navController.graph = mainGraph
        })
    }

}