package com.example.fragmentstest.databases

import com.example.fragmentstest.R
import com.example.fragmentstest.models.User

object DataMemoryAbstraction {
    var usersReference: MutableList<User> = emptyList<User>().toMutableList()

    init {
        initialize()
    }

    private fun initialize() {
        usersReference.add(
            User(
                "1", "Almudena", "652352510",
                "Calle de Prueba 1", R.drawable.b, false
            )
        )
        usersReference.add(
            User(
                "2", "Daniel", "625793410",
                "Calle de Prueba 2", R.drawable.a, false
            )
        )
        usersReference.add(
            User(
                "3", "Javier", "699243294",
                "Calle de Prueba 3", R.drawable.c, true
            )
        )
        usersReference.add(
            User(
                "4", "Lucas", "699243294",
                "Calle de Prueba 4", R.drawable.c, false
            )
        )
        usersReference.add(
            User(
                "5", "María", "652352510",
                "Calle de Prueba 5", R.drawable.b, false
            )
        )
        usersReference.add(
            User(
                "6", "Pepe", "654634534",
                "Calle de Prueba 6", R.drawable.a, false
            )
        )
    }
}
