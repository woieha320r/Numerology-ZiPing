package com.example.fate.global;

import java.util.ArrayList;
import java.util.List;

/**
 * 五行（木火土金水）
 * 生克关系已被规定
 *
 * @author xiamu
 * @version 1.0
 * @date 2022/1/16 22:21
 */
@SuppressWarnings("unused")
public class TheFiveElements {

    /**
     * 相生关系环
     */
    private static final List<String> THE_FIVE_ELEMENTS;

    // 初始化相生关系环
    static {
        THE_FIVE_ELEMENTS = new ArrayList<>();
        THE_FIVE_ELEMENTS.add("M");
        THE_FIVE_ELEMENTS.add("H");
        THE_FIVE_ELEMENTS.add("T");
        THE_FIVE_ELEMENTS.add("J");
        THE_FIVE_ELEMENTS.add("S");
    }

    /**
     * 其在相生关系环中的位置
     */
    private final int index;

    /**
     * 私有构造器
     */
    private TheFiveElements(String flag) {
        this.index = THE_FIVE_ELEMENTS.indexOf(flag);
    }

    /**
     * 木
     */
    public static TheFiveElements mu() {
        return new TheFiveElements("M");
    }

    /**
     * 火
     */
    public static TheFiveElements huo() {
        return new TheFiveElements("H");
    }

    /**
     * 土
     */
    public static TheFiveElements tu() {
        return new TheFiveElements("T");
    }

    /**
     * 金
     */
    public static TheFiveElements jin() {
        return new TheFiveElements("J");
    }

    /**
     * 水
     */
    public static TheFiveElements shui() {
        return new TheFiveElements("S");
    }

    /**
     * 克我者
     */
    public TheFiveElements suppressMe() {
        return new TheFiveElements(THE_FIVE_ELEMENTS.get((index + 3) % 5));
    }

    /**
     * 我克者
     */
    public TheFiveElements iSuppress() {
        return new TheFiveElements(THE_FIVE_ELEMENTS.get((index + 2) % 5));
    }

    /**
     * 生我者
     */
    public TheFiveElements encourageMe() {
        return new TheFiveElements(THE_FIVE_ELEMENTS.get((index + 4) % 5));
    }

    /**
     * 我生者
     */
    public TheFiveElements iEncourage() {
        return new TheFiveElements(THE_FIVE_ELEMENTS.get((index + 1) % 5));
    }

}
