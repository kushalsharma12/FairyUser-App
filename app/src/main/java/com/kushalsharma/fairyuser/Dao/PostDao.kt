package com.kushalsharma.fairyuserapp.Dao

import android.util.Log
import com.kushalsharma.fairyuserapp.Modals.User
import kotlinx.coroutines.GlobalScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kushalsharma.fairyuserapp.Modals.Post
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class PostDao {

    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("Posts")
    val auth = Firebase.auth

    fun addPost(
        title: String,
        description: String,
        quantity: String,
        image: String,
        price : String
    ) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
            Log.d("ka","${user.permanentAddress} + ${user.displayName} + lkdsjfldj")

            val post =
                Post(title, description, quantity, image, price,user, user.uid)
            postCollection.document().set(post)
        }

    }

//    fun getPostById(postId: String): Task<DocumentSnapshot> {
//        return postCollection.document(postId).get()
//    }

}
