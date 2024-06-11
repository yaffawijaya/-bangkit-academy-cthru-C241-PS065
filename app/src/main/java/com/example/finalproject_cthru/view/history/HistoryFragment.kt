package com.example.finalproject_cthru.view.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject_cthru.R
import com.example.finalproject_cthru.databinding.FragmentHistoryBinding
import com.example.finalproject_cthru.view.camera.CameraActivity
import com.example.finalproject_cthru.view.upload.UploadActivity


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHistory
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.analyzeButton.setOnClickListener {
            val intent = Intent(requireActivity(), UploadActivity::class.java)
            requireActivity().startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}