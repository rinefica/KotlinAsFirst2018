@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import kotlin.math.max

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    var mapC = mutableMapOf<String, String>()
    var newPhone = mutableSetOf<String?>()

    var allKeys = mutableSetOf<String>()
    mapA.forEach { t, _ -> allKeys.add(t) }
    mapB.forEach { t, _ -> allKeys.add(t) }

    for (name in allKeys) {
        newPhone.clear()

        if (name in mapA)
            newPhone.add(mapA[name])
        if (name in mapB)
            newPhone.add(mapB[name])

        mapC[name] = newPhone.joinToString()
    }

    return mapC
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    if (grades.isEmpty()) return mapOf()

    var gradeToStudent = mutableMapOf<Int, List<String>>()
    grades.forEach { t, u ->
        gradeToStudent[u] = (gradeToStudent[u] ?: emptyList()) + t
    }

    return gradeToStudent
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((key, value) in a) {
        if (b[key] != value)
            return false
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    var averagePrice = mutableMapOf<String, Double>()
    var sumPrice = mutableMapOf<String, Pair<Double, Int>>()

    for ((name, price) in stockPrices) {
        if (!sumPrice.containsKey(name))
            sumPrice.put(name, price to 1)
        else
            sumPrice[name] = (((sumPrice[name]?.first ?: 0.0) + price)
                    to ((sumPrice[name]?.second ?: 1) + 1))
    }

    sumPrice.forEach { t, u -> averagePrice[t] = u.first / u.second }

    return averagePrice
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var minPrice = Double.MAX_VALUE
    var answer: String? = null

    stuff.forEach { t, u ->
        if ((u.first == kind) &&
                ((minPrice == Double.MAX_VALUE) || (minPrice > u.second))) {
            minPrice = u.second
            answer = t
        }
    }

    return answer
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    var allFriends = mapOf<String, Set<String>>()
    var allFriendsOfName: Set<String>
    var friendsToAdd: Set<String>
    var curFriends: Set<String>

    for (name in friends.keys) {
        friendsToAdd = friends.getValue(name)
        allFriendsOfName = friendsToAdd

        do {
            curFriends = friendsToAdd
            for (friend in curFriends) {
                friendsToAdd = friends[friend]?.filter {
                    it !in allFriendsOfName.union(setOf(name))
                }?.toSet() ?: setOf()
                allFriendsOfName += friendsToAdd
            }
        } while (friendsToAdd.isNotEmpty())

        allFriends += name to allFriendsOfName
    }

    var allName = setOf<String>()
    for (names in friends.values)
        allName += names
    allName -= friends.keys
    allName.forEach { allFriends += it to setOf() }

    return allFriends
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit = b.forEach { key, value ->
    if (a.containsKey(key) && (a[key] == value))
        a.remove(key)
}


/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    var people = setOf<String>()
    for (name in a)
        if (b.contains(name))
            people += name
    return people.toList()
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String) =
        word.all { chars.map { it.toLowerCase() }.contains(it.toLowerCase()) }

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    var uniq = setOf<String>()
    var repeats = mutableMapOf<String, Int>()

    for (value in list) {
        if (uniq.contains(value)) {
            if (repeats.contains(value))
                repeats[value] = (repeats[value] ?: 0) + 1
            else
                repeats[value] = 2
        } else
            uniq += value
    }

    return repeats
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {

    fun createList(w: String): List<Char> {
        var word = listOf<Char>()
        for (c in w)
            word += c

        return word
    }

    var curWord: List<Char>
    var nextWord: List<Char>
    for (i in 0 until words.size) {
        curWord = createList(words[i])
        for (j in (i + 1) until words.size) {
            if (words[i].length == words[j].length) {
                nextWord = createList(words[j])
                for (c in curWord)
                    nextWord -= c
                if (nextWord.isEmpty())
                    return true
            }
        }

    }

    return false
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair( -1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    var min = -1
    var max = -1

    if (list.contains(0) && list.contains(number)) {
        val i0 = list.indexOf(0)
        val iNumber = list.lastIndexOf(number)
        if (i0 != iNumber) {
            min = kotlin.math.min(i0, iNumber)
            max = kotlin.math.max(i0, iNumber)
        }
    } else {
        if (number % 2 == 0) {
            val halfNumber = number / 2

            if (list.contains(halfNumber) &&
                    (list.indexOf(halfNumber) != list.lastIndexOf(halfNumber)))
                return Pair(list.indexOf(halfNumber),
                        list.lastIndexOf(halfNumber))
        }

        for (i in 0 until list.size) {

            if ((list[i] in setOf(0, number, if (number % 2 == 0) (number / 2) else 0))
                    || (list[i] > number))
                continue

            for (j in (i + 1) until list.size)
                if (list[i] + list[j] == number)
                    return Pair(i, j)

        }
    }

    return Pair(min, max)
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    var answer = setOf<String>()
    val countTreas = treasures.size + 1
    val countM = capacity + 1
    var price: Array<Array<Int>> = Array(countTreas) { Array(countM) { 0 } }

    fun findAnswer(k: Int, s: Int) {
        if (price[k][s] == 0)
            return
        if (price[k - 1][s] == price[k][s])
            findAnswer(k - 1, s)
        else {
            findAnswer(k - 1, s - treasures.values.toList()[k - 1].first)
            answer += treasures.keys.toList()[k - 1]
        }
    }

    for (i in 0 until countTreas)
        price[i][0] = 0
    for (i in 0 until countM)
        price[0][i] = 0

    var curWeight: Int
    var curPrice: Int
    for (i in 1 until countTreas) {
        for (j in 1 until countM) {
            curWeight = treasures.values.toList()[i - 1].first
            curPrice = treasures.values.toList()[i - 1].second
            price[i][j] =
                    if (curWeight <= j)
                        max(price[i - 1][j], price[i - 1][j - curWeight] + curPrice)
                    else
                        price[i - 1][j]

        }
    }

    findAnswer(countTreas - 1, countM - 1)

    return answer
}
