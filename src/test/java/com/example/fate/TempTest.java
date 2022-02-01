package com.example.fate;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.example.fate.utils.GlennToGanZhi;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * TempTest
 *
 * @author xiamu
 * @version 1.0
 * @date 2022/2/1 09:31
 */
public class TempTest {

    @Test
    public void test() {
        System.out.println(LocalDateTimeUtil.between(
                LocalDateTime.of(2022, 2, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 31, 1, 0, 0),
                ChronoUnit.DAYS
        ));
        System.out.println(LocalDateTimeUtil.between(
                LocalDateTime.of(2022, 2, 1, 1, 0, 0),
                LocalDateTime.of(2022, 2, 1, 1, 59, 0),
                ChronoUnit.HOURS
        ));
    }

    @Test
    public void test2() {
        System.out.println(GlennToGanZhi.convert(LocalDateTime.of(1800, 1, 1, 7, 0, 0)));
        System.out.println(GlennToGanZhi.convert(LocalDateTime.of(1800, 1, 1, 23, 0, 0)));
        System.out.println(GlennToGanZhi.convert(LocalDateTime.of(1800, 6, 12, 9, 0, 0)));
        System.out.println(GlennToGanZhi.convert(LocalDateTime.of(1899, 12, 31, 7, 0, 0)));
        System.out.println(GlennToGanZhi.convert(LocalDateTime.of(1899, 12, 31, 23, 0, 0)));
        System.out.println(GlennToGanZhi.convert(LocalDateTime.of(1900, 1, 1, 7, 0, 0)));
        System.out.println(GlennToGanZhi.convert(LocalDateTime.of(1900, 1, 1, 23, 0, 0)));
        System.out.println(GlennToGanZhi.convert(LocalDateTime.of(1900, 9, 30, 23, 0, 0)));
        System.out.println(GlennToGanZhi.convert(LocalDateTime.of(1999, 1, 23, 8, 50, 0)));
    }

}
