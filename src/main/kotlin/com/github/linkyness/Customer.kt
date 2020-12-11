package com.github.linkyness

class Customer(val id: String?, val name: String?, val address: String?, val age: Int) {

    override fun toString(): String {
        return "Customer [id=$id, name=$name, address=$address, age=$age]"
    }
}