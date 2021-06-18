package com.magednan.jobhunter.ui.fragments.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.magednan.jobhunter.R
import com.magednan.jobhunter.databinding.JobDetailsFragmentBinding
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.viewmodels.JobDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter


@AndroidEntryPoint
class JobDetailsFragment : Fragment(R.layout.job_details_fragment) {

   private lateinit var binding:JobDetailsFragmentBinding

     val args: JobDetailsFragmentArgs by navArgs()
    lateinit var job:Job
    val viewModel by viewModels<JobDetailsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
binding= JobDetailsFragmentBinding.bind(view)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        job=args.job

        Glide.with(binding.root).load(job.companyLogoUrl).placeholder(R.drawable.ic_baseline_no_photography_24).into(binding.jobdFragIvLogo)
        binding.jobdFragTvCategory.setText("job category: " + job.category)
        binding.jobdFragTvCompanyName.setText("companyName: " + job.companyName)
        binding.jobdFragTvDate.setText(job.publicationDate)
        binding.jobdFragTvJobType.setText( "jobType: "+job.jobType)
        binding.jobdFragTvTitle.setText(job.title)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.jobdFragTvDescription.setText(Html.fromHtml(job.description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            binding.jobdFragTvDescription.setText(Html.fromHtml(job.description));
        }
        binding.fBtn.setMenuListener(object : SimpleMenuListenerAdapter() {
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
             var itemId=menuItem.itemId
                when(itemId) {
                   R.id.fab_menu_share->{
                       Toast.makeText(context,"share",Toast.LENGTH_LONG).show()
                       val shareIntent = Intent()
                       shareIntent.action = Intent.ACTION_SEND
                       shareIntent.type="text/plain"
                       shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                       startActivity(Intent.createChooser(shareIntent,getString(R.string.send_to)))
                       return true

                   }  R.id.fab_menu_save->{
                       Toast.makeText(context,"save",Toast.LENGTH_LONG).show()
                    viewModel.uploadJob(job,context)
                    return true

                }  R.id.fab_menu_apply->{
                       Toast.makeText(context,"apply",Toast.LENGTH_LONG).show()
                    val bundle = Bundle().apply {
                        putString("job url", job.url)
                    }
                    findNavController().navigate(
                        R.id.action_jobDetailsFragment_to_applyFragment,bundle
                    )
                    return true
                   }
                }
                return false
            }
        })

    }

}