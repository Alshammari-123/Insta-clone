package com.example.finle_project.Model

import android.webkit.JavascriptInterface
import com.example.finle_project.View.home.VideoCall.CallActivity

class JavascriptInterface(val callActivity: CallActivity) {

@JavascriptInterface
    public fun onPeerConnevted(){
        callActivity.onPeerConnevted()
    }
}