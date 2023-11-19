package entities

import enums.Status


data class Philosopher(
    val id: Int,
    val nome: String,
    var status: Status
                ) : Thread()