package kr.wayde.githubsearch.util


typealias Invoker = (() -> Unit)

typealias InvokerT<T> = ((T) -> Unit)

typealias InvokerRetBool = (() -> Boolean)

// 코드에 직접 {} 형태의 람다를 넣게 되면 프로가드에서 걸려버림.
val NOTHING : Invoker = {}