package worktime;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Person {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("日期")
    private String data;
    @ExcelProperty("打卡时间")
    private String endTime;
    /**
     * 加班时长：分钟
     */
    @ExcelProperty("加班时长（分钟）")
    private int duration;

    @ExcelProperty("加班时长（小时）")
    private double durationHour;

}
