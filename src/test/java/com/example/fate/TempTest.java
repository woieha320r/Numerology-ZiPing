package com.example.fate;

import com.example.fate.global.MingZao;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * TempTest
 *
 * @author xiamu
 * @version 2.0
 * @date 2022/02/10 09:31
 */
public class TempTest {

    @Test
    public void test() {
        System.out.println(MingZao.get(LocalDateTime.of(1800, 1, 1, 7, 0, 0)));
        System.out.println(MingZao.get(LocalDateTime.of(1800, 1, 1, 23, 0, 0)));
        System.out.println(MingZao.get(LocalDateTime.of(1800, 6, 12, 9, 0, 0)));
        System.out.println(MingZao.get(LocalDateTime.of(1899, 12, 31, 7, 0, 0)));
        System.out.println(MingZao.get(LocalDateTime.of(1899, 12, 31, 23, 0, 0)));
        System.out.println(MingZao.get(LocalDateTime.of(1900, 1, 1, 7, 0, 0)));
        System.out.println(MingZao.get(LocalDateTime.of(1900, 1, 1, 23, 0, 0)));
        System.out.println(MingZao.get(LocalDateTime.of(1900, 9, 30, 23, 0, 0)));
        System.out.println(MingZao.get(LocalDateTime.of(1999, 1, 23, 8, 50, 0)));
        /*
         * 结果当为
         * 己未年丙子月庚寅日庚辰时
         * 己未年丙子月辛卯日戊子时
         * 庚申年壬午月壬申日乙巳时
         * 己亥年丙子月癸酉日丙辰时
         * 己亥年丙子月甲戌日甲子时
         * 己亥年丙子月甲戌日戊辰时
         * 己亥年丙子月乙亥日丙子时
         * 庚子年乙酉月丁未日庚子时
         * 戊寅年乙丑月乙亥日庚辰时
         * */
    }

}
