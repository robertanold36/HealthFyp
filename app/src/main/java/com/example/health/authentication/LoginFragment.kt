package com.example.health.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.health.HomeActivity
import com.example.health.R
import com.example.health.authentication.viewmodel.AuthenticationViewModel

class LoginFragment : Fragment(), AuthenticationListener {

    private lateinit var authenticationViewModel: AuthenticationViewModel
    private var pBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pageToRegister = view.findViewById<TextView>(R.id.pageRegister)
        pBar = view.findViewById(R.id.progressBar)
        authenticationViewModel = ViewModelProvider(this)
            .get(AuthenticationViewModel::class.java)

        authenticationViewModel.authenticationRepository.authenticationListener = this

        val email = view.findViewById<EditText>(R.id.email)
        val password = view.findViewById<EditText>(R.id.password)
        val loginBtn = view.findViewById<Button>(R.id.login)

        pageToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_hospitalSelection)
        }

        loginBtn.setOnClickListener {
            val eMail = email.text.toString()
            val passWord = password.text.toString()

            if (eMail.isBlank() ||
                passWord.isBlank()
            ) {
                Toast.makeText(requireActivity(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                authenticationViewModel.login(eMail, passWord)
            }
        }

    }

    override fun onSuccess() {

        pBar?.visibility = View.INVISIBLE
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()

    }

    override fun onLoading() {
        pBar?.visibility = View.VISIBLE
    }

    override fun onFail(message: String) {
        pBar?.visibility = View.INVISIBLE
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }


}