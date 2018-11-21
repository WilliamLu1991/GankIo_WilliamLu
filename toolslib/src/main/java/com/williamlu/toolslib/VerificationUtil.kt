package com.williamlu.toolslib

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.TextUtils
import java.text.ParseException
import java.util.*
import java.util.regex.Pattern

/**
 * @Author: WilliamLu
 * @Date: 2018/11/21
 * @Description:手机号、邮箱、身份证有效性检测类
 */
object VerificationUtil {
    // 判断手机号码是否有效
    fun isValidTelNumber(telNumber: String): Boolean {
        if (!TextUtils.isEmpty(telNumber)) {
            val regex = "(\\+\\d+)?1[345678]\\d{9}$"
            return Pattern.matches(regex, telNumber)
        }

        return false
    }

    // 判断邮箱地址是否有效
    fun isValidEmailAddress(emailAddress: String): Boolean {

        if (!TextUtils.isEmpty(emailAddress)) {
            val regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"
            return Pattern.matches(regex, emailAddress)
        }

        return false
    }

    // 判断内容是否由字母，数字，下划线组成
    fun isValidContent(content: String): Boolean {

        if (!TextUtils.isEmpty(content)) {
            val regex = "^[\\w\\u4e00-\\u9fa5]+$"
            return Pattern.matches(regex, content)
        }

        return false
    }

    // 判断身份证号码是否有效
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun isValidIdCard(idCard: String): Boolean {
        return IdcardValidator.isValidateIdcard(idCard)
    }

    // 校验身份证的基本组成
    fun isIdcard(idCard: String): Boolean {
        if (!TextUtils.isEmpty(idCard)) {
            val regex = "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)"
            return Pattern.matches(regex, idCard)
        }

        return false
    }

    // 校验15身份证的基本组成
    fun is15Idcard(idCard: String): Boolean {
        if (!TextUtils.isEmpty(idCard)) {
            val regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$"
            return Pattern.matches(regex, idCard)
        }

        return false
    }

    // 校验18身份证的基本组成
    fun is18Idcard(idCard: String): Boolean {
        if (!TextUtils.isEmpty(idCard)) {
            val regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$"
            return Pattern.matches(regex, idCard)
        }

        return false
    }

    /**
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * <p>
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * <p>
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
     * <p>
     * 第十八位数字(校验码)的计算方法为：
     * 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * 2.将这17位数字和系数相乘的结果相加。
     * 3.用加出来和除以11，看余数是多少？
     * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为: 1 0 X 9 8 7 6 5 4 3 2;
     * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     */
    private object IdcardValidator {

        // 省,直辖市代码表
        private val codeAndCity = arrayOf(arrayOf("11", "北京"), arrayOf("12", "天津"), arrayOf("13", "河北"), arrayOf("14", "山西"), arrayOf("15", "内蒙古"), arrayOf("21", "辽宁"), arrayOf("22", "吉林"), arrayOf("23", "黑龙江"), arrayOf("31", "上海"), arrayOf("32", "江苏"), arrayOf("33", "浙江"), arrayOf("34", "安徽"), arrayOf("35", "福建"), arrayOf("36", "江西"), arrayOf("37", "山东"), arrayOf("41", "河南"), arrayOf("42", "湖北"), arrayOf("43", "湖南"), arrayOf("44", "广东"), arrayOf("45", "广西"), arrayOf("46", "海南"), arrayOf("50", "重庆"), arrayOf("51", "四川"), arrayOf("52", "贵州"), arrayOf("53", "云南"), arrayOf("54", "西藏"), arrayOf("61", "陕西"), arrayOf("62", "甘肃"), arrayOf("63", "青海"), arrayOf("64", "宁夏"), arrayOf("65", "新疆"), arrayOf("71", "台湾"), arrayOf("81", "香港"), arrayOf("82", "澳门"), arrayOf("91", "国外"))

        // 每位加权因子
        private val power = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)

