package com.example.health.authentication

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.health.HomeActivity
import com.example.health.R
import com.example.health.authentication.viewmodel.AuthenticationViewModel
import com.example.health.model.PatientModel
import com.example.health.util.UtilityClass.Companion.savePrefs
import com.google.gson.Gson
import java.util.*


class RegisterFragment : Fragment(), AuthenticationListener {

    private lateinit var authenticationViewModel: AuthenticationViewModel
    private val args: RegisterFragmentArgs by navArgs()
    private var pBar: ProgressBar? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val doctorDetails = args.doctorDetails
        Log.e("Testing", doctorDetails.toString())
        val submitBtn = view.findViewById<Button>(R.id.signup)

        authenticationViewModel = ViewModelProvider(this)
            .get(AuthenticationViewModel::class.java)

        authenticationViewModel.authenticationRepository.authenticationListener = this
        pBar=view.findViewById(R.id.progressBar)

        val date = Calendar.getInstance().time.toString()
        val email = view.findViewById<EditText>(R.id.email)
        val name = view.findViewById<EditText>(R.id.full_name)
        val passWord = view.findViewById<EditText>(R.id.password)
        val diseaseSpinner = view.findViewById<Spinner>(R.id.diseases)


        submitBtn.setOnClickListener {

            val fullName = name.text.toString()
            val eMail = email.text.toString()
            val password = passWord.text.toString()
            val diseaseSelected = diseaseSpinner.selectedItem.toString()

            if (fullName.isBlank() ||
                eMail.isBlank() ||
                password.isBlank()
            ) {
                Toast.makeText(activity, "Please fil all the fields", Toast.LENGTH_LONG).show()
            } else if (password.length < 6) {
                Toast.makeText(activity, "Password must at least 6 character", Toast.LENGTH_LONG)
                    .show()
            } else {
                val patientModel = PatientModel(
                    fullName,
                    eMail,
                    diseaseSelected,
                    doctorDetails.hospital,
                    date,
                    doctorDetails.doctorId
                )
                authenticationViewModel.createUserAccount(patientModel,password)
            }

        }

    }

    override fun onSuccess(data:Any?) {

        savePrefs(
            requireContext(),data!!
        )
        pBar?.visibility = View.INVISIBLE
        val intent=Intent(requireActivity(),HomeActivity::class.java)
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