package com.example.finle_project.View.home.VideoCall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import com.example.finle_project.Model.JavascriptInterface
import com.example.finle_project.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class CallActivity : AppCompatActivity() {

    var username = ""
    var friendsUsername =""
    var isPeerConnected = true

    var firebaseRef = Firebase.database.getReference("usersCall")
    var isAudio = true
    var isVideo = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        var callBtn = findViewById<Button>(R.id.callBtn)
        var toggleAudioBtn = findViewById<ImageView>(R.id.toggleAudioBtn)
        var toggleVideoBtn = findViewById<ImageView>(R.id.toggleVideoBtn)



        username = intent.getStringExtra("username")?:""

        callBtn.setOnClickListener {
            var frindNameEdit = findViewById<EditText>(R.id.frindNameEdit)
            friendsUsername = frindNameEdit.text.toString()
            sendCallRequeset()

        }

        toggleAudioBtn.setOnClickListener {
            isAudio = !isAudio
            callJavascriptFunction("javascript:toggleAudio(\"${isAudio}\")")
            toggleAudioBtn.setImageResource(if (isAudio) R.drawable.ic_baseline_mic_24 else R.drawable.ic_baseline_mic_off_24)

        }

        toggleVideoBtn.setOnClickListener {
            isVideo = !isVideo
            callJavascriptFunction("javascript:toggleVideo(\"${isVideo}\")")
            toggleVideoBtn.setImageResource(if (isVideo) R.drawable.ic_baseline_videocam_24 else R.drawable.ic_baseline_videocam_off_24)


        }

        setupWebView()




    }

    private fun sendCallRequeset() {
        if (!isPeerConnected){
            Toast.makeText(this, "you are not connected. Check your internet", Toast.LENGTH_LONG).show()
            return
        }
        firebaseRef.child(friendsUsername).child("incoming").setValue(username)
        firebaseRef.child(friendsUsername).child("isAvailable").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value.toString() =="ture"){
                   listenForconnId()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun listenForconnId() {
        firebaseRef.child(friendsUsername).child("connId").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null){
                    return
                  switchToControls()
                    callJavascriptFunction("javascript:startCal(\"${snapshot.value}\")")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        } )
    }

    private fun setupWebView() {
        var webView = findViewById<WebView>(R.id.webView)

        webView.webChromeClient = object : WebChromeClient(){
            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.grant(request.resources)

            }
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.addJavascriptInterface(JavascriptInterface(this),"Android")

        loadVideoCall()


    }

    private fun loadVideoCall() {
        val filePath = "file:android_asset/call.html"
        var webView = findViewById<WebView>(R.id.webView)

        webView.loadUrl(filePath)
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                initializePeer()
            }

        }

    }

    var uniqueId = ""

    private fun initializePeer() {
        uniqueId = getUniqueId()

        callJavascriptFunction("javascript:init(\"${uniqueId}\")")
        firebaseRef.child(username).child("incoming").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                onCallRequest(snapshot.value as? String)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        } )

    }

    private fun onCallRequest(caller: String?) {
        var callLayout = findViewById<RelativeLayout>(R.id.callLayout)
        var incomingCallText = findViewById<TextView>(R.id.incomingCallText)
        var acceptBtn = findViewById<ImageView>(R.id.acceptBtn)

        if (caller ==null) return
        callLayout.visibility = View.VISIBLE
        incomingCallText.text = "$caller is calling..."

        acceptBtn.setOnClickListener {
            firebaseRef.child(username).child("connId").setValue(uniqueId)
            firebaseRef.child(username).child("isAvailable").setValue(true)
            callLayout.visibility = View.GONE
            switchToControls()
        }
        var rejectBtn = findViewById<ImageView>(R.id.rejectBtn)

        rejectBtn.setOnClickListener {
            firebaseRef.child(username).child("incoming").setValue(null)
            callLayout.visibility = View.GONE

        }
    }

    private fun switchToControls() {
        var inputLayout = findViewById<RelativeLayout>(R.id.inputLayout)
        var callControlLayout = findViewById<LinearLayout>(R.id.callControlLayout)

        inputLayout.visibility = View.GONE
        callControlLayout.visibility = View.VISIBLE

    }

    @JvmName("getUniqueId1")
    private fun getUniqueId():String{
        return UUID.randomUUID().toString()
    }

    private fun callJavascriptFunction(functionString: String){
        var webView = findViewById<WebView>(R.id.webView)

        webView.post {

            webView.evaluateJavascript(functionString ,null)
        }
    }

    fun onPeerConnevted() {

        isPeerConnected = true
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        var webView = findViewById<WebView>(R.id.webView)
        firebaseRef.child(username).setValue(null)
        webView.loadUrl("about:blank")
        super.onDestroy()
    }
}