@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.isPrime
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double{
    var abs = 0.0
    for(a in v)
        abs += (a * a)
    return Math.sqrt(abs)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
        if(list.isEmpty()) 0.0
        else
            list.sum()/list.size


/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double>{
    if(list.isEmpty()) return list

    val mean = list.average()
    for(i in 0 until list.size)
        list[i] -= mean

    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double = a.foldIndexed(0.0){
    index, previousResult, element ->
    previousResult + element * b[index]
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double{
    if(p.isEmpty()) return 0.0

    return p.fold(0.0){
        previousResult, element ->
        previousResult + element * Math.pow(x, p.indexOf(element).toDouble())
    }
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    if(list.isEmpty()) return list

    var sum = list[0]

    for(i in 1 until list.size){
        sum += list[i]
        list[i] = sum
    }

    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int>{

    fun findFactor(n: Int):Int{
        if (n % 2 == 0) return 2
        for(i in 3..n step 2)
            if(isPrime(i) && (n % i == 0))
                return i
        return n
    }

    var factor = listOf<Int>()
    var number = n

    while(number > 1){
        if(isPrime(number)){
            factor += number
            break
        }
        factor += findFactor(number)
        number /= factor.last()
    }

    return factor
}


/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int>{
    if(n == 0)
        return listOf(0)

    var nInBase = listOf<Int>()
    var number = n

    while(number >= base){
        nInBase += number % base
        number /= base
    }

    nInBase += number

    return nInBase.asReversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String =
        convert(n, base).map{if(it > 9) 'a' + (it - 10) else it}.joinToString(separator = "")


/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int{

    fun pow(n: Int, s: Int): Int{
        if(n == 0) return 0
        if((n == 1) || (s == 0)) return 1
        if(s == 1) return n

        var answer = n
        for(i in 2..s)
            answer *= n

        return answer
    }

    return digits.asReversed().foldIndexed(0){
        index, previousResult, element -> previousResult + element * pow(base, index)
    }
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int{
    var digits = listOf<Int>()

    for(char in str){
        if(char.isDigit())
            digits += (char - '0')
        else
            digits += (char - 'a' + 10)
    }

    return decimal(digits, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String{
    val rChar = listOf("I", "V", "X", "L", "C", "D", "M")
    fun createRomanDigit(d: Int, power: Int): String{
        var romanD = ""
        if(d != 0)
            if(d < 4)
                for(i in 1..d)
                    romanD += rChar[2 * power]
            else if(d == 4)
                romanD += (rChar[2 * power] + rChar[2 * power + 1])
            else if(d < 9) {
                romanD += rChar[2 * power + 1]
                for(i in 1..(d - 5))
                    romanD += rChar[2 * power]
            } else
                romanD += (rChar[2 * power] + rChar[2 * power + 2])

        return romanD
    }

    var answer = ""
    val digits = convert(n, 10).asReversed()

    if((digits.size > 3) && (digits[3] != 0))
        if(digits[3] < 4)
            for(i in 1..digits[3])
                answer += rChar[6]

    for(i in 2 downTo 0)
    if(digits.size > i)
        answer += createRomanDigit(digits[i], i)

    return answer
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999 999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String{
    if(n == 0) return "ноль"

    val s1000 = " тысяч"
    val s100 = listOf("", " сто"," двести"," триста"," четыреста"," пятьсот",
                      " шестьсот"," семьсот"," восемьсот"," девятьсот")
    val s10 = listOf(" десять"," одиннадцать"," двенадцать"," тринадцать"," четырнадцать",
            " пятнадцать"," шестнадцать"," семнадцать"," восемнадцать"," девятнадцать")
    val s2090 = listOf(" двадцать"," тридцать"," сорок"," пятьдесят"," шестьдесят",
            " семьдесят"," восемьдесят"," девяносто")
    val s1 = listOf("", " один"," два"," три"," четыре"," пять",
            " шесть"," семь"," восемь"," девять")

    fun translate(s1:Int, s0:Int):String = when (s1) {
        1 -> s10[s0]
        in 2..9 -> s2090[s1 - 2]
        else -> ""
    }

    var answer = ""
    val digits = convert(n, 10).asReversed()

    if(digits.size >= 6)
        answer += s100[digits[5]]

    if(digits.size >= 5)
        answer += translate(digits[4], digits[3])

    if(digits.size >= 4)
        answer += if ((digits.size == 4) || (digits[4] != 1))
            when(digits[3]){
                1 -> " одна тысяча"
                2 -> " две тысячи"
                3 -> " три тысячи"
                4 -> " четыре тысячи"
                0, in 5..9 -> s1[digits[3]] + s1000
                else -> ""
            } else if (digits[4] == 1)
                s1000
        else ""

    if(digits.size >= 3)
        answer += s100[digits[2]]

    if(digits.size >= 2)
        answer += translate(digits[1], digits[0])

    answer += if ((digits.size == 1) || (digits[1] != 1))
        s1[digits[0]] else ""

    return answer.trim()
}