package org.wit.hillfort.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun findOneByEmail(email: String): UserModel?
    fun create(user: UserModel)
}