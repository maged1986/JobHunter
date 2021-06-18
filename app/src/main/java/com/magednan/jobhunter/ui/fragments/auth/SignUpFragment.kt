package com.magednan.jobhunter.ui.fragments.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.magednan.jobhunter.R
import com.magednan.jobhunter.databinding.SignUpFragmentBinding
import com.magednan.jobhunter.viewmodels.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.sign_up_fragment) {
    private val PICKFILE_REQUEST_CODE = 1234
    private lateinit var selectedImageUri: Uri


    private lateinit var binding: SignUpFragmentBinding
    val viewModel by viewModels<SignUpViewModel>()
    lateinit var imageUrl:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignUpFragmentBinding.bind(view)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.registerEtLayoutConfirmPassword.setCounterEnabled(true)
        binding.registerEtLayoutConfirmPassword.counterMaxLength = 10
        binding.registerEtLayoutPassword.setCounterEnabled(true)
        binding.registerEtLayoutPassword.counterMaxLength = 10



        binding.registerBtnRegister.setOnClickListener(View.OnClickListener {
            createNewUser()
        })

        binding.registerTvLinkLogin.setOnClickListener(View.OnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.signInFragment) };

        })

    }

    fun createNewUser() {
        if (doStringsMatch(binding.registerEtPassword, binding.registerEtConfirmPassword)) {
            if ((checkField(binding.registerEtEmail) == true
                        && checkField(binding.registerEtJob) == true
                        && checkField(binding.registerEtName) == true
                        && checkField(binding.registerEtPassword) == true
                        && checkField(binding.registerEtConfirmPassword) == true)
            ) {
                getContext()?.let {
                    viewModel.createNewUser(
                        binding.registerEtName.getText().toString()
                        , binding.registerEtJob.getText().toString()
                        , binding.registerEtEmail.getText().toString()
                        , binding.registerEtPassword.getText().toString(),
                        it
                    )
                };
                resetFields()


            } else {
                Toast.makeText(getContext(), "this is required field", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "paswords doesnmatch", Toast.LENGTH_SHORT).show();
        }
    }
    private fun doStringsMatch(editText1: EditText, editText2: EditText): Boolean {
    return editText1.text.toString() == editText2.text.toString()
}

private fun resetFields() {
    binding.registerEtEmail.text = null
    binding.registerEtName.text = null
    binding.registerEtPassword.text = null
    binding.registerEtConfirmPassword.text = null
}

    private fun checkField(editText: EditText): Boolean? {
        return if (editText.text.toString().length > 2) {
            true
        } else {
            editText.error = "this is reqired field"
            Toast.makeText(context, "please fill all required fields", Toast.LENGTH_SHORT)
                .show()
            false
        }
    }
}