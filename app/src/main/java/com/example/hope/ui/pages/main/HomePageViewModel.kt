package com.example.hope.ui.pages.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hope.ui.composables.post.PostData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomePageViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val userID = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private val _postList = MutableStateFlow<List<PostData>>(emptyList())
    val postList: StateFlow<List<PostData>> = _postList
    private var postsListener: ValueEventListener? = null

    init {
        fetchPostsRealTime()
        fetchSavedPostRealTime()
    }

    fun fetchPostsRealTime() {
        val postsRef = database.getReference("Posts")
        postsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = snapshot.children.mapNotNull { postSnapshot ->
                    val post = postSnapshot.getValue(PostData::class.java)
                    post?.let {
                        // Fetch the bookmark status for the user
                        val bookmarksRef = database.getReference("Bookmarks/$userID/${it.postID}")
                        bookmarksRef.get().addOnSuccessListener { bookmarkSnapshot ->
                            val isBookmarked = bookmarkSnapshot.getValue(Boolean::class.java) ?: false
                            // Convert PostData1 to PostData with the correct bookmark status
                            val postData = PostData(
                                postID = it.postID,
                                userID = it.userID,
                                username = it.username,
                                profilePicture = it.profilePicture,
                                postImg = it.postImg,
                                title = it.title,
                                location = it.location,
                                description = it.description,
                                isBookmarked = isBookmarked
                            )
                            _postList.value = _postList.value + postData // Add the post to the list
                        }.addOnFailureListener {
                            Log.e("HomePageViewModel", "Failed to fetch bookmark status: ${it.message}")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomePageViewModel", "Error fetching posts: ${error.message}")
            }
        }

        postsListener?.let { listener ->
            postsRef.addValueEventListener(listener)
        }
    }

    private val _savedPostList = MutableStateFlow<List<PostData>>(emptyList())
    val savedPostList: StateFlow<List<PostData>> = _savedPostList

    fun fetchSavedPostRealTime() {
        val bookmarksRef = database.getReference("Bookmarks/$userID")

        bookmarksRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                // List of post IDs from the user's bookmarks
                val savedPostIDs = snapshot.children.mapNotNull { it.key }

                if (savedPostIDs.isNotEmpty()) {
                    val postsRef = database.getReference("Posts")
                    val savedPosts = mutableListOf<PostData>()
                    var processedPosts = 0 // Counter to track the number of processed posts

                    // Fetch each post using the post IDs
                    savedPostIDs.forEach { postID ->
                        postsRef.child(postID).get().addOnSuccessListener { postSnapshot ->
                            val post = postSnapshot.getValue(PostData::class.java)
                            post?.let {
                                // Optionally, fetch the bookmark status for the user
                                val bookmarkStatusRef = database.getReference("Bookmarks/$userID/$postID")
                                bookmarkStatusRef.get().addOnSuccessListener { bookmarkSnapshot ->
                                    val isBookmarked = bookmarkSnapshot.getValue(Boolean::class.java) ?: false
                                    val postData = PostData(
                                        postID = it.postID,
                                        userID = it.userID,
                                        username = it.username,
                                        profilePicture = it.profilePicture,
                                        postImg = it.postImg,
                                        title = it.title,
                                        location = it.location,
                                        description = it.description,
                                        isBookmarked = isBookmarked
                                    )
                                    savedPosts.add(postData)
                                }.addOnFailureListener {
                                    Log.e("HomePageViewModel", "Failed to fetch bookmark status: ${it.message}")
                                }
                            }
                            // Increment the processedPosts counter
                            processedPosts++
                            // Check if all posts have been processed
                            if (processedPosts == savedPostIDs.size) {
                                _savedPostList.value = savedPosts
                            }
                        }.addOnFailureListener {
                            // Increment the processedPosts counter even if post is not found
                            processedPosts++
                            // Check if all posts have been processed
                            if (processedPosts == savedPostIDs.size) {
                                _savedPostList.value = savedPosts
                            }
                            Log.e("HomePageViewModel", "Failed to fetch post: ${it.message}")
                        }
                    }
                } else {
                    // Handle the case when there are no saved posts
                    _savedPostList.value = emptyList()
                }
            } else {
                // Handle case when the user has no bookmarks at all
                _savedPostList.value = emptyList()
            }
        }.addOnFailureListener { exception ->
            Log.e("HomePageViewModel", "Failed to fetch saved posts: ${exception.message}")
            _savedPostList.value = emptyList() // Return empty list in case of failure
        }
    }


}

