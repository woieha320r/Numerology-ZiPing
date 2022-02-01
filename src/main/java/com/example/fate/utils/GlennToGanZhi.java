package com.example.fate.utils;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * GlennToGanZhi
 *
 * @author xiamu
 * @version 1.0
 * @date 2022/1/26 19:43
 */
public class GlennToGanZhi {

    private static JSONObject currYearJieQiInfo;
    private static final String DATE_TIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * 干、支、节气、时辰
     */
    private static final List<String> GAN = Arrays.asList("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸");
    private static final List<String> ZHI = Arrays.asList("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥");
    private static final List<String> JIE_QI = Arrays.asList("立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至", "小寒", "大寒");
    private static final JSONObject SHI_CHEN = JSONUtil.createObj();
    private static LocalTime SHI_CHEN_PREV_TIME = LocalTime.of(23, 0, 0);

    static {
        ZHI.forEach(zhi -> {
            SHI_CHEN.putOnce(zhi, SHI_CHEN_PREV_TIME.format(TIME_FORMATTER));
            SHI_CHEN_PREV_TIME = SHI_CHEN_PREV_TIME.plusHours(2L);
        });
    }

    /**
     * 格列历元年是辛酉年
     */
    private static final String GLENN_YUAN_YEAR_GAN = "辛";
    private static final String GLENN_YUAN_YEAR_ZHI = "酉";
    /**
     * 格列历1900年1月1日是甲戌日，新的一天按子时起
     */
    private static final LocalDateTime GLENN_FLAG_DATE_BEGIN = LocalDateTime.of(1899, 12, 31, 23, 0, 0);
    private static final LocalDateTime GLENN_FLAG_DATE_END = LocalDateTime.of(1900, 1, 1, 23, 0, 0);
    private static final String GLENN_FLAG_DAY_GAN = "甲";
    private static final String GLENN_FLAG_DAY_ZHI = "戌";

    /**
     * 以日干起子时干，五鼠遁
     * 以年干起寅月干，五虎遁
     */
    private static final Map<String, String> WU_SHU_DUN = MapUtil.builder(new HashMap<String, String>())
            .put("甲", "甲")
            .put("己", "甲")
            .put("乙", "丙")
            .put("庚", "丙")
            .put("丙", "戊")
            .put("辛", "戊")
            .put("丁", "庚")
            .put("壬", "庚")
            .put("戊", "壬")
            .put("癸", "壬")
            .build();
    private static final Map<String, String> WU_HU_DUN = MapUtil.builder(new HashMap<String, String>())
            .put("甲", "丙")
            .put("己", "丙")
            .put("乙", "戊")
            .put("庚", "戊")
            .put("丙", "庚")
            .put("辛", "庚")
            .put("丁", "壬")
            .put("壬", "壬")
            .put("戊", "甲")
            .put("癸", "甲")
            .build();

    public static String convert(LocalDateTime glennTime) {
        currYearJieQiInfo = getCurrYearJieQiInfo(glennTime.getYear());
        String year = getYear(glennTime) + "年";
        String month = getMonth(glennTime, year.split("年")[0].substring(0, 1)) + "月";
        String day = getDay(glennTime) + "日";
        String hour = getHour(glennTime, day.split("日")[0].substring(0, 1)) + "时";
        return year + month + day + hour;
    }

    /**
     * 从json文件解析出当年的节气信息
     */
    private static JSONObject getCurrYearJieQiInfo(int year) {
        return (JSONObject) JSONUtil.parseArray(FileUtil.readString("/Users/xiamu/Documents/Projects/Fate/src/main/resources/jieQi.json", StandardCharsets.UTF_8))
                .parallelStream()
                .filter(obj -> {
                    JSONObject jsonObject = (JSONObject) obj;
                    return Objects.equals(jsonObject.getInt("年份"), year);
                }).findAny().orElse(new JSONObject());
    }

    /**
     * 立春之后为本年，立春之前为前一年
     */
    private static String getYear(LocalDateTime glennTime) {
        int diffYear = glennTime.getYear() - 1;
        if (glennTime.isBefore(LocalDateTimeUtil.parse(currYearJieQiInfo.getStr(JIE_QI.get(0)), DATE_TIME_FORMAT_STR))) {
            diffYear--;
            //把节气对象换成上一年的
            currYearJieQiInfo = getCurrYearJieQiInfo(glennTime.getYear() - 1);
        }
        return GAN.get((GAN.indexOf(GLENN_YUAN_YEAR_GAN) + diffYear) % 10) + ZHI.get((ZHI.indexOf(GLENN_YUAN_YEAR_ZHI) + diffYear) % 12);
    }

