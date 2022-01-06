package com.example.finle_project.View.home.Vido

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.finle_project.Model.CasheApp.Companion.simpleCache
import com.example.finle_project.R
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory
import com.google.android.exoplayer2.upstream.cache.CacheUtil
import com.google.android.exoplayer2.util.Util.getUserAgent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.iceteck.silicompressorr.SiliCompressor
import com.iceteck.silicompressorr.Util
import com.iceteck.silicompressorr.videocompression.MediaController.mContext

class AddVideosActivity : AppCompatActivity() {

    private lateinit var actionBar: ActionBar
    private val VIDEO_PICK_GALLERY_CODE = 100
    private val VIDEO_PICK_CAMERA_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    private lateinit var cameraPermission: Array<String>
    private var videoUri: Uri? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_videos)
        var uplodVideo = findViewById<Button>(R.id.uplodVideo)
        var videoUri = findViewById<VideoView>(R.id.videoView)
        var titleEt = findViewById<EditText>(R.id.title)
        var pickVideoFab = findViewById<FloatingActionButton>(R.id.pickVideoFab)







        // init actionBar
//        actionBar = supportActionBar!!
//        actionBar.title = "Add New Video"
//        actionBar.setDisplayHomeAsUpEnabled(true)
//        actionBar.setDisplayShowHomeEnabled(true)

        //cashe
//        val videouri = Uri.parse(videoUri.toString())
//        val dataSpec = DataSpec(videouri)
//        val defaultCacheKeyFactory = CacheUtil.DEFAULT_CACHE_KEY_FACTORY
//        val progressListener =
//            CacheUtil.ProgressListener { requestLength, bytesCached, newBytesCached ->
//                val downloadPercentage: Double = (bytesCached * 100.0
//                        / requestLength)
//            }
//        val dataSource: DataSource =
//            DefaultDataSourceFactory(
//                mContext,
//               UInt.getUserAgent(this, getString(R.string.app_name))).createDataSource()



        cameraPermission = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        // init progressbar
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Uploading Video...")
        progressDialog.setCanceledOnTouchOutside(false)

        // handel click, upload video
        uplodVideo.setOnClickListener {
            // get title
            title = titleEt.text.toString().trim()
            if (TextUtils.isEmpty(title)) {
                // no title is entered
                Toast.makeText(this, "Title is requirad", Toast.LENGTH_SHORT).show()
            } else if (videoUri == null) {
                // video is not picked
                Toast.makeText(this, "Pick the video first", Toast.LENGTH_SHORT).show()
            } else {
                // title entered , video picked , so now upload
                uploadVideoFirebase()

            }
        }

        // handel click , pick video
        pickVideoFab.setOnClickListener {
            videoPickDialog()
        }

    }

    /**
     * Used to starting caching a video
     */
    private fun cacheVideo(
        dataSpec: DataSpec,
        defaultCacheKeyFactory: CacheKeyFactory?,
        dataSource: DataSource,
        progressListener: CacheUtil.ProgressListener
    ) {
        CacheUtil.cache(
            dataSpec,
            simpleCache,
            defaultCacheKeyFactory,
            dataSource,
            progressListener,
            null
        )
    }
    private fun uploadVideoFirebase() {
        // show progress
        progressDialog.show()
        //timestamp
        val timestamp = "" + System.currentTimeMillis()
        // file path and name in firebase
        val filePathAndName = "Videos/video_$timestamp"

        //storge reference
        val storageRefrence = FirebaseStorage.getInstance().getReference(filePathAndName)
        // upload video useing uri of storage
        storageRefrence.putFile(videoUri!!)
            .addOnProgressListener { taskSnapshot ->
                val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                progressDialog.setMessage("Uploading Video ($progress)")
                Toast.makeText(this, "Uploaded $progress%", Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener { taskSnapshot ->

                // upload , get url of upload video
                val uriTask = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val downladUri = uriTask.result
                if (uriTask.isSuccessful) {
                    // video url is received successfully

                    //now we cann add video details to firebase
                    val hashMap = HashMap<String, Any>()
                    hashMap["id"] = "$timestamp"
                    hashMap["title"] = "$title"
                    hashMap["timestamp"] = "$timestamp"
                    hashMap["videoUri"] = "$downladUri"

                    // put the above info to do
                    val dbReference = FirebaseDatabase.getInstance().getReference("Video")
                    dbReference.child(timestamp)
                        .setValue(hashMap)
                        .addOnSuccessListener { taskSnapshot ->
                            // video info added successFully
                            progressDialog.dismiss()
                            Toast.makeText(this, "Video Upladed", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            //failed adding video inf 
                            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()

                        }

                }

            }
            .addOnFailureListener { e ->
                //failed uploading
                progressDialog.dismiss()
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // set the picked video to video view
    private fun setVideoToVideoView() {
// video play control
        val mediaController = MediaController(this)
        val videoView = findViewById<VideoView>(R.id.videoView)
        mediaController.setAnchorView(videoView)

        // set media contrllopler
        videoView.setMediaController(mediaController)
        // set video uri
        videoView.setVideoURI(videoUri)
        videoView.requestFocus()
        videoView.setOnPreparedListener {
            // when video is ready, by default don't play outomatically
            videoView.pause()


        }


    }

    private fun videoPickDialog() {
        // options to display in dialog
        val options = arrayOf("Camera", "Gallery")
        //aler dialog
        val builder = AlertDialog.Builder(this)
        //title
        builder.setTitle("Pick Video From")
            .setItems(options) { dialogInterface, i ->
                // handel itme clicks
                if (i == 0) {
                    // camera clicked
                    if (!checkCameraPermissions()) {
                        // permissions was not allowed
                        requestCameraPermissions()
                    } else {
                        videoPickCamera()
                    }

                } else {
                    //gallery clicked
                    videoPickGallery()
                }
            }
            .show()
    }

    private fun requestCameraPermissions() {
        // requset camera permission
        ActivityCompat.requestPermissions(
            this,
            cameraPermission,
            CAMERA_REQUEST_CODE
        )
    }

    private fun checkCameraPermissions(): Boolean {
        // check if camera permissions i.e. camera and storage is allowed or not
        val result1 = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val result2 = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        return result1 && result2
    }

    private fun videoPickGallery() {
        // video pick intent gallery
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Choose video"),
            VIDEO_PICK_CAMERA_CODE
        )
    }

    private fun videoPickCamera() {
        //video pick intent camera
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, VIDEO_PICK_CAMERA_CODE)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // handel permission results*/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE ->
                if (grantResults.size > 0) {
                    //check if permission allowed or denied
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted && storageAccepted) {
                        //both permission allowed
                        videoPickCamera()
                    } else {
                        //both or one of those are denied
                        Toast.makeText(this, "Parmission donied", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // handle video pick result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == RESULT_OK) {
            //video is pick from camera or gallery
            if (requestCode == VIDEO_PICK_CAMERA_CODE) {
                videoUri = data!!.data
                setVideoToVideoView()
            } else if (requestCode == VIDEO_PICK_GALLERY_CODE) {
                videoUri = data!!.data
                setVideoToVideoView()
            }
        } else {
            // canclled picking
            Toast.makeText(this, " Canclled", Toast.LENGTH_SHORT).show()


        }
        super.onActivityResult(requestCode, resultCode, data)


    }

}