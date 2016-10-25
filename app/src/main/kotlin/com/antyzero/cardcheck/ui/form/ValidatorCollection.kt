package com.antyzero.cardcheck.ui.form

open class ValidatorCollection(val validators: MutableList<Validator> = mutableListOf()) : Validator {

    override fun isValid(): Boolean {
        return validators.fold(true) { accumulator, validator -> accumulator.and(validator.isValid()) }
    }
}

fun ValidatorCollection.with(vararg validators: Validator): ValidatorCollection {
    validators.forEach { this.validators.add(it) }
    return this
}