package com.demo.swt.mystudyappshop.letcode


/**
 * introduce：here is introduce
 * author：sunwentao
 * email：wentao.sun@freebrio.com
 * data: 2020/11/25
 */

object FindMidd {
    @JvmStatic
    fun main(args: Array<String>) {
        print(findMedianSortedArrays(intArrayOf(1, 3, 5, 7, 9), intArrayOf(2, 4, 6, 8, 10)))
    }

    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        if (nums1.size > nums2.size) {
            return findMedianSortedArrays(nums2, nums1)
        }
        val m = nums1.size
        val n = nums2.size
        var left = 0
        var right = m
        var ansi = -1
        // median1：前一部分的最大值
        // median2：后一部分的最小值
        var median1 = 0
        var median2 = 0
        while (left <= right) {
            // 前一部分包含 nums1[0 .. i-1] 和 nums2[0 .. j-1]
            // 后一部分包含 nums1[i .. m-1] 和 nums2[j .. n-1]
            val i = (left + right) / 2
            val j = (m + n + 1) / 2 - i

            // nums_im1, nums_i, nums_jm1, nums_j 分别表示 nums1[i-1], nums1[i], nums2[j-1], nums2[j]
            val nums_im1 = if (i == 0) Int.MIN_VALUE else nums1[i - 1]
            val nums_i = if (i == m) Int.MAX_VALUE else nums1[i]
            val nums_jm1 = if (j == 0) Int.MIN_VALUE else nums2[j - 1]
            val nums_j = if (j == n) Int.MAX_VALUE else nums2[j]
            if (nums_im1 <= nums_j) {
                ansi = i
                median1 = Math.max(nums_im1, nums_jm1)
                median2 = Math.min(nums_i, nums_j)
                left = i + 1
            } else {
                right = i - 1
            }
        }
        return if ((m + n) % 2 == 0) (median1 + median2) / 2.0 else median1.toDouble()
    }


}