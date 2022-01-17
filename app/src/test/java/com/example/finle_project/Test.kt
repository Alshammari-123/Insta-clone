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
    fun emailIsValidWithInvalidEmailThenReturnFalse(){
        val validation = validator.emailIsValid("ali-test`S.mu.edu.sa")
        Assert.assertEquals(false,validation)
    }

    @Test
    fun emailIsValidWithInvalidEmailThenReturnFalseV(){
        val validation = validator.emailIsValid("turki$%gmail.com")
        Assert.assertEquals(false,validation)
    }


    @Test
    fun emailIsValidWithValidEmailThenReturnTrueValue(){
        val validation = validator.emailIsValid("turki@test.com")
        Assert.assertEquals(true,validation)
    }
    @Test
    fun emailIsValidWithValidEmailThenReturnTrue(){
        val validation = validator.emailIsValid("turki@gmail.com")
        Assert.assertEquals(true,validation)
    }
    @Test
    fun emailIsValidWithValidEmailThenReturnTrueV(){
        val validation = validator.emailIsValid("turki@hotmail.com")
        Assert.assertEquals(true,validation)
    }

    @Test
    fun passwordIsValidWithInvalidPasswordThenReturnFalseValue(){
        val validation = validator.passwordIsValid("turk234555")
        Assert.assertEquals(false,validation)
    }
    @Test
    fun passwordIsValidWithInvalidPasswordThenReturnFalse(){
        val validation = validator.passwordIsValid("turk$234555")
        Assert.assertEquals(false,validation)
    }
    @Test
    fun passwordIsValidWithInvalidPasswordThenReturnFalseV(){
        val validation = validator.passwordIsValid("turk//*#234555")
        Assert.assertEquals(false,validation)
    }

    @Test
    fun passwordIsValidWithValidPasswordThenReturnTrueValue(){
        val validation = validator.passwordIsValid("Turk234555")
        Assert.assertEquals(true,validation)
    }
    @Test
    fun passwordIsValidWithValidPasswordThenReturnTrue(){
        val validation = validator.passwordIsValid("TurkAlshammari-123")
        Assert.assertEquals(true,validation)
    }
    @Test
    fun passwordIsValidWithValidPasswordThenReturnTrueV(){
        val validation = validator.passwordIsValid("Turk123TA")
        Assert.assertEquals(true,validation)
    }

}