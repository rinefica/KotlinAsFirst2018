package lesson5.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun shoppingListCostTest() {
        val itemCosts = mapOf(
                "Хлеб" to 50.0,
                "Молоко" to 100.0
        )
        assertEquals(
                150.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко"),
                        itemCosts
                )
        )
        assertEquals(
                150.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко", "Кефир"),
                        itemCosts
                )
        )
        assertEquals(
                0.0,
                shoppingListCost(
                        listOf("Хлеб", "Молоко", "Кефир"),
                        mapOf()
                )
        )
    }

    @Test
    @Tag("Example")
    fun filterByCountryCode() {
        val phoneBook = mutableMapOf(
                "Quagmire" to "+1-800-555-0143",
                "Adam's Ribs" to "+82-000-555-2960",
                "Pharmakon Industries" to "+1-800-555-6321"
        )

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+1")
        assertEquals(2, phoneBook.size)

        filterByCountryCode(phoneBook, "+999")
        assertEquals(0, phoneBook.size)
    }

    @Test
    @Tag("Example")
    fun removeFillerWords() {
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я как-то люблю Котлин".split(" "),
                        "как-то"
                )
        )
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я как-то люблю таки Котлин".split(" "),
                        "как-то",
                        "таки"
                )
        )
        assertEquals(
                "Я люблю Котлин".split(" "),
                removeFillerWords(
                        "Я люблю Котлин".split(" "),
                        "как-то",
                        "таки"
                )
        )
    }

    @Test
    @Tag("Example")
    fun buildWordSet() {
        assertEquals(
                buildWordSet("Я люблю Котлин".split(" ")),
                mutableSetOf("Я", "люблю", "Котлин")
        )
        assertEquals(
                buildWordSet("Я люблю люблю Котлин".split(" ")),
                mutableSetOf("Котлин", "люблю", "Я")
        )
        assertEquals(
                buildWordSet(listOf()),
                mutableSetOf<String>()
        )
    }

    @Test
    @Tag("Normal")
    fun mergePhoneBooks() {
        assertEquals(
                mapOf("Emergency" to "112"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "112")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "112", "Police" to "02")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112, 911", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112"),
                        mapOf("Emergency" to "911", "Police" to "02")
                )
        )
        assertEquals(
                mapOf("Emergency" to "112, 911", "Fire department" to "01", "Police" to "02"),
                mergePhoneBooks(
                        mapOf("Emergency" to "112", "Fire department" to "01"),
                        mapOf("Emergency" to "911", "Police" to "02")
                )
        )
    }

    @Test
    @Tag("Easy")
    fun buildGrades() {
        assertEquals(
                mapOf<Int, List<String>>(),
                buildGrades(mapOf())
        )
        // TODO: Sort the values here or let the students do it?
        assertEquals(
                mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат")),
                buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
        )
        assertEquals(
                mapOf(3 to listOf("Семён", "Михаил", "Марат")),
                buildGrades(mapOf("Марат" to 3, "Семён" to 3, "Михаил" to 3))
        )
    }

    @Test
    @Tag("Easy")
    fun containsIn() {
        assertTrue(containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")))
        assertFalse(containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")))
    }

    @Test
    @Tag("Normal")
    fun averageStockPrice() {
        assertEquals(
                mapOf<String, Double>(),
                averageStockPrice(listOf())
        )
        assertEquals(
                mapOf("MSFT" to 100.0, "NFLX" to 40.0),
                averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
        )
        assertEquals(
                mapOf("MSFT" to 150.0, "NFLX" to 40.0),
                averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
        )
        assertEquals(
                mapOf("MSFT" to 150.0, "NFLX" to 45.0),
                averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
        )
    }

    @Test
    @Tag("Normal")
    fun findCheapestStuff() {
        assertNull(
                findCheapestStuff(
                        mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                        ""
                )
        )
        assertNull(
                findCheapestStuff(
                        mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                        "торт"
                )
        )
        assertEquals(
                "Мария",
                findCheapestStuff(
                        mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
                        "печенье"
                )
        )
    }

    @Test
    @Tag("Hard")
    fun propagateHandshakes() {
        assertEquals(
                mapOf("226" to setOf("1ba", "125", "d7", "298", "136",
                        "189", "135", "1", "334", "30c", "35b", "26c", "29c", "324", "23", "0"),
                        "1ba" to setOf("189", "136", "135", "226", "125", "1", "334", "30c",
                                "35b", "26c", "29c", "298", "324", "23", "d7", "0"),
                        "135" to setOf("125", "136", "23", "1ba", "226", "35b", "d7", "189", "30c",
                                "26c", "1", "334", "29c", "298", "324", "0"),
                        "1" to setOf("1ba", "189", "136", "135", "226", "125", "334",
                                "30c", "35b", "26c", "29c", "298", "324", "23", "d7", "0"),
                        "324" to setOf(),
                        "26c" to setOf(),
                        "0" to setOf("125", "324", "1", "226", "189", "23", "1ba", "26c", "136",
                                "334", "29c", "298", "d7", "135", "35b", "30c"),
                        "189" to setOf("226", "30c", "1ba", "29c", "125", "d7", "298", "136", "35b",
                                "0", "324", "26c", "23", "135", "334", "1"),
                        "125" to setOf("136", "23", "1ba", "226", "35b", "d7", "189",
                                "30c", "26c", "135", "1", "334", "29c", "298", "324", "0"),
                        "136" to setOf("26c", "35b", "125", "1ba", "226", "1", "23", "d7",
                                "189", "30c", "135", "334", "29c", "298", "324", "0"),
                        "298" to setOf("1ba", "226", "125", "189", "136", "135",
                                "1", "334", "30c", "35b", "26c", "29c", "324", "23", "d7", "0"),
                        "d7" to setOf("26c",
                                "226", "136", "125", "35b", "0", "135", "29c", "324",
                                "1ba", "189", "334", "1", "23", "298", "30c"),
                        "23" to setOf("226", "125", "189", "1ba", "d7", "298", "136",
                                "35b", "30c", "29c", "135", "1", "334", "26c", "324", "0"),
                        "334" to setOf("125", "136", "324", "226", "1", "1ba", "30c", "26c",
                                "35b", "d7", "189", "298", "0", "29c", "23", "135"),
                        "30c" to setOf("125", "1ba", "136", "35b", "0", "298", "226", "324", "189",
                                "26c", "23", "135", "29c", "334", "d7", "1"),
                        "29c" to setOf("226", "125", "136", "1ba", "d7",
                                "298", "23", "35b", "189", "30c", "26c", "135", "1", "334", "324", "0"),
                        "35b" to setOf("1", "1ba", "189", "136", "135", "226",
                                "125", "334", "30c", "26c", "29c", "298", "324", "23", "d7", "0")
                ),
                propagateHandshakes(
                        mapOf(
                                "226" to setOf("1ba",
                                        "125","d7","298","136","189", "135","1", "334", "30c","35b",
                                        "26c","29c","324","23","0"),
                                "1ba" to setOf("189",
                                        "136",
                                        "135",
                                        "226",
                                        "125",
                                        "1",
                                        "334",
                                        "30c",
                                        "35b",
                                        "26c",
                                        "29c",
                                        "298",
                                        "324",
                                        "23",
                                        "d7",
                                        "0"),
                                "135" to setOf("125",
                                        "136",
                                        "23",
                                        "1ba",
                                        "226",
                                        "35b",
                                        "d7",
                                        "189",
                                        "30c",
                                        "26c",
                                        "1",
                                        "334",
                                        "29c",
                                        "298",
                                        "324",
                                        "0"),
                                "1" to setOf("1ba",
                                        "189",
                                        "136",
                                        "135",
                                        "226",
                                        "125",
                                        "334",
                                        "30c",
                                        "35b",
                                        "26c",
                                        "29c",
                                        "298",
                                        "324",
                                        "23",
                                        "d7",
                                        "0"),
                                "324" to setOf(),
                                "26c" to setOf(),
                                "0" to setOf("125",
                                        "324",
                                        "1",
                                        "226",
                                        "189",
                                        "23",
                                        "1ba",
                                        "26c",
                                        "136",
                                        "334",
                                        "29c",
                                        "298",
                                        "d7",
                                        "135",
                                        "35b",
                                        "30c"),
                                "189" to setOf("226","30c","1ba","29c","125","d7",
                                        "298","136","35b","0","324","26c","23","135", "334","1"),
                                "125" to setOf("136",
                                        "23","1ba","226","35b","d7","189","30c","26c","135",
                                        "1","334","29c","298","324","0"),
                                "136" to setOf("26c","35b","125","1ba", "226","1","23","d7","189",
                                        "30c","135","334","29c","298","324","0"),
                                "298" to setOf("1ba","226","125","189","136","135","1","334","30c",
                                        "35b","26c","29c","324","23","d7","0"),
                                "d7" to setOf("26c","226","136","125",
                                        "35b","0","135","29c","324",
                                        "1ba","189","334","1","23","298","30c"),
                                "23" to setOf("226","125","189","1ba","d7","298","136",
                                        "35b","30c","29c","135","1","334","26c","324","0"),
                                "334" to setOf("125","136","324","226","1","1ba","30c","26c","35b",
                                        "d7","189","298","0","29c","23","135"),
                                "30c" to setOf("125",
                                        "1ba","136","35b","0","298","226","324","189","26c",
                                        "23","135","29c","334","d7","1"),
                                "29c" to setOf("226","125","136","1ba","d7",
                                        "298","23","35b","189","30c","26c","135","1","334","324","0"),
                                "35b" to setOf("1","1ba","189","136","135","226","125","334","30c",
                                        "26c","29c","298","324","23","d7","0")
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Marat" to setOf("Mikhail", "Sveta"),
                        "Sveta" to setOf("Mikhail"),
                        "Mikhail" to setOf()
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Sveta"),
                                "Sveta" to setOf("Mikhail")
                        )
                )
        )
        assertEquals(
                mapOf(
                        "Marat" to setOf("Mikhail", "Sveta"),
                        "Sveta" to setOf("Marat", "Mikhail"),
                        "Mikhail" to setOf("Sveta", "Marat")
                ),
                propagateHandshakes(
                        mapOf(
                                "Marat" to setOf("Mikhail", "Sveta"),
                                "Sveta" to setOf("Marat"),
                                "Mikhail" to setOf("Sveta")
                        )
                )
        )
    }

    @Test
    @Tag("Easy")
    fun subtractOf() {
        val from = mutableMapOf("a" to "z", "b" to "c")

        subtractOf(from, mapOf())
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("b" to "z"))
        assertEquals(from, mapOf("a" to "z", "b" to "c"))

        subtractOf(from, mapOf("a" to "z"))
        assertEquals(from, mapOf("b" to "c"))
    }

    @Test
    @Tag("Easy")
    fun whoAreInBoth() {
        assertEquals(
                emptyList<String>(),
                whoAreInBoth(emptyList(), emptyList())
        )
        assertEquals(
                listOf("Marat"),
                whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Marat", "Kirill"))
        )
        assertEquals(
                emptyList<String>(),
                whoAreInBoth(listOf("Marat", "Mikhail"), listOf("Sveta", "Kirill"))
        )
    }

    @Test
    @Tag("Normal")
    fun canBuildFrom() {
        assertTrue(canBuildFrom(listOf('C', ';', ')',
                'p', '㚬', 'ᠱ', 'f', ':', '', ',', '盾', 'L', 'ᲂ',
                '?', 'S', '鐮', 'N', '/', 'ᇻ', '號', 'G', 'U', '武', '⬱', 'L', '睍'), "g"))
        assertTrue(canBuildFrom(listOf('&', '{', 'O', 'j', '扻', 'Ꞣ', '裟', 'X', '*', 'A',
                'X', '', '`', 'U', 'M', 'k', 'V', 'h', '', '~', '䇗', '힩', '?', '', '辉', '~'), "K"))
        assertFalse(canBuildFrom(emptyList(), "foo"))
        assertTrue(canBuildFrom(listOf('a', 'b', 'o'), "baobab"))
        assertFalse(canBuildFrom(listOf('a', 'm', 'r'), "Marat"))
    }

    @Test
    @Tag("Normal")
    fun extractRepeats() {
        assertEquals(
                emptyMap<String, Int>(),
                extractRepeats(emptyList())
        )
        assertEquals(
                mapOf("a" to 2),
                extractRepeats(listOf("a", "b", "a"))
        )

        assertEquals(
                mapOf("a" to 2, "b" to 2, "c" to 3),
                extractRepeats(listOf("a", "b", "a", "b", "c", "c", "c")))

        assertEquals(
                mapOf("a" to 2, "c" to 3),
                extractRepeats(listOf("a", "b", "a", "d", "c", "c", "c"))
        )
        assertEquals(
                emptyMap<String, Int>(),
                extractRepeats(listOf("a", "b", "c"))
        )
    }

    @Test
    @Tag("Normal")
    fun hasAnagrams() {
        assertFalse(hasAnagrams(emptyList()))
        assertTrue(hasAnagrams(listOf("рот", "свет", "тор")))
        assertFalse(hasAnagrams(listOf("рот", "свет", "код", "дверь")))
    }

    @Test
    @Tag("Hard")
    fun findSumOfTwo() {
        assertEquals(
                Pair(-1, -1),
                findSumOfTwo(listOf(1, 24555, 40700, 0, 28371, 1, 30069), 0)
        )
        assertEquals(
                Pair(-1, -1),
                findSumOfTwo(emptyList(), 1)
        )
        assertEquals(
                Pair(0, 2),
                findSumOfTwo(listOf(1, 2, 3), 4)
        )
        assertEquals(
                Pair(-1, -1),
                findSumOfTwo(listOf(1, 2, 3), 6)
        )
        assertEquals(
                Pair(3, 4),
                findSumOfTwo(listOf(1, 2, 3, 0, 5), 5)
        )
        assertEquals(
                Pair(1, 5),
                findSumOfTwo(listOf(1, 6, 7, 2, 3, 6), 12)
        )
    }

    @Test
    @Tag("Impossible")
    fun bagPacking() {

        assertEquals(
                setOf("13"),
                bagPacking(
                        mapOf("0" to (234 to 101),
                                "1" to (149 to 418),
                                "2" to (1 to 2),
                                "3" to (233 to 148),
                                "4" to (2 to 149),
                                "5" to (144 to 346),
                                "6" to (149 to 2),
                                "7" to (2 to 149),
                                "8" to (328 to 441),
                                "9" to (2 to 286),
                                "10" to (149 to 1),
                                "11" to (2 to 149),
                                "12" to (1 to 2),
                                "13" to (1 to 148),
                                "14" to (148 to 149)),
                        1
                )
        )
        assertEquals(
                setOf("Кубок"),
                bagPacking(
                        mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                        850
                )
        )
        assertEquals(
                emptySet<String>(),
                bagPacking(
                        mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
                        450
                )
        )
    }

    // TODO: map task tests
}
