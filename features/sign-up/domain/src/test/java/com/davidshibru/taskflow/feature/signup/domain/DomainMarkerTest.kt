package com.davidshibru.taskflow.feature.signup.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class DomainMarkerTest {
    @Test
    fun `initial test`() {

        println("Test should pass"[0])
        // c
        // шалаш
        // lcr  -> x
        // шалаш
        //  lcr -> o / c - 2 l - 3
        // шалаш
        // l  c
        // шалаш
        // cr
        // шалаш

        val res = longestPalindrome("ccc")

        println("\n\n\n\n$res\n\n\n\n")

        assertEquals("Test should pass", 4, 2 + 2)
    }

    fun longestPalindrome(s: String): String {
        var pL = 0
        var pLen = 0

        var window = 1

        var l = 0

        if (s.length == 2 && s[0] == s[1]) {
            return s
        }

        while (window < s.length - 1) {
            if (isPalindrome(l, window, s)) {
                pL = l
                l = 0
                pLen = window
                window++
            } else {
                l++
            }

            if (l + window > s.length - 1) {
                l = 0
                window++
            }
        }

        if (pLen == 0) {
            return s[0].toString()
        }

        return s.substring(pL, pL + pLen + 1)
    }

    fun isPalindrome(l: Int, window: Int, s: String): Boolean {
        var left = l
        var right = window + l

        while (left <= right) {
            if (s[left] != s[right]) {
                return false
            }

            left++
            right--
        }

        return true
    }
}