@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
/*fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }

    println("${dateStrToDigit("15 июля 2016")}")
}*/

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val date = str.split(" ")
    val month = listOf("января", "февраля", "марта", "апреля",
            "мая", "июня", "июля", "августа", "сентября",
            "октября", "ноября", "декабря")

    try {
        if (date.size > 3)
            throw Exception("Data false")

        if (!month.contains(date[1]))
            throw Exception("Month false")

        val monthNumber = month.indexOf(date[1]) + 1

        if (daysInMonth(monthNumber, date[2].toInt()) < date[0].toInt())
            throw Exception("Days false")

        return String.format("%02d.%02d.%d", date[0].toInt(), monthNumber, date[2].toInt())
    } catch (e: Exception) {
        return ""
    }

}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val date = digital.split(".")
    val month = listOf("января", "февраля", "марта", "апреля",
            "мая", "июня", "июля", "августа", "сентября",
            "октября", "ноября", "декабря")

    try {
        if (date.size > 3)
            throw Exception("Data false")

        if (date[1].toInt() !in 1..12)
            throw Exception("Month false")

        val monthNumber = date[1].toInt()

        if (daysInMonth(monthNumber, date[2].toInt()) < date[0].toInt())
            throw Exception("Days false")

        return String.format("%d %s %d", date[0].toInt(), month[monthNumber - 1], date[2].toInt())
    } catch (e: Exception) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    if (!phone.matches(Regex("""^(\+\s*\d)?([\d\s\-)(])*""")))
        return ""

    var answer = ""
    phone.forEach {
        answer += if (it.isDigit() || (it == '+')) it else ""
    }

    return answer
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    var max = -1

    try {
        jumps.replace("[\\s]{2,}".toRegex(), " ")
                .split(" ").forEach {
                    if (it !in setOf("%", "-") && (it.toInt() > max))
                        max = it.toInt()
                }
    } catch (e: Exception) {
        return -1
    }

    return max
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val results = jumps.split(" ")
    var max = -1
    var curHeight: Int
    var curRes: String

    try {
        for (i in 0 until results.size step 2) {
            curHeight = results[i].toInt()
            curRes = results[i + 1]

            if (!curRes.all { it in setOf('+', '-', '%') }) throw Exception("Incorrect result")

            if (curRes.contains('+') && (curHeight > max))
                max = curHeight
        }
    } catch (e: Exception) {
        return -1
    }

    return max
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (!expression.matches(Regex("""^\d+( [+-] \d+)*""")))
        throw IllegalArgumentException()

    val operand = expression.split(" ")
    var answer = 0
    var member: Int
    var sign = "+"

    for (i in 0 until operand.size step 2) {
        member = operand[i].toInt()
        answer += when (sign) {
            "+" -> member
            "-" -> -member
            else -> 0
        }

        if (i == operand.size - 1) break

        sign = operand[i + 1]
    }
    return answer
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val words = str.split(" ")
    var currentIndex = 0

    for (i in 0 until (words.size - 1)) {
        if (words[i].toLowerCase() == words[i + 1].toLowerCase())
            return currentIndex
        currentIndex += words[i].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val items = description.split("; ")
    var maxPrice = -1.0
    var curPrice: Double
    var maxName = ""

    try {
        items.forEach {
            var curItem = it.split(" ")
            curPrice = curItem[1].toDouble()

            if (curPrice < 0) throw Exception("Incorrect price")

            if (maxPrice < curPrice) {
                maxPrice = curPrice
                maxName = curItem[0]
            }
        }
    } catch (e: Exception) {
        return ""
    }

    return maxName
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val base = listOf('I', 'V', 'X', 'L', 'C', 'D', 'M')
    val dec = listOf(1, 5, 10, 50, 100, 500, 1000)
    var answer = 0

    try {
        if (!roman.all { it in base }) throw Exception("Not number")

        if (roman.length == 1)
            answer += dec[base.indexOf(roman[0])]
        else {
            var prevDigit = roman[0]
            var curCount = 1
            var minIndex = base.indexOf(roman[0])

            for (i in 1 until roman.length) {
                if (roman[i] != prevDigit) {
                    if (base.indexOf(prevDigit) > base.indexOf(roman[i]))
                        answer += dec[base.indexOf(prevDigit)] * curCount
                    else {
                        if (prevDigit in setOf('V', 'L')) throw Exception("Not number")
                        if (base.indexOf(roman[i]) - base.indexOf(prevDigit) !in 1..2)
                            throw Exception("Not number")

                        minIndex = minOf(base.indexOf(roman[i]), base.indexOf(prevDigit), minIndex)
                        if (base.indexOf(roman[i]) - minIndex !in 0..2)
                            throw Exception("Not number")

                        answer -= dec[base.indexOf(prevDigit)] * curCount
                    }

                    curCount = 1
                    prevDigit = roman[i]
                } else {
                    curCount++
                    if ((curCount > 3) || (roman[i] in setOf('V', 'L'))) throw Exception("Not number")
                }

                if (i == (roman.length - 1))
                    answer += dec[base.indexOf(roman[i])] * curCount
            }
        }

    } catch (e: Exception) {
        return -1
    }
    return answer
}


/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()/*{

    fun bracketsIsCorrect(): Boolean {
        var brackets = Stack<Char>()
        commands.forEach {
            when (it) {
                '[' -> brackets.push(it)
                ']' -> {
                    if (brackets.isEmpty() || brackets.pop() != '[')
                        return false//throw IllegalArgumentException()
                }
            }
        }

        if (brackets.isNotEmpty()) return false//throw IllegalArgumentException()

        return true
    }

    fun commandsIsCorrect() =
            commands.all { it in setOf(' ', '>', '<', '+', '-', '[', ']') }

    if (!commandsIsCorrect() || !bracketsIsCorrect())
        throw IllegalArgumentException()

    var answer = Array(cells) { 0 }
    var curPos: Int = cells / 2
    var curCommand = 0

    for (i in 0 until limit) {
        if (curCommand >= commands.length) break
        when (commands[curCommand]) {
            //' ' -> curCommand++
            '>' -> {
                curPos++
                if (curPos >= answer.size)
                    throw IllegalStateException()
            }
            '<' -> {
                curPos--
                if (curPos < 0)
                    throw IllegalStateException()
            }
            '+' -> {
                answer[curPos]++
            }
            '-' -> {
                answer[curPos]--
            }
            '[' ->
                if (answer[curPos] == 0) {
                    //new curCommand!!!
                    var brackets = Stack<Char>()
                    for (j in curCommand + 1 until commands.length) {
                        if (commands[j] == '[')
                            brackets.push(commands[j])
                        if (commands[j] == ']')
                            if (brackets.isEmpty())
                                curCommand = j
                            else
                                brackets.pop()
                    }
                }
            ']' -> {
                if (answer[curPos] != 0) {
                    //new curCommand!!!
                    var brackets = Stack<Char>()

                    for (j in curCommand - 1 downTo 0) {
                        if (commands[j] == ']')
                            brackets.push(commands[j])
                        if (commands[j] == '[')
                            if (brackets.isEmpty())
                                curCommand = j
                            else
                                brackets.pop()
                    }
                }
            }
        }
        curCommand++
    }

    return answer.toList()
}*/