        // 判断18位身份证号码是否有效
        private fun isValidate18Idcard(idcard: String): Boolean {
            if (idcard.length != 18) {
                return false
            }
            val idcard17 = idcard.substring(0, 17)
            val idcard18Code = idcard.substring(17, 18)
            val c: CharArray?
            val checkCode: String?
            if (isDigital(idcard17)) {
                c = idcard17.toCharArray()
            } else {
                return false
            }

            if (null != c) {
                val bit = converCharToInt(c)
                val sum17 = getPowerSum(bit)

                // 将和值与11取模得到余数进行校验码判断
                checkCode = getCheckCodeBySum(sum17)
                if (null == checkCode) {
                    return false
                }
                // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
                // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
                if (!idcard18Code.equals(checkCode, ignoreCase = true)) {
                    return false
                }
            }

            return true
        }

        // 将15位的身份证转成18位身份证
        @RequiresApi(api = Build.VERSION_CODES.N)
        fun convertIdcarBy15bit(idcard: String): String? {
            var idcard18: String? = null
            if (idcard.length != 15) {
                return null
            }

            if (isDigital(idcard)) {
                // 获取出生年月日
                val birthday = idcard.substring(6, 12)
                var birthdate: Date? = null
                try {
                    birthdate = SimpleDateFormat("yyMMdd").parse(birthday)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                val cday = Calendar.getInstance()
                cday.time = birthdate!!
                val year = cday.get(Calendar.YEAR).toString()

                idcard18 = idcard.substring(0, 6) + year + idcard.substring(8)

                val c = idcard18.toCharArray()
                var checkCode: String? = ""

                if (null != c) {
                    val bit = converCharToInt(c)
                    val sum17: Int
                    sum17 = getPowerSum(bit)
                    // 获取和值与11取模得到余数进行校验码
                    checkCode = getCheckCodeBySum(sum17)
                    // 获取不到校验位
                    if (null == checkCode) {
                        return null
                    }

                    // 将前17位与第18位校验码拼接
                    idcard18 += checkCode
                }
            } else {
                return null
            }

            return idcard18
        }

        // 是否全部由数字组成
        fun isDigital(str: String?): Boolean {
            return if (str == null || "" == str) false else str.matches("^[0-9]*$".toRegex())
        }

        // 将身份证的每位和对应位的加权因子相乘之后，再得到和值
        fun getPowerSum(bit: IntArray): Int {
            var sum = 0
            if (power.size != bit.size) {
                return sum
            }

            for (i in bit.indices) {
                for (j in power.indices) {
                    if (i == j) {
                        sum = sum + bit[i] * power[j]
                    }
                }
            }

            return sum
        }

        // 将和值与11取模得到余数进行校验码判断
        private fun getCheckCodeBySum(sum17: Int): String? {
            var checkCode: String? = null
            when (sum17 % 11) {
                10 -> checkCode = "2"
                9  -> checkCode = "3"
                8  -> checkCode = "4"
                7  -> checkCode = "5"
                6  -> checkCode = "6"
                5  -> checkCode = "7"
                4  -> checkCode = "8"
                3  -> checkCode = "9"
                2  -> checkCode = "x"
                1  -> checkCode = "0"
                0  -> checkCode = "1"
            }

            return checkCode
        }

        // 将字符数组转为整型数组
        @Throws(NumberFormatException::class)
        private fun converCharToInt(c: CharArray): IntArray {
            val a = IntArray(c.size)
            var k = 0
            for (temp in c) {
                a[k++] = Integer.parseInt(temp.toString())
            }

            return a
        }

        // 验证身份证号码是否有效
        @RequiresApi(api = Build.VERSION_CODES.N)
        fun isValidateIdcard(idcard: String): Boolean {
            if (!TextUtils.isEmpty(idcard)) {
                if (idcard.length == 15) {
                    return isValidate18Idcard(convertIdcarBy15bit(idcard)!!)
                } else if (idcard.length == 18) {
                    return isValidate18Idcard(idcard)
                }
            }

            return false
        }
    }
}