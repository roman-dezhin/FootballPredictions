package biz.ddroid.footballpredictions.ui.matches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

import biz.ddroid.footballpredictions.R

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.text)
            textView.text = getInt(ARG_OBJECT).toString()
        }
        val textView: TextView = requireView().findViewById(R.id.pending)
        viewModel = ViewModelProvider(this).get(PendingMatchesViewModel::class.java)
        viewModel.getMatches().observe(viewLifecycleOwner, { matches ->
            if (matches.isEmpty())
                textView.text = "No data"
            else
                textView.text = matches.toString()
        })
        viewModel.error.observe(viewLifecycleOwner, { error ->
            textView.text = error
        })
    }
}
