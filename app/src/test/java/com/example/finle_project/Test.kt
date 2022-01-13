package com.example.finle_project

import org.junit.Assert
import org.junit.Before
import org.junit.Test


class Test {
    private lateinit var validator: com.example.finle_project.util.Validation

    @Before
    fun setup(){
        validator = com.example.finle_project.util.Validation()
    }
    @Test
    fun emailIsValidWithInvalidEmailThenReturnFalseValue(){
        val validation = validator.emailIsValid("turki-test.com")
        Assert.assertEquals(false,validation)
    }

    @Test
    fun emailIsValidWithValidEmailThenReturnTrueValue(){
        val validation = validator.emailIsValid("turki@test.com")
        Assert.assertEquals(true,validation)
    }
    @Test
    fun passwordIsValidWithInvalidPasswordThenReturnFalseValue(){
        val validation = validator.passwordIsValid("turk234555")
        Assert.assertEquals(false,validation)
    }
    @Test
    fun passwordIsValidWithValidPasswordThenReturnTrueValue(){
        val validation = validator.passwordIsValid("Turk234555")
        Assert.assertEquals(true,validation)
    }

}