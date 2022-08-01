package com.example.fragmentstest.databases

import android.content.Context
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileStorage(
    applicationContext: Context
) : Storage {

    private val folder: File by lazy {
        File(applicationContext.getExternalFilesDir(null), "usersData")
    }
    private val file: File by lazy {
        File(folder, "usersList.txt")
    }

    override fun getUsers(): MutableList<User> {
        var usersList: MutableList<User> = emptyList<User>().toMutableList()

        if (folder.exists() && file.exists()) {
            val fis = FileInputStream(file);
            val ois = ObjectInputStream(fis);
            var res = ois.readObject()
            usersList = (res as MutableList<User>)
            fis.close()
            ois.close()
        }

        return usersList
    }

    override fun editUser(user: User) {
        var users = this.getUsers()
        val fos = FileOutputStream(file);
        val oos = ObjectOutputStream(fos);

        val selectedUser = users.find { it.id == user.id }
        users.remove(selectedUser)
        users.add(user)
        users.sortBy { it.name }
        oos.writeObject(users);

        fos.close()
        oos.close();
    }

    override fun addUser(user: User) {
        var users = this.getUsers()
        val fos = FileOutputStream(file);
        val oos = ObjectOutputStream(fos);

        users.add(user)
        users.sortBy { it.name }
        oos.writeObject(users);

        fos.close()
        oos.close();
    }

    override fun removeUser(user: User) {
        var users = this.getUsers()
        val fos = FileOutputStream(file);
        val oos = ObjectOutputStream(fos);

        users.remove(user)
        users.sortBy { it.name }
        oos.writeObject(users);

        fos.close()
        oos.close();
    }

}
