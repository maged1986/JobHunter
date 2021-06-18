package com.magednan.jobhunter.firebase

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.magednan.jobhunter.models.Job
import com.magednan.jobhunter.models.UrgentJob
import com.magednan.jobhunter.models.User
import com.magednan.jobhunter.ui.activities.MainActivity
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject


class Firebase @Inject constructor() : AppCompatActivity() {
    private var mUser: User? = null
    val auth = FirebaseAuth.getInstance()
    var user = auth.currentUser

    var imageUrl: String? = null

    // to cereat new usesr and save user data
    fun createNewUser(
        name: String?, job: String?,
        email: String, password: String,
        context: Context
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult?> {
                override fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful()) {
                        //send email verificaiton
                        sendVerificationEmail(context)
                        updateUserData(name, job, email)
                        //add user details to firebase database
                        Toast.makeText(
                            context, "Successful ",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(context,MainActivity::class.java))
                    }
                    if (!task.isSuccessful()) {
                        Toast.makeText(
                            context, "Someone with that email ",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            })
    }

    fun updateUserData(
        name: String?,
        job: String?,
        email: String?
    ) {
        //add data to the "users" node
        mUser = User(name, job, email)
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        //insert into users node
        reference.child("users").push()
            .setValue(mUser)
    }

    fun sendVerificationEmail(mContext: Context?) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.addOnCompleteListener(object : OnCompleteListener<Void?> {
            override fun onComplete(task: Task<Void?>) {
                if (task.isSuccessful()) {
                } else {
                    Toast.makeText(
                        mContext,
                        "couldn't send a verification email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    // to make user login
    fun logIn(email: String, password: String, context: Context) {
        val mAuth: FirebaseAuth
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(object : OnCompleteListener<AuthResult?> {
                override fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "createUserWithEmail:success")
                        Toast.makeText(
                            context, "Authentication successe.",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(context,MainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(
                            "TAG", "createUserWithEmail:failure",
                            task.getException()
                        )
                        Toast.makeText(
                            context, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    // to make user logout
    fun signOut() {
        val mAuth: FirebaseAuth
        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
    }

    // to saveJob
    fun uploadJob(job: Job, context: Context?) {
        val mAuth: FirebaseAuth
        mAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser!!.uid
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection(userId).add(job)
            .addOnSuccessListener(object : OnSuccessListener<DocumentReference?> {
                override fun onSuccess(documentReference: DocumentReference?) {
                    Toast.makeText(context, "Data Update", Toast.LENGTH_LONG).show()
                }
            }).addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            })
    }

    // to saveJob
    fun createAJob(urgentJob: UrgentJob, context: Context?) {
        val mAuth: FirebaseAuth
        mAuth = FirebaseAuth.getInstance()
        val userId = mAuth.currentUser!!.uid
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("urgentJob").add(urgentJob)
            .addOnSuccessListener(object : OnSuccessListener<DocumentReference?> {
                override fun onSuccess(documentReference: DocumentReference?) {
                    Toast.makeText(context, "Profile Updated", Toast.LENGTH_LONG).show()
                }
            }).addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            })
    }

    fun uploadPhoto(uri: Uri, context: Context?): String? {
        val date = Calendar.getInstance().time
        val storageReference: StorageReference = FirebaseStorage.getInstance().getReference()
            .child("posts/users//$date")
        storageReference.putFile(uri).addOnSuccessListener(OnSuccessListener<Any?> {
            storageReference.getDownloadUrl().addOnSuccessListener(OnSuccessListener<Any> { uri ->
                imageUrl = uri.toString()
                if (imageUrl != null) {
                    Toast.makeText(context, imageUrl, Toast.LENGTH_SHORT).show()
                }
                Log.d("TAG", "onFailure: $imageUrl")
            }).addOnFailureListener(OnFailureListener { e ->
                Log.d(
                    "TAG",
                    "onFailure: " + e.message
                )
            })
        })
        return imageUrl
    }


    suspend fun getSavedJobs(): List<Job> {
        var userId = user!!.uid
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val jobsRef: Query = firebaseFirestore.collection(userId)
        return try {
            jobsRef.get().await().toObjects(Job::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getUrgentJobs(): List<UrgentJob> {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val urgentJobRef: Query = firebaseFirestore.collection("urgentJob")
        return try {
            urgentJobRef.get().await().toObjects(UrgentJob::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }


}