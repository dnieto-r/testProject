package com.example.fragmentstest.databases

import android.content.Context
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.Group
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.inject.Inject

class FileStorage @Inject constructor(
    private val applicationContext: Context
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

    override fun createGroup(group: Group) {}

    override fun getGroups(): List<Group> {
        return emptyList()
    }

    override fun removeGroup(group: Group) {}

    override fun addUserToGroup(userId: String, groupId: Int) {}

    override fun getGroup(userId: String): Group {
        return Group(0, "Sin Grupo")
    }

    override fun updateUserGroup(userId: String, groupId: Int) {}

    override fun updateGroup(group: Group) {}

}
