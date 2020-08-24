package biz.ddroid.footballpredicts.ui.matches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import biz.ddroid.footballpredicts.R

class CompletedMatchesFragment : Fragment() {

    companion object {
        fun newInstance() = CompletedMatchesFragment()
    }

    private lateinit var viewModel: CompletedMatchesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_matches_completed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CompletedMatchesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
