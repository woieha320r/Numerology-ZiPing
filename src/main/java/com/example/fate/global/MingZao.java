package com.example.fate.global;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 命造：生辰的甲子历表示
 *
 * @author xiamu
 * @date 2022-02-10
 */
@Data
@Accessors(chain = true)
public class MingZao {

    private JiaZi year;
    private JiaZi month;
    private JiaZi day;
    private JiaZi hour;

    public static MingZao get(LocalDateTime glennBirthday) {
        JiaZi year = JiaZi.getYear(glennBirthday);
        JiaZi month = JiaZi.getMonth(glennBirthday, year.getGan());
        JiaZi day = JiaZi.getDay(glennBirthday);
        JiaZi hour = JiaZi.getHour(glennBirthday, day.getGan());
        return new MingZao()
                .setYear(year)
                .setMonth(month)
                .setDay(day)
                .setHour(hour);
    }

    @Override
    public String toString() {
        return year.getGan().getName() + year.getZhi().getName() + "年"
                + month.getGan().getName() + month.getZhi().getName() + "月"
                + day.getGan().getName() + day.getZhi().getName() + "日"
                + hour.getGan().getName() + hour.getZhi().getName() + "时";
    }

}
