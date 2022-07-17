package com.kushalsharma.fairyuserapp.Modals

data class Post (
    val title: String = "",
    val description : String = "",
    val quantity : String = "",
    val productImageUrl : String ="",
    val price : String = "",
    val createdBy: User = User(),
    val userId: String = User().uid

    )
