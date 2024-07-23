package com.runique.auth.domain

interface PatternValidator {
    fun matches(value : String): Boolean
}