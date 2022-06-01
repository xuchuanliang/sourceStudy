package worktime;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class PersonTotal{
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("总加班时长（分钟）")
    private int totalDuration;
    @ExcelProperty("总加班时长（小时）")
    private double totalDurationHour;
    @ExcelProperty("总加班天数")
    private int day;
}
