package com.davidshibru.taskflow.feature.signup.presentation

import org.junit.Test

class PresentationTest {
    @Test
    fun `initial test`() {
        strStr("mississippi", "issipi")
    }

    fun strStr(haystack: String, needle: String): Int {
        var i = 0

        if (haystack.length < needle.length) {
            return -1
        }

        while (i < haystack.length) {
            if(i + needle.length > haystack.length) return -1

            if(haystack[i] == needle[0]) {
                var same = true
                for(k in needle.indices) {

                    if(haystack[i + k] != needle[k]) {
                        same = false
                        break
                    }
                }
                if(same) {
                    return i
                }
            }


            i++
        }

        return -1
    }

    fun removeElement(nums: IntArray, `val`: Int): Int {
        var l = 0
        var r = 0

        while (r <= nums.size - 1) {
            val le = nums[l]
            val ri = nums[r]
            if (le != `val` && ri != `val`) {
                l++
                r++
            } else if ( le == `val` && ri == `val` ) {
                r++
            } else if (le == `val`) {
                nums[l] = nums[r]
                nums[r] = `val`
                l++
                r = l
            }
        }

        return l
    }

    fun removeDuplicates(nums: IntArray): Int {
        var l = 0
        var r = 0

        while (r < nums.size) {

            val left = nums[l]
            val right = nums[r]

            if (left < right) {
                nums[l + 1] = right
                l++
                r++
            } else {
                r++
            }
        }

        return l + 1
    }
    fun removeDuplicates1(nums: IntArray): Int {
        var l = 0
        var r = 0
        var count = 0


        while (l < nums.size) {
            if (r == nums.size - 1) {
                count++
                break
            }

            val left = nums[l]
            val right = nums[r]

            if (left == right) {
                r++
            } else {
                count++
                l = r
            }
        }

        return count
    }

    fun longestPalindrome(s: String): String {
        var pL = 0
        var pLen = 0

        var window = 1

        var l = 0

        if (s.length == 1) {
            return s
        }

        if (s.length == 2 && s[0] == s[1]) {
            return s
        }

        do {
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
        } while (window < s.length - 1)

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

    fun testPalindrome() {
        val list = listOf(
            "Слова",
            "палиндромы",
            "это",
            "уникальные",
            "слова ",
            "или",
            "фразы,",
            "читающиеся",
            "одинаково",
            "дед",
            "поп",
            "пуп",
            "дед",
            "шаш",
            "кок",
            "око",
            "баб",
            "мкм",
            "рвр",
            "воров",
            "наган",
            "радар",
            "ротор",
            "топот",
        )

        println("\n\n\n\n")
        for (word in list) {
            println("$word - ${isPalindrome(0, word.length - 1, word)}")
        }
    }
}