package services

import application.Program
import entities.Philosopher
import enums.Status
import repositories.Actions
import java.util.concurrent.Semaphore

class PhilosopherActions (private val philosopher: Philosopher) : Actions {
    private val semaphore = Semaphore(0)

    override fun run() {
        try {
            while (true) {
                print()
                when (philosopher.status) {
                    Status.THINKING -> {
                        thinking()
                        Program.mutex
                        semaphore.acquire()
                        Status.HUNGRY
                    }
                    Status.HUNGRY -> {
                        hungry()
                        Program.mutex
                        semaphore.release()
                        semaphore.acquire()
                        Status.EATING
                    }
                    Status.EATING -> {
                        eating()
                        Program.mutex
                        semaphore.acquire()
                        Status.THINKING

                        test(philosopher.left())
                        test(philosopher.right())

                        semaphore.release()
                    }
                }
            }
        }
        catch (e : InterruptedException) {
        }
    }

    fun Philosopher.left(): Philosopher {
        val index = if (id == 0) Program.quantity - 1 else id - 1
        return Program.philosophers[index]
    }

    fun Philosopher.right(): Philosopher {
        val index = (id + 1) % Program.quantity
        return Program.philosophers[index]

    }
    fun test(philo : Philosopher) {
        if (philo.left().status != Status.EATING
            && philo.status == Status.HUNGRY
            && philo.left().status != Status.EATING){
                philo.status = Status.EATING
                semaphore.release()
        }
    }
    override fun print() {
        println("${philosopher.nome} is ${Status.description}")
    }

    override fun thinking(){
        try{
            Thread.sleep((Math.random() * 1000).toLong())
        }
        catch(e : InterruptedException){}
    }
    override fun hungry(){
        try{
            Thread.sleep((Math.random() * 1000).toLong())
        }
        catch(e : InterruptedException){}
    }
    override fun eating(){
        try{
            Thread.sleep((Math.random() * 1000).toLong())
        }
        catch(e : InterruptedException){}
    }
}
