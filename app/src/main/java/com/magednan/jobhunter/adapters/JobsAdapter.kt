package com.magednan.jobhunter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magednan.jobhunter.R
import com.magednan.jobhunter.databinding.JobsListItemBinding
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.models.Jobs
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JobsAdapter  @Inject constructor(val context:Context): PagingDataAdapter<Job, JobsAdapter.JobsHolder>(JOBS_COMPARATOR) {
  var lastItem=-1


    override fun onBindViewHolder(holder: JobsHolder, position: Int) {
        if (holder.adapterPosition>lastItem){
            val animation=AnimationUtils.loadAnimation(context,R.anim.item_animation_fall_down)
            holder.binding.parent.startAnimation(animation)
            lastItem=holder.adapterPosition
            var job = getItem(position)
            if (job != null) {
                holder.bind(job)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsHolder {
        val binding=JobsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return JobsHolder(binding)
    }

    private var onItemClickListener: ((Job) -> Unit)? = null

    inner class JobsHolder constructor(val binding:JobsListItemBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(job:Job){
            Glide.with(binding.root).load(job.companyLogoUrl).centerCrop().placeholder(R.drawable.ic_baseline_no_photography_24).into(binding.ivCompanyLogo)
            binding.tvCompanyName.setText(job.companyName)
            binding.tvJobLocation.setText(job.candidateRequiredLocation)
            binding.tvJobTitle.setText(job.title)
            binding.tvJobType.setText(job.jobType)
            binding.tvDate.setText(job.publicationDate)
            binding.parent.setOnClickListener(View.OnClickListener {
                onItemClickListener?.let { it(job) }

            })
        }
    }

    fun setOnItemClickListener(listener: (Job) -> Unit) {
        onItemClickListener = listener
    }
    companion object{
        private val JOBS_COMPARATOR= object : DiffUtil.ItemCallback<Job>() {
            override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
                return  oldItem.id==newItem.id }
            override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
                return oldItem==newItem }
        }
    }


}