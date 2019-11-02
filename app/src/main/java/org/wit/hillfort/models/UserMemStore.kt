package org.wit.hillfort.models

import org.jetbrains.anko.AnkoLogger


class UserMemStore : UserStore, AnkoLogger {

    val users = mutableListOf<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun findOneByEmail(email: String): UserModel? {
        return users.find { it.email == email }
    }
    override fun create(user: UserModel) {
        users.add(user)
    }

}