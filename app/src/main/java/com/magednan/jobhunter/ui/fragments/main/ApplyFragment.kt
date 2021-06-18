package com.magednan.jobhunter.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.magednan.jobhunter.R
import com.magednan.jobhunter.databinding.FragmentApplyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplyFragment : Fragment(R.layout.fragment_apply) {
   val args: ApplyFragmentArgs by navArgs()
    lateinit var binding:FragmentApplyBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentApplyBinding.bind(view)
        val url=args.jobUrl

        binding.webView.apply {
            webViewClient = WebViewClient()
          loadUrl(url)
        }
    }
}