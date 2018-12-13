package com.williamlu.toolslib

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author: WilliamLu
 * @Date: 2018/11/20
 * @Description: 
 */
object DateUtils {

    val DATE_YYYYMMDD = "yyyy-MM-dd"
    val DATE_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss"
    val DATE_YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss SSS"
    val DATE_VISUAL14FORMAT = "yyyy-MM-dd HH:mm:ss"
    val LOCALE = Locale.SIMPLIFIED_CHINESE
    private var last_format_type = ""
    private var last_format: SimpleDateFormat? = null

    fun getTimeString(time: String): String {
        if (TextUtils.isEmpty(time)) {
            return ""
        }
        try {
            val simpleDateFormat = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", LOCALE)
            val date = simpleDateFormat.parse(time)
            val formTime = date.getTime()
            var distance = (System.currentTimeMillis() - formTime) / 1000
            val timestamp: String
            if (distance < 0)
                distance = 0
            if (distance < 60) {
                // timestamp = distance + "秒前";
                timestamp = "刚刚"
            } else if (distance < 60 * 60) {
                distance = distance / 60
                timestamp = distance.toString() + "分钟前"
            } else if (distance < 60 * 60 * 24) {
                distance = distance / 60 / 60
                timestamp = distance.toString() + "小时前"
            } else if (distance < 60 * 60 * 24 * 7) {
                distance = distance / 60 / 60 / 24
                timestamp = distance.toString() + "天前"
            } else if (distance < 60 * 60 * 24 * 29) {
                distance = distance / 60 / 60 / 24 / 7
                timestamp = distance.toString() + "周前"
            } else if (distance < 60 * 60 * 24 * 29 * 3) {
                distance = distance / 60 / 60 / 24 / 30
                timestamp = distance.toString() + "个月前"
            } else {
                timestamp = time
            }
            return timestamp
        } catch (e: Exception) {
        }

        return time
    }

    /**
     * 获取当前时间,返回的日期格式为[.DATE_VISUAL14FORMAT]
     * 2014-09-11 11:03:33
     * @return String
     */
    fun getCurrentTime(): String {
        return getCurrentDateTime(DATE_VISUAL14FORMAT)
    }

    /**
     * 获取当前时间的毫秒数
     *
     * @return long
     */
    fun getCurrentTimeInMills(): Long {
        return TimeCalibrate.getCurrentCalendar().time
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return long
     */
    fun date2TimeStamp(date_str: String, format: String): Long {
        try {
            val sdf = SimpleDateFormat(format, LOCALE)
            return sdf.parse(date_str).getTime()
        } catch (e: Exception) {
        }

        return 0L
    }

    fun string2Date(date: String): Date? {
        try {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", LOCALE).parse(date)
        } catch (e: Exception) {
        }

        return null
    }

    /**
     * 字符串的日期格式的计算
     *
     * @throws ParseException
     */
    @Throws(ParseException::class)
    fun daysBetween(smdate: String, bdate: String): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd", LOCALE)
        val cal = Calendar.getInstance()
        cal.time = sdf.parse(smdate)
        val time1 = cal.timeInMillis
        cal.time = sdf.parse(bdate)
        val time2 = cal.timeInMillis
        val between_days = (time2 - time1) / (1000 * 3600 * 24)

        return Integer.parseInt(between_days.toString())
    }

    @Throws(ParseException::class)
    fun timesBetween(smdate: String, bdate: String): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", LOCALE)
        val cal = Calendar.getInstance()
        cal.time = sdf.parse(smdate)
        val time1 = cal.timeInMillis
        cal.time = sdf.parse(bdate)
        val time2 = cal.timeInMillis
        val between_days = (time2 - time1) / (1000 * 60)

