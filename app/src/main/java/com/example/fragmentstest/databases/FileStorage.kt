package com.example.fragmentstest.databases

import android.content.Context
import android.util.Log
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.EOFException
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

    override fun getRxUser(): Single<List<User>> {
        try {
            val fis = FileInputStream(file);
            val ois = ObjectInputStream(fis);

            var res = ois.readObject()
            var usersList = res as List<User>

            fis.close()
            ois.close()

            return Single.fromCallable{
                usersList
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        } catch (e: EOFException) {
            Log.d("ERROR", "Ha ocurrido un error al inicializar el objeto ObjectInputStream")
            return Single.never()
        }
        return Single.fromCallable{ emptyList() }
    }

    override fun editUser(user: User): Completable {
        getRxUser()
            .map { it ->
                val fos = FileOutputStream(file);
                val oos = ObjectOutputStream(fos);

                val tempUserList = it.toMutableList()
                val selectedUser = it.find { it.id == user.id }
                tempUserList.remove(selectedUser)
                tempUserList.add(user)
                tempUserList.sortBy { it.name }
                oos.writeObject(tempUserList);

                fos.close()
                oos.close()

                tempUserList
            }
        return Completable.complete()

    }

    override fun addUser(user: User): Completable {
        getRxUser()
            .map { it ->
                val fos = FileOutputStream(file);
                val oos = ObjectOutputStream(fos);
                val tempUserList = it.toMutableList()

                tempUserList.add(user)
                tempUserList.sortBy { it.name }
                oos.writeObject(tempUserList);
                fos.close()
                oos.close()
            }
        return Completable.complete()

    }

    override fun removeUser(user: User): Completable {
        getRxUser()
            .map { it ->
                val fos = FileOutputStream(file);
                val oos = ObjectOutputStream(fos);
                val tempUserList = it.toMutableList()

                tempUserList.remove(user)
                tempUserList.sortBy { it.name }
                oos.writeObject(tempUserList);

                fos.close()
                oos.close();
            }

        return Completable.complete()
    }

}
