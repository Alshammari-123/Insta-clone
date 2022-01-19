package com.example.finle_project.model

class Message {

    var uid: String? = null
    var message : String? = null
    var senderId :String? = null

    constructor(){}

    constructor(message:String? , senderId : String? , uid:String?){

        this.message = message
        this.senderId = senderId
        this.uid = uid


    }
}