    /**
     * 若恰好为交节时间，则按下一个节处理
     * 得到下一个节，得到本节，得到地支，五虎遁得到寅月干支，按六十甲子向下排列与寅月相隔的月数
     */
    private static String getMonth(LocalDateTime glennTime, String yearGan) {
        //找到生日的下一个节：自出生后所有节气中距离生日最近的那个，没有的话就说明是最后一个节，下一个就是立春
        Map<Integer, String> jieAfterDays = new HashMap<>();
        currYearJieQiInfo.keySet().forEach(key -> {
            boolean isJie = JIE_QI.indexOf(key) % 2 == 0;
            if (!isJie) {
                jieAfterDays.put(-1, key);
                return;
            }
            LocalDateTime jieStartTime = LocalDateTimeUtil.parse(currYearJieQiInfo.getStr(key), DATE_TIME_FORMAT_STR);
            boolean isNextJie = jieStartTime.isAfter(glennTime);
            if (!isNextJie) {
                jieAfterDays.put(-1, key);
                return;
            }
            jieAfterDays.put((int) LocalDateTimeUtil.between(glennTime, jieStartTime, ChronoUnit.DAYS), key);
            //若没有，表示其在本年最后一个节之后出生，下一个节为立春
        });
        Optional<Integer> nextJieKey = jieAfterDays.keySet().stream()
                .filter(val -> val >= 0)
                .min(Integer::compare);
        String jieAfterTime = nextJieKey.isPresent() ? jieAfterDays.get(nextJieKey.get()) : "立春";
        //得到下一个节对应的地支，再得到上一个地支，就是当月的地支
        int monthZhiIndex = ((JIE_QI.indexOf(jieAfterTime) / 2) + 2 + 11) % 12;
        int diffMonth = monthZhiIndex - ZHI.indexOf("寅");
        if (diffMonth < 0) {
            diffMonth = ((-diffMonth) * 11) % 12;
        }
        //当年寅月的天干
        int yinMonthGanIndex = GAN.indexOf(WU_HU_DUN.get(yearGan));
        return GAN.get((yinMonthGanIndex + diffMonth) % 10) + ZHI.get(monthZhiIndex);
    }

    /**
     * 子时算第二天
     * 以1900.01.01甲戌日为界（哪一天为界无所谓）
     * 按六十甲子向前或向后排列相隔的24小时个数，注意23:00是一天的界限
     */
    private static String getDay(LocalDateTime glennTime) {
        //满24小时算一天
        if (glennTime.isAfter(GLENN_FLAG_DATE_BEGIN)) {
            long diffDays = LocalDateTimeUtil.between(GLENN_FLAG_DATE_BEGIN, glennTime, ChronoUnit.DAYS);
            return GAN.get((int) ((GAN.indexOf(GLENN_FLAG_DAY_GAN) + diffDays) % 10)) + ZHI.get((int) ((ZHI.indexOf(GLENN_FLAG_DAY_ZHI) + diffDays) % 12));
        } else {
            long diffDays = LocalDateTimeUtil.between(glennTime, GLENN_FLAG_DATE_END, ChronoUnit.DAYS);
            if (Objects.equals(glennTime.getHour(), 23)) {
                diffDays--;
            }
            return GAN.get((int) ((GAN.indexOf(GLENN_FLAG_DAY_GAN) + (diffDays * 9)) % 10)) + ZHI.get((int) ((ZHI.indexOf(GLENN_FLAG_DAY_ZHI) + (diffDays * 11)) % 12));
        }
    }

    /**
     * 获取时干，交接点按下一个算
     */
    private static String getHour(LocalDateTime glennTime, String dayGan) {
        int hourZhiIndex = ZHI.indexOf(
                SHI_CHEN.keySet().stream().filter(key -> {
                    long diffMinutes = LocalDateTimeUtil.between(LocalTime.parse(SHI_CHEN.getStr(key), TIME_FORMATTER).atDate(glennTime.toLocalDate()), glennTime, ChronoUnit.MINUTES);
                    return diffMinutes >= 0 && diffMinutes < 120;
                }).findAny().orElse(null)
        );
        int ziHourGanIndex = GAN.indexOf(WU_SHU_DUN.get(dayGan));
        return GAN.get((ziHourGanIndex + hourZhiIndex) % 10) + ZHI.get(hourZhiIndex);
    }

}
