package com.example.finle_project.Model

class UserChate {
    var name:String? = null
    var email:String? = null
    var uid:String? = null

    constructor() {

    }

    constructor(name:String?, email: String, uid:String?){
        this.name = name
        this.email= email.toString()
        this.uid = uid

    }
}