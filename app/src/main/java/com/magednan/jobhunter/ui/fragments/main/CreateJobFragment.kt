package com.magednan.jobhunter.ui.fragments.main

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.magednan.jobhunter.R
import com.magednan.jobhunter.databinding.CreateJobFragmentBinding
import com.magednan.jobhunter.models.UrgentJob
import com.magednan.jobhunter.viewmodels.CrrateJobViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateJobFragment : Fragment(R.layout.create_job_fragment) {
    val viewModel by viewModels<CrrateJobViewModel>()
    private lateinit var binding: CreateJobFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= CreateJobFragmentBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.profileBtnSubmit.setOnClickListener {
            if (checkField(binding.createJobEtCompany) == true
                && checkField(binding.createJobEtDetials) == true
                && checkField(binding.createJobEtEmail) == true
                && checkField(binding.createJobEtHrAgentName) == true
                && checkField(binding.createJobEtTelPhone) == true
            ) {
                getContext()?.let { it1 ->
                    viewModel.createAJob(UrgentJob(
                        binding.createJobEtHrAgentName.getText().toString(),
                        binding.createJobEtDetials.getText().toString(),
                        binding.createJobEtTelPhone.getText().toString(),
                        binding.createJobEtEmail.getText().toString(),
                        binding.createJobEtCompany.getText().toString())
                        , it1
                    )
                };
            }
        }
    }

    private fun resetFields() {
        binding.createJobEtCompany.text=null
        binding.createJobEtDetials.text=null
        binding.createJobEtEmail.text=null
        binding.createJobEtHrAgentName.text=null
        binding.createJobEtTelPhone.text=null

    }

    private fun checkField(editText: EditText): Boolean? {
        return if (editText.text.toString().length > 2) {
            true
        } else {
            editText.error = "this is required field"
            Toast.makeText(context, "please fill all required fields", Toast.LENGTH_SHORT)
                .show()
            false
        }
    }
}