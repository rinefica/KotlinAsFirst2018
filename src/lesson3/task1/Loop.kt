@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.math.PI
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var count = 0
    var number = abs(n)

    do{
        count++
        number /= 10
    } while(number > 0)

    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int /*= if ((n == 1) || (n == 2)) 1 else (fib(n - 1) + fib(n - 2))*/
{
    if(n in setOf(1,2)) return 1

    var prev = 1
    var prevPrev = 1
    var cur = 2

    for(i in 3..n){
        cur = prev + prevPrev
        prevPrev = prev
        prev = cur
    }

    return cur
}


fun gcd(k: Int, l: Int): Int = if(k == l) k
    else gcd(max(k, l) - min(k, l), min(k, l))
/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int{
    if(max(m, n) % min(m, n) == 0) return max(m, n)
    if(isPrime(m) || isPrime(n)) return m * n
    return m * n / gcd(m, n)
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int{
    for(i in 2..Math.sqrt(n.toDouble()).toInt())
        if (n % i == 0)
            return i
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int) = n / minDivisor(n)

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    if((isPrime(m) && isPrime(n) && (max(m, n) % min(m, n) != 0)) ||
            (m == 1) || (n == 1)) return true

    return gcd(m, n) == 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean{
    val k = Math.sqrt(m.toDouble()).toInt()
    val j = Math.sqrt(n.toDouble()).toInt()

    return (k * k == m) || (j * j == n) || (k != j)
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int =
        if(x == 1) 0
        else 1 + if(x % 2 == 0) collatzSteps(x / 2) else collatzSteps(3 * x + 1)

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double{
    var number = x % (2 * PI)

    fun createMember(n: Int): Double{
        val i = 2 * n + 1
        val sign = if(n % 2 == 0) 1.0 else -1.0

        return sign * pow(number, i.toDouble()) / factorial(i)
    }

    var answer = number
    var n = 1

    var member = createMember(n)

    while(Math.abs(member) >= eps){
        answer += member
        n++
        member = createMember(n)
    }

    return answer
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double = sin(x + PI / 2, eps)

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int{

    if(n < 10) return n

    var number = n
    var answer = 0

    while(number > 0){
        answer = answer*10 + number % 10
        number /=10
    }

    return answer
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean =
        n == revert(n)


/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean{
    if(n < 10) return false

    val z = n % 10
    var number = n/10

    while(number > 0){
        if(z != number % 10)
            return true
        number /= 10
    }
    return false
}

fun findDigitN(n: Int, functionCreate: (Int) -> Int): Int{
    var member: Int
    var length = 0
    var curLength = 0
    var i = 0

    do{
        length += curLength
        i++
        member = functionCreate(i)
        curLength = digitNumber(member)
    }while(length + curLength < n)

    for(j in 1..curLength - (n - length))
        member /= 10

    return member % 10
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 1 4 9 16 25 36 49 64 81 100 121 144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int{
    if(n == 1) return 1

    return findDigitN(n){it * it}
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1 1 2 3 5 8 13 21 34 55 89 144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int{
    if(n in setOf(1, 2)) return 1

    return findDigitN(n){fib(it)}
}
