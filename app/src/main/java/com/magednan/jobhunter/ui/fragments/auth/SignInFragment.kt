package com.magednan.jobhunter.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.magednan.jobhunter.R
import com.magednan.jobhunter.databinding.SignInFragmentBinding
import com.magednan.jobhunter.ui.activities.MainActivity
import com.magednan.jobhunter.viewmodels.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.sign_in_fragment) {


    private lateinit var binding:SignInFragmentBinding
    val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= SignInFragmentBinding.bind(view)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.loginBtnLogin.setOnClickListener( View.OnClickListener {
            if (checkField(binding.loginEtEmail) == true && checkField(binding.loginEtPassword) == true) {
                getContext()?.let { it1 ->
                    viewModel.login(binding.loginEtEmail.getText().toString(),
                        binding.loginEtPassword.getText().toString(), it1
                    )
                };
            }
        })
        binding.loginTvRegister.setOnClickListener(View.OnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.signUpFragment) };

        })

    }

    private fun resetFields() {
        binding.loginEtEmail.text=null
        binding.loginEtPassword.text=null

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