        return Integer.parseInt(between_days.toString())
    }

    fun isCloseEnough(time1: String, time2: String): Boolean {
        try {
            val simpleDateFormat = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", LOCALE)
            val date = simpleDateFormat.parse(time1)
            val formTime1 = date.getTime()
            val date2 = simpleDateFormat.parse(time2)
            val formTime2 = date2.getTime()
            val time = formTime2 - formTime1
            if (time / 1000 < 2 * 60) {
                return true
            }
        } catch (e: Exception) {

        }

        return false
    }

    fun getTimeSuffix(): String {
        val cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.get(Calendar.MONTH) + 1
        val mDay = cal.get(Calendar.DAY_OF_MONTH)
        var month = "" + mMonth
        if (mMonth < 10) {
            month = "0$mMonth"
        }
        var day = "" + mDay
        if (mDay < 10) {
            day = "0$mDay"
        }
        // int h = cal.get(Calendar.HOUR_OF_DAY);
        // int m = cal.get(Calendar.MINUTE);
        // int s = cal.get(Calendar.SECOND);
        // String hour = "" + h;
        // if (h < 10) {
        // hour = "0" + h;
        // }
        // String minute = "" + m;
        // if (m < 10) {
        // minute = "0" + m;
        // }
        // String second = "" + s;
        // if (s < 10) {
        // second = "0" + s;
        // }
        return mYear.toString() + month + day + System.currentTimeMillis()
    }

    fun formatDateWithDefault(time: String): Date? {
        return formatDate(time, "1970-01-01")
    }

    fun formatDate(time: String, defaultTime: String): Date? {
        return formatDate(time, DATE_VISUAL14FORMAT, defaultTime)
    }

    /**
     * 将String类型的时间转换为Date类型
     * 默认格式化时间为[.DATE_VISUAL14FORMAT]
     *
     * @param time        String
     * @param defaultTime String
     * @return Date
     */
    fun formatDate(time: String, timeFormat: String, defaultTime: String): Date? {
        var date: Date? = null
        var format: SimpleDateFormat
        format = SimpleDateFormat(timeFormat, LOCALE)
        try {
            date = format.parse(time)
        } catch (e: Exception) {
            format = SimpleDateFormat("yyyy-MM-dd", LOCALE)
            try {
                date = format.parse(time)
            } catch (e1: ParseException) {
                try {
                    date = format.parse(defaultTime)
                } catch (e2: ParseException) {
                    e2.printStackTrace()
                }

            }

        }

        return date
    }

    /**
     * 返回当前日期，格式为：yyyyMMDD
     *
     * @return String|当前日期
     */
    fun getCurrentDate(): String {
        return getCurrentDate(DATE_YYYYMMDD)
    }

    /**
     * 返回当前日期，格式为传入的format_type
     *
     * @param format_type String
     * @return String|当前日期
     */
    @Synchronized
    fun getCurrentDate(format_type: String): String {
        val currentDate = TimeCalibrate.getCurrentCalendar()
        if (!TextUtils.isEmpty(last_format_type)) {
            if (TextUtils.equals(last_format_type, format_type) && last_format != null) {
                return last_format!!.format(currentDate)
            }
        }
        val format = SimpleDateFormat(format_type, LOCALE)
        last_format_type = format_type
        last_format = format
        return format.format(currentDate)
    }

    /**
     * 格式化日期
     *
     * @param dateStr String
     * @param type    String
     * @return Date
     */
    fun getData(dateStr: String, type: String): Date? {
        val format = SimpleDateFormat(type, LOCALE)
        var date: Date? = null
        try {
            date = format.parse(dateStr)
        } catch (e: ParseException) {
        }

        return date
    }

    /**
     * 将日期从一种格式转化为另一种格式
     *
     * @param dateStr String
     * @param originFormat String
     * @param targetFormat String
     * @return String
     */
    fun formartDateStrToTarget(dateStr: String, originFormat: String, targetFormat: String): String {
        if (TextUtils.isEmpty(dateStr)) {
            return ""
        }
        var format = SimpleDateFormat(originFormat, LOCALE)
        var date = ""

        try {
            val dateOb = format.parse(dateStr)
            format = SimpleDateFormat(targetFormat, LOCALE)
            date = format.format(dateOb)
        } catch (e: ParseException) {
        }

        return date
    }

    /**
     * 返回当前的日期时间，格式为：yyyyMMDDHHmmss
     *
     * @return String|当前时间
     */
    fun getCurrentDateTime(): String {
        return getCurrentDate(DATE_YYYYMMDDHHMMSS)
    }

    fun getCurrentDateTime(format_type: String): String {
        return getCurrentDate(format_type)
    }

    /**
     * 计算第二个日期减去第一个日期的得到的天数，日期必须为[.DATE_YYYYMMDD]格式
     *
     * @param dateStr1 String|日期1，必须为yyyyMMDD格式
     * @param dateStr2 String|日期2，必须为yyyyMMDD格式
     * @return int|天
     */
    fun compareTwoDateResultDays(dateStr1: String, dateStr2: String): Int {
        return compareDate(dateStr1, dateStr2, DATE_YYYYMMDD).toInt() / (24 * 3600)
    }

    /**
     * 第二个日期减去第一个日期得到的秒数，日期必须为[.DATE_YYYYMMDDHHMMSS]格式
     *
     * @param dateStr1 String|日期1
     * @param dateStr2 String|日期2
     * @return long|秒
     */
    fun compareTwoDateTime(dateStr1: String, dateStr2: String): Long {
        return compareDate(dateStr1, dateStr2, DATE_YYYYMMDDHHMMSS)
    }

    /**
     * 第二个日期减去第一个日期，得到的差值
     *
     * @param dateStr1    String|日期1
     * @param dateStr2    String|日期2
     * @param format_type String|日期格式
     * @return long|如果传入的时间格式精确到毫秒，则返回毫秒；否则，返回到秒
     */
    fun compareDate(dateStr1: String, dateStr2: String, format_type: String): Long {
        return comareDateByLevel(dateStr1, dateStr2, format_type, format_type)
    }

    /**
     * 比较两个日期的相差值，支持比较Level
     *
     * @param dateStr1     String|日期1
     * @param dateStr2     String|日期2
     * @param format_type  String|源日期格式
     * @param level_format String|比较level日期格式
     * @return long|如果传入的时间格式精确到毫秒，则返回毫秒；否则，返回到秒
     */
    fun comareDateByLevel(dateStr1: String, dateStr2: String, format_type: String, level_format: String): Long {
        var format_type = format_type
        var level_format = level_format
        var minus: Long = 0
        if (TextUtils.isEmpty(format_type)) {
            format_type = DATE_YYYYMMDD
        }
        if (TextUtils.isEmpty(level_format)) {
            level_format = DATE_YYYYMMDD
        }
        if (!TextUtils.isEmpty(dateStr1) && !TextUtils.isEmpty(dateStr2) && dateStr1.length == dateStr2.length) {

            try {
                val df = SimpleDateFormat(format_type, LOCALE)
                val date1 = df.parse(dateStr1)
                val date2 = df.parse(dateStr2)

                val df_dest = SimpleDateFormat(level_format, LOCALE)
                val date1_dest = df_dest.parse(df_dest.format(date1))
                val date2_dest = df_dest.parse(df_dest.format(date2))
                minus = date2_dest.getTime() - date1_dest.getTime()
                if (dateStr1.length <= 14) {
                    minus = minus / 1000
                }
            } catch (e: ParseException) {
            }

        }
        return minus
    }

    /**
     * 传入的日期是否是合法日期,默认为[.DATE_YYYYMMDD]格式
     *
     * @param dateStr String | 日期的值
     * @return boolean | true:合法;false:不合法
     */
    fun isDateRight(dateStr: String): Boolean {
        return isDateRight(dateStr, DATE_YYYYMMDD)
    }

    /**
     * 传入的日期是否是合法日期
     *
     * @param dateStr     String | 日期的值
     * @param format_type String | 日期的格式
     * @return boolean | true:合法;false:不合法
     */
    fun isDateRight(dateStr: String, format_type: String): Boolean {
        var format_type = format_type
        var result = false
        if (!TextUtils.isEmpty(dateStr)) {
            if (TextUtils.isEmpty(format_type)) {
                format_type = DATE_YYYYMMDD
            }

            val df = SimpleDateFormat(format_type, LOCALE)
            try {
                val date = df.parse(dateStr)
                result = date != null
            } catch (e: ParseException) {
                result = false
            }

        }
        return result
    }

    /**
     * 当前日期是否为周末，日期必须为[.DATE_YYYYMMDD]
     *
     * @param dateStr String|[.DATE_YYYYMMDD]格式
     * @return boolean|true:是周末;false:不是周末
     */
    fun isDateWeekend(dateStr: String): Boolean {
        var result = false
        if (!TextUtils.isEmpty(dateStr) && dateStr.length == 8) {
            result = isDateWeekend(dateStr, DATE_YYYYMMDD)
        }
        return result
    }

    /**
     * 当前日期是否为周末
     *
     * @param dateStr     String|
     * @param format_type String|日期格式
     * @return boolean|true:是周末;false:不是周末
     */
    fun isDateWeekend(dateStr: String, format_type: String): Boolean {
        val day = getWeekDays(dateStr, format_type)
        return day == 6 || day == 7
    }

    /**
     * 当前日期是星期几，如果是星期一，就返回1，星期日就返回7，以此类推。
     * 如果日期格式不合法，则返回-1
     * @param dateStr String
     * @param format_type String
     * @return int | 如果是星期一，就返回1，星期日就返回7，以此类推。
     */
    fun getWeekDays(dateStr: String, format_type: String): Int {
        var result = false
        val df = SimpleDateFormat(format_type, Locale.SIMPLIFIED_CHINESE)
        var day = -1
        try {
            val date = df.parse(dateStr)
            val calendar = GregorianCalendar()
            if (date != null) {
                calendar.timeInMillis = date.time
                day = calendar.get(Calendar.DAY_OF_WEEK) - 1
                if (day == 0) {
                    day = 7
                }
            }
        } catch (e: ParseException) {
            result = false
        }

        return day
    }

    fun formatBirthday(birthday: String): String {
        if (!TextUtils.isEmpty(birthday)) {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val birthdayDate = string2Date(birthday)
            calendar.time = birthdayDate!!
            return (currentYear - calendar.get(Calendar.YEAR)).toString()
        }
        return ""
    }

    fun getLocalCalendar(): Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"))
    }

    /**
     * 判断是否为闰年
     */
    fun isLeapYear(year: Int): Boolean {
        if (year % 100 == 0 && year % 400 == 0) {
            return true
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true
        }
        return false
    }
}