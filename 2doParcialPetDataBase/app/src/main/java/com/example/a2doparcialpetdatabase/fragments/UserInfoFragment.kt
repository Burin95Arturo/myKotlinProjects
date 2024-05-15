package com.example.a2doparcialpetdatabase.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.a2doparcialpetdatabase.R
import com.example.a2doparcialpetdatabase.viewmodels.UserInfoViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.util.zip.ZipEntry


class UserInfoFragment : Fragment() {

    companion object {
        fun newInstance() = UserInfoFragment()
    }

    private lateinit var viewModel: UserInfoViewModel

    lateinit var v:View
    lateinit var btnUserInfoEdit : Button
    lateinit var btnUserInfoDelete : Button
    lateinit var btnUserInfoLogout : Button
    lateinit var btnUserInfoImage : ImageButton
    lateinit var edtUserInfoName : EditText
    lateinit var edtUserInfoEmail : EditText
    lateinit var edtUserInfoSurname : EditText
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    private var storage = Firebase.storage
    var storageRef = storage.reference
    private lateinit var imageBitmap: Bitmap

    private val IMAGE_CAPTURE_REQUEST_CODE = 100
    private lateinit var actualUserUid : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_info, container, false)

        // Initialize Firebase Auth
        auth = Firebase.auth

        btnUserInfoEdit = v.findViewById(R.id.btnUserInfoEdit)
        btnUserInfoDelete = v.findViewById(R.id.btnUserInfoDelete)
        btnUserInfoLogout = v.findViewById(R.id.btnUserInfoLogout)
        edtUserInfoName = v.findViewById(R.id.edtUserInfoName)
        edtUserInfoSurname = v.findViewById(R.id.edtUserInfoSurname)
        edtUserInfoEmail = v.findViewById(R.id.edtUserInfoEmail)
        btnUserInfoImage = v.findViewById(R.id.btnUserInfoImage)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // To record the uid for some uses
            actualUserUid = currentUser.uid

            viewModel.setUserInfo(currentUser)
            viewModel.myUser.observe(viewLifecycleOwner){
                myVariable->
                Log.d( "Test", "${myVariable?.email}")
                edtUserInfoEmail.setText(myVariable?.age.toString())
                edtUserInfoName.setText(myVariable?.name)
                edtUserInfoSurname.setText(myVariable?.surname) // change to surname
                var image: ImageView = v.findViewById(R.id.btnUserInfoImage)
                Glide.with(v).load(myVariable?.image).into(image)
            }
        } else {
            // No user is signed in
            Log.d("Test", "Not Loaded")
        }

        btnUserInfoImage.setOnClickListener{
            // Check if the app has camera permissions
            if (hasCameraPermission()) {
                // Open the camera
                startCamera()
            } else {
                // Request camera permissions
                requestCameraPermission()
            }
        }

        btnUserInfoDelete.setOnClickListener(){
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Delete User")
                builder.setMessage("¿Really want to delete?")

                builder.setPositiveButton("Delete") { dialog, which ->
                // My code
                    viewModel.deleteUser(currentUser)
                    viewModel.status.observe(viewLifecycleOwner){
                        state->
                        if (state == true){
                            Snackbar.make(v, "User Deleted Succesfully", Snackbar.LENGTH_SHORT).show()
                            auth.signOut()
                            var action = UserInfoFragmentDirections.actionUserInfoFragmentToLoginActivity()
                            v.findNavController().navigate(action)
                        }else{
                            Snackbar.make(v, "User Deleted Failed", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
                builder.setNeutralButton( "Idle"){
                    dialog,wich->
                    dialog.dismiss() // close popup
                }
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss() // se cierra la PopUp
                }
                val dialog = builder.create()
                dialog.show()
        }

        btnUserInfoLogout.setOnClickListener{
            auth.signOut()
            var action = UserInfoFragmentDirections.actionUserInfoFragmentToLoginActivity()
            v.findNavController().navigate(action)
        }

        btnUserInfoEdit.setOnClickListener{
            if( edtUserInfoEmail.length()>0 && edtUserInfoSurname.length()>0 && edtUserInfoName.length()>0 ){
                viewModel.changeUserInfo(edtUserInfoName.text.toString(),
                    edtUserInfoEmail.text.toString(),
                    edtUserInfoSurname.text.toString(),
                    currentUser)
                viewModel.myUser.observe(viewLifecycleOwner){
                        myVariable->
                    Log.d( "Test", "${myVariable?.email}")
                    edtUserInfoEmail.setText(myVariable?.age.toString())
                    edtUserInfoName.setText(myVariable?.name)
                    edtUserInfoSurname.setText(myVariable?.surname) // change to surname
                    Snackbar.make(v, "Success Edit", Snackbar.LENGTH_SHORT).show()
                }
            }else{
                Snackbar.make(v, "Nothing to add", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        // Implement CameraX setup here
        // This could include setting up a PreviewView, configuring use cases, etc.
        // Refer to the CameraX documentation for more details.
        Snackbar.make(v, "Camera Opened!!!", Snackbar.LENGTH_SHORT).show()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IMAGE_CAPTURE_REQUEST_CODE)
        //pickImageFromGallery()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, open the camera
                startCamera()
            } else {
                // Handle permission denial
            }
        }

    private fun requestCameraPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if the result is for the image capture request
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The image capture was successful
            // Get the captured image from the data intent
            imageBitmap = data?.extras?.get("data") as Bitmap
            // Now you can use the captured image (e.g., set it to an ImageView)
            btnUserInfoImage.setImageBitmap(imageBitmap)

            val storagePath = "user_images" // Specify your desired storage path

            viewModel.uploadImage(
                imageBitmap,
                storagePath,
                onSuccess = { downloadUrl ->
                    // Handle the successful upload, downloadUrl contains the URL of the uploaded image
                    Log.d("FirebaseStorageHelper", "Image uploaded successfully. Download URL: $downloadUrl")
                    viewModel.editUserImage(actualUserUid, downloadUrl)
                    viewModel.myUser.observe(viewLifecycleOwner){
                            myVariable->
                        var image: ImageView = v.findViewById(R.id.btnUserInfoImage)
                        Glide.with(v).load(myVariable?.image).into(image)
                    }
                },
                onFailure = {
                    // Handle the upload failure
                    Log.e("FirebaseStorageHelper", "Image upload failed.")
                }
            )
        } else {
            // Handle other results or errors here
        }
    }
}