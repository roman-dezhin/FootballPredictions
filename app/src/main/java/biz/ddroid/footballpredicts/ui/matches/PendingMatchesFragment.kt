package biz.ddroid.footballpredicts.ui.matches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import biz.ddroid.footballpredicts.R

class PendingMatchesFragment : Fragment() {

    companion object {
        fun newInstance() = PendingMatchesFragment()
    }

    private lateinit var viewModel: PendingMatchesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_matches_pending, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PendingMatchesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
