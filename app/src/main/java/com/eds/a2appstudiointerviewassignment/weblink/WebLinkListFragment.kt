package com.eds.a2appstudiointerviewassignment.weblink

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.eds.a2appstudiointerviewassignment.databinding.FragmentWeblinkListBinding

class WebLinkListFragment: Fragment(){

    private val viewModel: WebLinkViewModel by activityViewModels()

    private var _binding: FragmentWeblinkListBinding? = null
    private val binding : FragmentWeblinkListBinding
        get() = _binding!!

    companion object {
        val TAG = "WebLinkListFragment"
        fun itSelf() = WebLinkListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWeblinkListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = WebLinkListAdapter()
        binding.webLinkRecyclerView.adapter = adapter
        binding.webLinkRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.list.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            if (it.isEmpty()) {
                binding.emptyAnimationView.visibility = View.VISIBLE
                binding.textViewStatus.visibility = View.VISIBLE
            } else {
                binding.emptyAnimationView.visibility = View.GONE
                binding.textViewStatus.visibility = View.GONE
            }
            Log.v(TAG, "Updated List => $it")
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}