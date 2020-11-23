package com.demo.swt.mystudyappshop.letcode


/**
 * introduce：here is introduce
 * author：sunwentao
 * email：wentao.sun@freebrio.com
 * data: 2020/10/22
 */
object addNumber {
    @JvmStatic
    fun main(args: Array<String>) {
        println(twoSum(intArrayOf(1, 3, 2, 7, 12), 9).contentToString())
        val node11 = ListNode(2)
        val node12 = ListNode(4)
        val node13 = ListNode(3)
        val node21 = ListNode(5)
        val node22 = ListNode(6)
        val node23 = ListNode(4)
        node11.next = node12
        node12.next = node13
        node21.next = node22
        node22.next = node23
        val newNode = addTwoNumbers(node11, node21)
        println("最大字符长度为" + lengthOfLongestSubstring("sbcsshsdskfg"))
    }

    /**
     * 俩数相加，寻找指定的数字，返回指定数字在数组中的下标
     */
    fun twoSum(nums: IntArray, target: Int): IntArray {
        var map = hashMapOf<Int, Int>()
        map[nums[0]] = 0
        for (i in 1..nums.size) {
            val other = target - nums[i]
            if (map.containsKey(other)) {
                return intArrayOf(map[other]!!, i)
            }
            map[nums[i]] = i
        }
        return intArrayOf(0, 0)
    }

    /**
     * 俩个链表相加，返回一个新的链表
     */
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var l1 = l1
        var l2 = l2
        var head: ListNode? = null
        var tail: ListNode? = null
        var carry = 0
        while (l1 != null || l2 != null) {
            val n1 = l1?.`val` ?: 0
            val n2 = l2?.`val` ?: 0
            val sum = n1 + n2 + carry
            if (head == null) {
                tail = ListNode(sum % 10)
                head = tail
            } else {
                tail?.next = ListNode(sum % 10)
                tail = tail?.next
            }
            carry = sum / 10
            //节点后移动
            if (l1 != null) {
                l1 = l1.next
            }
            if (l2 != null) {
                l2 = l2.next
            }
        }
        //判断最后一个节点
        if (carry > 0) {
            tail?.next = ListNode(carry)
        }
        return head
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }


    /**
     * 寻找一个字符串中最大长度的字符序列
     */
    fun lengthOfLongestSubstring(s: String): Int {
        // 哈希集合，记录每个字符是否出现过
        val occ: MutableSet<Char> = HashSet()
        val n = s.length
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        var rk = -1
        var ans = 0
        for (i in 0 until n) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s[i - 1])
            }
            while (rk + 1 < n && !occ.contains(s[rk + 1])) {
                // 不断地移动右指针
                occ.add(s[rk + 1])
                ++rk
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1)
        }
        return ans
    }


}