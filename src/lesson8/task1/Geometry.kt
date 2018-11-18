@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import java.util.*
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))

    fun byHalf(p: Point) =
            Point((x + p.x) / 2,
                    (y + p.y) / 2)


    fun isInCircle(c: Circle) = (this.distance(c.center) <= c.radius)
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double =
            if (center.distance(other.center) <= (radius + other.radius))
                0.0 else center.distance(other.center) - (radius + other.radius)


    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = (center.distance(p) <= radius)
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2)
        throw IllegalArgumentException("Count of points < 2")

    if (points.size == 2)
        return Segment(points[0], points[1])

    var pointMax1 = Point(0.0, 0.0)
    var pointMax2 = Point(0.0, 0.0)
    var maxDistance = 0.0

    for (i in 0 until points.size) {
        for (j in i + 1 until points.size) {
            if (points[i].distance(points[j]) > maxDistance) {
                maxDistance = points[i].distance(points[j])
                pointMax1 = points[i]
                pointMax2 = points[j]
            }
        }
    }

    return Segment(pointMax1, pointMax2)
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle =
        Circle(
                diameter.begin.byHalf(diameter.end),
                diameter.begin.distance(diameter.end) / 2
        )

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val del = sin(angle) * cos(other.angle) - cos(angle) * sin(other.angle)
        if (del.equals(0.0)) throw Exception("Parallel lines")

        val delY = other.b * sin(angle) - b * sin(other.angle)
        val delX = other.b * cos(angle) - b * cos(other.angle)

        return Point(delX / del, delY / del)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int = 31 * b.hashCode() + angle.hashCode()

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

fun getPositiveAngle(angle: Double) =
        angle % PI + if (angle < 0) PI else .0

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line =
        Line(s.begin, getPositiveAngle(atan((s.begin.y - s.end.y) / (s.begin.x - s.end.x))))

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line =
        Line(b.byHalf(a),
                if (a.y == b.y) PI / 2
                else getPositiveAngle(atan((b.x - a.x) / (a.y - b.y)))
        )


/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2)
        throw IllegalArgumentException("Count of points < 2")

    if (circles.size == 2)
        return Pair(circles[0], circles[1])

    var circleMin1 = Circle(Point(.0, .0), .0)
    var circleMin2 = Circle(Point(.0, .0), .0)
    var minDistance = Double.POSITIVE_INFINITY

    for (i in 0 until circles.size) {
        for (j in i + 1 until circles.size) {
            if (circles[i].distance(circles[j]) < minDistance) {
                minDistance = circles[i].distance(circles[j])
                circleMin1 = circles[i]
                circleMin2 = circles[j]
            }
        }
    }

    return Pair(circleMin1, circleMin2)
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val center = bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c))
    val radius = center.distance(a)
    return Circle(center, radius)
}

fun findMinPoints(points: List<Point>): List<Point> {
    fun isRight(a: Point, b: Point, c: Point) =
            (b.x - a.x) * (c.y - b.y) - (b.y - a.y) * (c.x - b.x) < 0

    var myAnswer = points.toMutableList()

    for (i in 1 until myAnswer.size)
        if (myAnswer[i].x < myAnswer[0].x)
            myAnswer[0] = myAnswer[i].also { myAnswer[i] = myAnswer[0] }

    for (i in 2 until myAnswer.size) {
        var j = i
        while (j > 1 && isRight(myAnswer[0], myAnswer[j - 1], myAnswer[j])) {
            myAnswer[j - 1] = myAnswer[j].also { myAnswer[j] = myAnswer[j - 1] }
            j--
        }
    }

    var minPoints = Stack<Point>()
    minPoints.add(myAnswer[0])
    minPoints.add(myAnswer[1])

    for (i in 2 until myAnswer.size){
        while (isRight(minPoints[minPoints.size - 2], minPoints[minPoints.size - 1], myAnswer[i]))
            minPoints.pop()
        minPoints.push(myAnswer[i])
    }

    return minPoints.toList()
}


/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty())
        throw IllegalArgumentException()
    if (points.size == 1)
        return Circle(points[0], .0)
    if (points.size == 2)
        return circleByDiameter(Segment(points[0], points[1]))

    fun maxDiameter(p: List<Point>): Pair<Point, Point> {
        var maxL = -1.0
        var answer = Point(.0, .0) to Point(.0, .0)
        for (i in 0 until p.size)
            for (j in i + 1 until p.size) {
                if (p[i].distance(p[j]) > maxL) {
                    maxL = p[i].distance(p[j])
                    answer = p[i] to p[j]
                }
            }
        return answer
    }

    var minPoints = findMinPoints(points.toList())

    var (firstPoint, secondPoint) = maxDiameter(minPoints)
    var circle = circleByDiameter(Segment(firstPoint, secondPoint))
    if (minPoints.all { it.isInCircle(circle) }) return circle

    var minCircle = circle
    var minRadius = Double.MAX_VALUE
    for (i in 0 until minPoints.size)
        for (j in i + 1 until minPoints.size)
            for (k in j + 1 until minPoints.size) {
                var curCircle = circleByThreePoints(minPoints[i], minPoints[j], minPoints[k])
                if (curCircle.radius < minRadius &&
                        minPoints.all { it.isInCircle(curCircle) }){
                    minCircle = curCircle
                    minRadius = minCircle.radius
                }

            }

    return minCircle
}

