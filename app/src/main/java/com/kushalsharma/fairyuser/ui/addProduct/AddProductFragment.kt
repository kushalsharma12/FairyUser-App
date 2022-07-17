package com.kushalsharma.fairyuserapp.ui.addProduct

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kushalsharma.fairyuserapp.BuildConfig
import com.kushalsharma.fairyuserapp.Dao.PostDao
import com.kushalsharma.fairyuserapp.R
import com.kushalsharma.fairyuserapp.databinding.FragmentAddProductBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.*


class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var postDao: PostDao
    private val PICK_IMAGE_REQUEST = 71
    private var selectedImageUri: Uri? = null
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    private var imgLink : String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;


        binding.backBtnAddProduct.setOnClickListener {
            backtoProductListFragment(it)
        }
        binding.cancelBtnAddProduct.setOnClickListener {
            backtoProductListFragment(it)
        }
        binding.imgAddProductFrag.setOnClickListener {
            chooseImgFromPhnStorage()
        }

        postDao = PostDao()

        binding.addBtnAddProductFrag.setOnClickListener {
            uploadDataToFirebase()
//            uploadImg()
            backtoProductListFragment(it)
        }


        return root

    }


    private fun chooseImgFromPhnStorage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                selectedImageUri = data?.data
                val inputStream: InputStream? = null
                try {
                    if (BuildConfig.DEBUG && selectedImageUri == null) {
                        error("Assertion failed")
                        //
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                BitmapFactory.decodeStream(inputStream)
//                binding.imgAddProductFrag.setImageURI(selectedImageUri)
                Glide.with(binding.imgAddProductFrag.context).load(selectedImageUri)
                    .transform(CenterCrop(), RoundedCorners(32))
                    .into(binding.imgAddProductFrag)
            }
        }
    }
    private fun backtoProductListFragment(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_addProductFragment_to_navigation_productlist)
    }

//    private fun uploadImg() {
//
//        //uploading image to firestorage
//
//        storageReference = storageReference!!.child(
//            "images/"
//                    + UUID.randomUUID().toString()
//        )
//
//        storageReference!!.putFile(selectedImageUri!!).addOnSuccessListener{
//
//            val result  = it.metadata!!.reference!!.downloadUrl
//
//            result.addOnSuccessListener {
//               val imgUrl = it.toString()
//                Log.d("img1",imgUrl.toString())
//
//
//            }
//        }
//            .addOnFailureListener{
//                Toast.makeText(this.requireContext(), "Image not fetched", Toast.LENGTH_SHORT).show()
//            }
//
//    }


    private fun uploadDataToFirebase() {
        val titleInput = binding.productNameEt.text.toString().trim()
        val descripInput = binding.decpEt.text.toString().trim()
        val quantityInput = binding.quantityEt.text.toString().trim()
        val priceInput = binding.priceEt.text.toString().trim()


// Uploading data to firebase firestore

        if (titleInput.isNotEmpty() && descripInput.isNotEmpty()
            && quantityInput.isNotEmpty() && quantityInput != "0"
        ) {
            postDao.addPost(titleInput, descripInput, quantityInput, "imgUrl", priceInput)

        } else {
            Toast.makeText(this.context, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}




