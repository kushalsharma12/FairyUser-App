package com.kushalsharma.fairyuserapp.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.fairyuserapp.Modals.Post
import com.kushalsharma.fairyuserapp.R
import com.kushalsharma.fairyuserapp.databinding.FragmentProductlistBinding

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductlistBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: AdapterProductList
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductlistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = Firebase.auth
        val currentUser = auth.currentUser

        binding.addBtnProductList.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_navigation_productlist_to_addProductFragment)
        }

        val query = db.collection("Posts")
//            .whereEqualTo("userId", currentUser!!.uid)
            .orderBy("title").limit(100)

        val options = FirestoreRecyclerOptions.Builder<Post>()
            .setQuery(query, Post::class.java)
//            .setLifecycleOwner(this)
            .build()

        adapter = AdapterProductList(options)
        adapter.notifyDataSetChanged()
        binding.recyclerViewProductList.adapter = adapter



        return root
    }

    override fun onStart() {
        super.onStart()

        adapter.startListening()

    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}