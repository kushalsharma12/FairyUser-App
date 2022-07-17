package com.kushalsharma.fairyuserapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.fairyuserapp.Dao.UserDao
import com.kushalsharma.fairyuserapp.Modals.User
import com.kushalsharma.fairyuserapp.R
import com.kushalsharma.fairyuserapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    val dbp = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //getting current user
        auth = Firebase.auth
        val currentUser = auth.currentUser


        val ref = dbp.collection("Fairywalla").document(currentUser!!.uid)
        ref.get()
            .addOnSuccessListener {
                Log.d("data", "${it.data}")
                val data = it.toObject(User::class.java)
                binding.profileAddressEt.setText(data!!.permanentAddress)
                binding.profilePhNo.setText(data!!.phNo)

            }.addOnFailureListener {
                Log.d("error", "${it.message}")
            }


        Glide.with(binding.profileImage).load(currentUser!!.photoUrl)
            .transform(CenterCrop(), RoundedCorners(32))
            .placeholder(R.drawable.ic_loading_placeholder)
            .into(binding.profileImage)
        binding.profileName.text = currentUser.displayName.toString()
        binding.profileEmail.text = currentUser.email.toString()



        binding.updateProfileBtn.setOnClickListener {

            val getPhNo = binding.profilePhNo.text.toString()
            val getPermanentAddress = binding.profileAddressEt.text.toString()

            val user = User(
                currentUser.uid, currentUser.displayName,
                currentUser.photoUrl.toString(), getPhNo, getPermanentAddress
            )
            val usersDao = UserDao()
            usersDao.addUser(user)
            Toast.makeText(this.requireContext(), "Profile Updated", Toast.LENGTH_SHORT).show()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

