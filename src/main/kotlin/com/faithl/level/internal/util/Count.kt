package com.faithl.level.internal.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * Count 计算字符串并返回结果
 * 来自: https://www.cnblogs.com/woider/p/5331391.html
 *
 * @return
 */
fun String.count(): Double {
    return Calculator.conversion(this);
}

/**
 * 算数表达式求值
 * 直接调用Calculator的类方法conversion()
 * 传入算数表达式，将返回一个浮点值结果
 * 如果计算过程错误，将返回一个NaN
 */
class Calculator {

    private val postfixStack = Stack<String?>() // 后缀式栈
    private val opStack = Stack<Char>() // 运算符栈
    private val operatPriority = intArrayOf(0, 3, 2, 1, -1, 1, 0, 2) // 运用运算符ASCII码-40做索引的运算符优先级

    /**
     * 按照给定的表达式计算
     *
     * @param expression
     * 要计算的表达式例如:5+12*(3+5)/7
     * @return
     */
    fun calculate(expression: String): Double {
        val resultStack = Stack<String>()
        prepare(expression)
        Collections.reverse(postfixStack) // 将后缀式栈反转
        var firstValue: String
        var secondValue: String
        var currentValue: String? // 参与计算的第一个值，第二个值和算术运算符
        while (!postfixStack.isEmpty()) {
            currentValue = postfixStack.pop()
            if (!isOperator(currentValue!![0])) { // 如果不是运算符则存入操作数栈中
                currentValue = currentValue.replace("~", "-")
                resultStack.push(currentValue)
            } else { // 如果是运算符则从操作数栈中取两个值和该数值一起参与运算
                secondValue = resultStack.pop()
                firstValue = resultStack.pop()

                // 将负数标记符改为负号
                firstValue = firstValue.replace("~", "-")
                secondValue = secondValue.replace("~", "-")
                val tempResult = calculate(firstValue, secondValue, currentValue[0])
                resultStack.push(tempResult)
            }
        }
        return java.lang.Double.valueOf(resultStack.pop())
    }

    /**
     * 数据准备阶段将表达式转换成为后缀式栈
     *
     * @param expression
     */
    private fun prepare(expression: String) {
        opStack.push(',') // 运算符放入栈底元素逗号，此符号优先级最低
        val arr = expression.toCharArray()
        var currentIndex = 0 // 当前字符的位置
        var count = 0 // 上次算术运算符到本次算术运算符的字符的长度便于或者之间的数值
        var currentOp: Char
        var peekOp: Char // 当前操作符和栈顶操作符
        for (i in arr.indices) {
            currentOp = arr[i]
            if (isOperator(currentOp)) { // 如果当前字符是运算符
                if (count > 0) {
                    postfixStack.push(String(arr, currentIndex, count)) // 取两个运算符之间的数字
                }
                peekOp = opStack.peek()
                if (currentOp == ')') { // 遇到反括号则将运算符栈中的元素移除到后缀式栈中直到遇到左括号
                    while (opStack.peek() != '(') {
                        postfixStack.push(opStack.pop().toString())
                    }
                    opStack.pop()
                } else {
                    while (currentOp != '(' && peekOp != ',' && compare(currentOp, peekOp)) {
                        postfixStack.push(opStack.pop().toString())
                        peekOp = opStack.peek()
                    }
                    opStack.push(currentOp)
                }
                count = 0
                currentIndex = i + 1
            } else {
                count++
            }
        }
        if (count > 1 || count == 1 && !isOperator(arr[currentIndex])) { // 最后一个字符不是括号或者其他运算符的则加入后缀式栈中
            postfixStack.push(String(arr, currentIndex, count))
        }
        while (opStack.peek() != ',') {
            postfixStack.push(opStack.pop().toString()) // 将操作符栈中的剩余的元素添加到后缀式栈中
        }
    }

    /**
     * 判断是否为算术符号
     *
     * @param c
     * @return
     */
    private fun isOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')'
    }

    /**
     * 利用ASCII码-40做下标去算术符号优先级
     *
     * @param cur
     * @param peek
     * @return
     */
    fun compare(cur: Char, peek: Char): Boolean { // 如果是peek优先级高于cur，返回true，默认都是peek优先级要低
        var result = false
        if (operatPriority[peek.code - 40] >= operatPriority[cur.code - 40]) {
            result = true
        }
        return result
    }

    /**
     * 按照给定的算术运算符做计算
     *
     * @param firstValue
     * @param secondValue
     * @param currentOp
     * @return
     */
    private fun calculate(firstValue: String, secondValue: String, currentOp: Char): String {
        var result = ""
        when (currentOp) {
            '+' -> result = java.lang.String.valueOf(ArithHelper.add(firstValue, secondValue))
            '-' -> result = java.lang.String.valueOf(ArithHelper.sub(firstValue, secondValue))
            '*' -> result = java.lang.String.valueOf(ArithHelper.mul(firstValue, secondValue))
            '/' -> result = java.lang.String.valueOf(ArithHelper.div(firstValue, secondValue))
        }
        return result
    }

    companion object {
        fun conversion(expression: String): Double {
            var expressionClone = expression
            var result = 0.0
            val cal = Calculator()
            try {
                expressionClone = transform(expressionClone)
                result = cal.calculate(expressionClone)
            } catch (e: Exception) {
                // e.printStackTrace();
                // 运算错误返回NaN
                return 0.0 / 0.0
            }
            // return new String().valueOf(result);
            return result
        }

        /**
         * 将表达式中负数的符号更改
         *
         * @param expression
         * 例如-2+-1*(-3E-2)-(-1) 被转为 ~2+~1*(~3E~2)-(~1)
         * @return
         */
        private fun transform(expression: String): String {
            val arr = expression.toCharArray()
            for (i in arr.indices) {
                if (arr[i] == '-') {
                    if (i == 0) {
                        arr[i] = '~'
                    } else {
                        val c = arr[i - 1]
                        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == 'E' || c == 'e') {
                            arr[i] = '~'
                        }
                    }
                }
            }
            return if (arr[0] == '~' || arr[1] == '(') {
                arr[0] = '-'
                "0" + String(arr)
            } else {
                String(arr)
            }
        }
    }
}

object ArithHelper {

    // 默认除法运算精度
    private const val DEF_DIV_SCALE = 16

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    fun add(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.add(b2).toDouble()
    }

    fun add(v1: String?, v2: String?): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.add(b2).toDouble()
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    fun sub(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.subtract(b2).toDouble()
    }

    fun sub(v1: String?, v2: String?): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.subtract(b2).toDouble()
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1
     * 被乘数
     * @param v2
     * 乘数
     * @return 两个参数的积
     */
    fun mul(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.multiply(b2).toDouble()
    }

    fun mul(v1: String?, v2: String?): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.multiply(b2).toDouble()
    }

    fun div(v1: String?, v2: String?): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, DEF_DIV_SCALE, RoundingMode.HALF_UP).toDouble()
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    @JvmOverloads
    fun div(v1: Double, v2: Double, scale: Int = DEF_DIV_SCALE): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.divide(b2, scale, RoundingMode.HALF_UP).toDouble()
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    fun round(v: Double, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v.toString())
        val one = BigDecimal("1")
        return b.divide(one, scale, RoundingMode.HALF_UP).toDouble()
    }

    fun round(v: String?, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b = BigDecimal(v)
        val one = BigDecimal("1")
        return b.divide(one, scale, RoundingMode.HALF_UP).toDouble()
    }
}