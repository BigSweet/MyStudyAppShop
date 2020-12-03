package com.demo.swt.mystudyappshop.letcode


/**
 * introduce：here is introduce
 * author：sunwentao
 * email：wentao.sun@freebrio.com
 * data: 2020/12/2
 */
object Solution {
    @JvmStatic
    fun main(args: Array<String>) {
        print(myAtoi(" -42"))
    }

    fun myAtoi(str: String): Int {
        val automaton = Automaton()
        val length = str.length
        for (i in 0 until length) {
            automaton[str[i]]
        }
        return (automaton.sign * automaton.ans).toInt()
    }

    class Automaton {
        var sign = 1
        var ans: Long = 0
        private var state = "start"
        private val table: Map<String, Array<String>> = object : HashMap<String, Array<String>>() {
            init {
                put("start", arrayOf("start", "signed", "in_number", "end"))
                put("signed", arrayOf("end", "end", "in_number", "end"))
                put("in_number", arrayOf("end", "end", "in_number", "end"))
                put("end", arrayOf("end", "end", "end", "end"))
            }
        }

        operator fun get(c: Char) {
            state = table[state]!![get_col(c)]
            if ("in_number" == state) {
                ans = ans * 10 + (c - '0')
                ans = if (sign == 1) Math.min(ans, Int.MAX_VALUE.toLong()) else Math.min(ans, -(Int.MIN_VALUE).toLong())
            } else if ("signed" == state) {
                sign = if (c == '+') 1 else -1
            }
        }

        private fun get_col(c: Char): Int {
            if (c == ' ') {
                return 0
            }
            if (c == '+' || c == '-') {
                return 1
            }
            return if (Character.isDigit(c)) {
                2
            } else 3
        }
    }


}