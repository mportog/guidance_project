package com.mportog.guidanceprojecttest.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.system.*

fun main() {
    scopeAndContextExercises()
    //measureWorkInSingleThread()
    //measureWorkInMultiThread()
    //measureWorkInGlobalScopeWithoutSupervision()
    //measureWorkInGlobalScopeWithSupervision()
    //measureWorkInGlobalScopeWithoutSupervision()
    //measureWorkInAnotherThread()
}

fun scopeAndContextExercises() = runBlocking<Unit> {
    // A property "coroutineContext" de uma coroutine é na verdade uma coleção de itens
    // que implementam a interface CoroutineContext:
    println("My context is:\n$coroutineContext\n")

    // O Job, que é normalmente retornado pelo builder da coroutine é um desses itens:
    println("My job is:\n${coroutineContext[Job]}\n")

    // Essa coleção de CoroutineContexts que forma o "contexto" da coroutine é imutavel,
    // mas você pode usar o operador + para criar uma nóva coleção que resulta na soma das duas:
    println("A context with name:\n${coroutineContext + CoroutineName("test")}\n")

    // Ao lançar uma coroutine filha, o contexto da coroutine pai se soma ao contexto
    // passado na função de build, além de ser atribuida a um novo Job:
    launch(CoroutineName("child")) {
        println("I am a new coroutine and my context is:\n$coroutineContext}\n")
    }

    // A função passada como ultimo parâmetro para o builder tem CoroutineScope como
    // receiver, e o contexto desse scope é o mesmo que o contexto da coroutine lançada
    launch { scopeCheck(this) }

    // Se precisar lançar uma coroutine filha que continue a rodar mesmo depois de
    // o método que a lança terminar, passe uma instância do CoroutineScope e lance
    // a partir dela! Essas funções nem precisam ser suspending ;)
    doThis()
    doThatIn(this)
}

suspend fun scopeCheck(scope: CoroutineScope) {
    val check = scope.coroutineContext === coroutineContext
    println("Are the scopes the same? $check\n")
}


fun CoroutineScope.doThis() {
    launch {
        println("If a function is launching a child coroutine and should continue " +
                "runnig after the function returns,\npass the coroutine scope as " +
                "a parameter or receiver, and make it non-suspending.\n")
    }
}

fun doThatIn(scope: CoroutineScope) {
    scope.launch {
        println("Suspending functions, on the other hand, are designed to be " +
                "non-blocking and should not\nhave side-effects of launching any " +
                "concurrent work. Suspending functions can and should wait\nfor " +
                "all their work to complete before returning to the caller.\n")
    }
}


fun work(i: Int) {
    Thread.sleep(1000)
    println("Work $i done")
}

fun measureWorkInSingleThread() {
    println("measure work in runBlocking, all in the main thread")
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                launch {
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms\n")
}


fun measureWorkInMultiThread() {
    println("measure work in runBlocking, but in multiple threads")
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                launch(Dispatchers.Default) {
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms\n")
}

fun measureWorkInGlobalScopeWithoutSupervision() {
    println("measure work in GlobalScope, without supervision")
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                GlobalScope.launch {
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms\n")
}

fun measureWorkInGlobalScopeWithSupervision() {
    println("measure work in GlobalScope, with supervision")
    val time = measureTimeMillis {
        runBlocking {
            val jobs = mutableListOf<Job>()
            for (i in 1..2) {
                jobs += GlobalScope.launch {
                    work(i)
                }
            }
            jobs.forEach { it.join() }
        }
    }
    println("Done in $time ms\n")
}


suspend fun workInAnotherThread(i: Int) = withContext(Dispatchers.Default) {
    Thread.sleep(1000)
    println("Non Blocking Work $i done")
}

fun measureWorkInAnotherThread() {
    println("measure non blocking work")
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                launch {
                    workInAnotherThread(i)
                }
            }
        }
    }
    println("Done in $time ms\n")
}