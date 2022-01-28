package com.faithl.level.internal.util

/**
 * 判断表达式是不是只有一个数字
 *
 * @param str 原值
 * @return 数字非法性校验结果，失败返回false;
 */
private fun isNumber(str: String): Boolean {
    for (i in str.indices) {
        if (!Character.isDigit(str[i]) && str[i] != '.' && str[i] != ' ') {
            return false
        }
    }
    return true
}

fun String.eval() :Double {
    return getResult(this) ?: 0.0
}

/**
 * 解析算式，并计算算式结果；
 *
 * @param str 算式的字符串
 * @return double类型的算式结果
 */
fun getResult(str: String): Double? {

    // 递归头
    if (str.isEmpty() || isNumber(str)) {
        return if (str.isEmpty()) 0.0 else str.toDouble()
    }

    //递归体
    if (str.contains(")")) {
        // 最后一个左括号
        val lIndex = str.lastIndexOf("(")
        // 对于的右括号
        val rIndex = str.indexOf(")", lIndex)
        return getResult(
            str.substring(0, lIndex) + getResult(
                str.substring(
                    lIndex + 1,
                    rIndex
                )
            ) + str.substring(rIndex + 1)
        )
    }
    if (str.contains("+")) {
        val index = str.lastIndexOf("+")
        return getResult(str.substring(0, index))!! + getResult(str.substring(index + 1))!!
    }
    if (str.contains("-")) {
        val index = str.lastIndexOf("-")
        return getResult(str.substring(0, index))!! - getResult(str.substring(index + 1))!!
    }
    if (str.contains("*")) {
        val index = str.lastIndexOf("*")
        return getResult(str.substring(0, index))!! * getResult(str.substring(index + 1))!!
    }
    if (str.contains("/")) {
        val index = str.lastIndexOf("/")
        return getResult(str.substring(0, index))!! / getResult(str.substring(index + 1))!!
    }
    // 出错
    return null
}