package com.magednan.jobhunter.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.magednan.jobhunter.R
import com.magednan.jobhunter.databinding.UrgentJobItemBinding
import com.magednan.jobhunter.models.UrgentJob
import javax.inject.Inject


class UrgentJobAdapter @Inject constructor(val context: Context) :
    PagingDataAdapter<UrgentJob, UrgentJobAdapter.JobsHolder>(
        UrgentJobAdapter.JOBS_COMPARATOR
    ) {
    var lastItem = -1
    override fun onBindViewHolder(holder: UrgentJobAdapter.JobsHolder, position: Int) {
        if (holder.adapterPosition > lastItem) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
            holder.binding.root.startAnimation(animation)
            lastItem = holder.adapterPosition
            var urgentJob = getItem(position)
            if (urgentJob != null) {
                holder.bind(urgentJob)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrgentJobAdapter.JobsHolder {
        val binding =
            UrgentJobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobsHolder(binding)
    }

    inner class JobsHolder constructor(val binding: UrgentJobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(job: UrgentJob) {
            binding.urgentJobItemTvCompany.setText(job.company)
            binding.urgentJobItemTvHrAgent.setText(job.hrAgent)
            binding.urgentJobItemTvDetails.setText(job.details)
            binding.urgentJobItemBtnEmail.setOnClickListener {
                sendToEmail(job)
            }

        }
    }

    companion object {
        private val JOBS_COMPARATOR = object : DiffUtil.ItemCallback<UrgentJob>() {
            override fun areItemsTheSame(oldItem: UrgentJob, newItem: UrgentJob): Boolean {
                return oldItem.telPhone == newItem.telPhone
            }
            override fun areContentsTheSame(oldItem: UrgentJob, newItem: UrgentJob): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun sendToEmail(urgentJob: UrgentJob) {
        val selectorIntent = Intent(Intent.ACTION_SENDTO)
        val urlString =
            "mailto:" + Uri.encode(urgentJob.email) + "?subject=" + Uri.encode(urgentJob.hrAgent) + "&body=" + Uri.encode(
                urgentJob.details
            )
        selectorIntent.data = Uri.parse(urlString)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(urgentJob.email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, urgentJob.hrAgent)
        emailIntent.putExtra(Intent.EXTRA_TEXT, urgentJob.details)
        emailIntent.selector = selectorIntent

        context.startActivity(Intent.createChooser(emailIntent, "Send email"))
    }
    fun call(urgentJob: UrgentJob){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse(urgentJob.telPhone))
        context.startActivity(intent)
    }

}