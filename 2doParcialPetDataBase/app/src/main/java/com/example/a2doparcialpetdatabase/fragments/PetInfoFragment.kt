package com.example.a2doparcialpetdatabase.fragments

import android.app.Activity
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

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.a2doparcialpetdatabase.R
import com.example.a2doparcialpetdatabase.entities.Pets
import com.example.a2doparcialpetdatabase.viewmodels.PetInfoViewModel
import com.google.android.material.snackbar.Snackbar

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import androidx.core.content.ContextCompat


class PetInfoFragment : Fragment() {

    companion object {
        fun newInstance() = PetInfoFragment()
    }

    private lateinit var viewModel: PetInfoViewModel

    lateinit var v : View
    lateinit var btnPetInfoDelete : Button
    lateinit var btnPetInfoEdit : Button
    lateinit var btnPetInfoBack : Button
    lateinit var edtPetInfoName : EditText
    lateinit var edtPetInfoBreed : EditText
    lateinit var edtPetInfoAge : EditText
    lateinit var edtPetInfoNotes : EditText
    lateinit var btnPetInfoImage : ImageButton
    lateinit var btnPetInfoNext : Button

    private val IMAGE_CAPTURE_REQUEST_CODE = 100

    val db = FirebaseFirestore.getInstance()
    private val args: PetInfoFragmentArgs by navArgs()

    private lateinit var imageBitmap: Bitmap
    private var storage = Firebase.storage
    var storageRef = storage.reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_pet_info, container, false)

        btnPetInfoDelete = v.findViewById(R.id.btnPetInfoDelete)
        btnPetInfoEdit = v.findViewById(R.id.btnPetInfoEdit)
        btnPetInfoBack = v.findViewById(R.id.btnPetInfoBack)
        edtPetInfoName = v.findViewById(R.id.edtPetInfoName)
        edtPetInfoBreed = v.findViewById(R.id.edtPetInfoBreed)
        edtPetInfoAge = v.findViewById(R.id.edtPetInfoAge)
        edtPetInfoNotes = v.findViewById(R.id.edtPetInfoNotes)
        btnPetInfoImage = v.findViewById(R.id.btnPetInfoImage)
        btnPetInfoNext = v.findViewById(R.id.btnPetInfoNext)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PetInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        try{
            val docref = db.collection("pets").document("2SrIpdxG29UDzKLUpSnj")
            Log.w("Test", "Reading success${docref}")
            db.collection("pets").document(args.uid)
                .get()
                .addOnSuccessListener { document ->
                    Log.w("Test", "Reading success")
                    if(document != null)
                    {
                        val actualpet = document.toObject(Pets::class.java)!!
                        edtPetInfoName.setText((actualpet.name))
                        edtPetInfoBreed.setText(actualpet.breed)
                        edtPetInfoNotes.setText((actualpet.notes))
                        edtPetInfoAge.setText(actualpet.age.toString())
                       // val imageref = storageRef.child("images/stones_background.jpg")
                        var image: ImageView = v.findViewById(R.id.btnPetInfoImage)
                       // imageref.downloadUrl.addOnSuccessListener {imageurl ->
                       //     Glide.with(v).load(imageurl).into(image)
                       // }
                        Glide.with(v).load(actualpet.imageUrl).into(image)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Test", "Error reading", e)
                }
        }catch (e: Exception)
        {
            Log.w("Test", "fail", e)
        }

        btnPetInfoNext.setOnClickListener{
            val action = PetInfoFragmentDirections.actionPetInfoFragmentToPetMoreInfoFragment(args.uid)
            findNavController().navigate(action)
        }

        btnPetInfoEdit.setOnClickListener{
            if(edtPetInfoName.length()>0 && edtPetInfoAge.length()>0 && edtPetInfoBreed.length()>0 && edtPetInfoNotes.length()>0){
                viewModel.editPets(edtPetInfoName.text.toString(),
                    edtPetInfoAge.text.toString(),
                    edtPetInfoBreed.text.toString(),
                    edtPetInfoNotes.text.toString(),
                    args.uid
                )
                viewModel.myPets.observe(viewLifecycleOwner){
                    myVariable->
                   // edtPetInfoName.setText(myVariable?.name)
                   // edtPetInfoAge.setText(myVariable?.age.toString())
                   // edtPetInfoBreed.setText(myVariable?.breed)
                   // edtPetInfoNotes.setText(myVariable?.notes)

                    Snackbar.make(v, "Pet Info Edited", Snackbar.LENGTH_SHORT).show()
                }
            }else{
         //nothing to change
            }
        }

        btnPetInfoDelete.setOnClickListener{
            viewModel.deletePets(args.uid)
            viewModel.status.observe(viewLifecycleOwner){
                state->
                if(state == true){
                    Snackbar.make(v, "Delete Successfull", Snackbar.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }else{
                    Snackbar.make(v, "Delete Failure", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        btnPetInfoImage.setOnClickListener{
        // Check if the app has camera permissions
            if (hasCameraPermission()) {
                // Open the camera
                startCamera()
            } else {
                // Request camera permissions
                requestCameraPermission()
            }
        }

        btnPetInfoBack.setOnClickListener{
            findNavController().navigateUp()
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

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
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
            btnPetInfoImage.setImageBitmap(imageBitmap)

            val storagePath = "pet_images" // Specify your desired storage path

            viewModel.uploadImage(
                imageBitmap,
                storagePath,
                onSuccess = { downloadUrl ->
                    // Handle the successful upload, downloadUrl contains the URL of the uploaded image
                    Log.d("FirebaseStorageHelper", "Image uploaded successfully. Download URL: $downloadUrl")
                    viewModel.editPetImage(args.uid, downloadUrl)
                    viewModel.myPets.observe(viewLifecycleOwner){
                        myVariable->
                        var image: ImageView = v.findViewById(R.id.btnPetInfoImage)
                        Glide.with(v).load(myVariable?.imageUrl).into(image)
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