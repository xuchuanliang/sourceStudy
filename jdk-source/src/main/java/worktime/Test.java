package worktime;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Test {
//    private static List<String> exclude = Arrays.asList("徐传良", "何澄钟", "黄立琼", "孙富鑫", "林志亮", "林寿露");
    private static List<String> exclude = Collections.EMPTY_LIST;

    public static void main(String[] args) throws IOException {
        List<Person> objects = EasyExcel.read("C:\\Users\\xuchuanliangbt\\Desktop\\杭州富阳海康保泰安防技术服务有限公司_考勤报表_20220501-20220531.xlsx").head(Person.class).doReadAllSync();
        calculate(objects);
        Map<String, List<Person>> listMap = objects.stream().filter(p -> StrUtil.isNotEmpty(p.getEndTime())).collect(Collectors.groupingBy(Person::getName));
//        writeToTxt(listMap);
        writeToExcel(objects);
    }

    /**
     * 计算每一天的加班时长
     *
     * @param people
     */
    private static void calculate(List<Person> people) {
        people.forEach(p -> {
            if (StrUtil.isNotEmpty(p.getData()) && StrUtil.isNotEmpty(p.getEndTime())) {
                boolean flag = false;
                String endTime = p.getEndTime();
                if (endTime.startsWith("次日")) {
                    endTime = endTime.replace("次日 ", "");
                    flag = true;
                }
                endTime.trim();
                String[] split = endTime.split(":");
                int hour = Integer.parseInt(split[0]);
                if (flag) {
                    //凌晨打卡，+24
                    hour += 24;
                }
                int min = Integer.parseInt(split[1]);
                int totalMin = 0;
                if (hour > 17) {
                    totalMin += (hour - 17) * 60;
                }
                if (min > 0) {
                    totalMin += min;
                }
                p.setDuration(totalMin);
                p.setDurationHour(NumberUtil.div(p.getDuration(),60,2));
            }
        });
    }

    /**
     * 写到txt文件中
     *
     * @param listMap
     */
    private static void writeToTxt(Map<String, List<Person>> listMap) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("C:\\Users\\xuchuanliangbt\\Desktop\\" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN) + "_detail.txt")));
        BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(new File("C:\\Users\\xuchuanliangbt\\Desktop\\" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN) + "_total.txt")));
        listMap.entrySet().stream().filter(l -> !exclude.contains(l.getKey())).forEach(e -> {
            try {
                String name = e.getKey();
                List<Person> value = e.getValue();
                int totalDuration = value.stream().mapToInt(Person::getDuration).sum();
                String s = "姓名：" + name + "；加班总时长：" + totalDuration + "分钟；合计：" + NumberUtil.div(totalDuration, 60, 2) + "小时";
                bufferedWriter.write(s);
                bufferedWriter.write("\r\n");
                bufferedWriter2.write(s);
                bufferedWriter2.write("\r\n");
                bufferedWriter.write("每日加班明细如下：\r\n");
                for (Person p : value) {
                    s = "姓名：" + name + "；日期：" + p.getData() + "：下班打卡时间：" + p.getEndTime() + "；加班时长：" + p.getDuration() + "分钟\r\n";
                    bufferedWriter.write(s);
                }
                bufferedWriter.write("\r\n");
                bufferedWriter2.write("\r\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        bufferedWriter.flush();
        bufferedWriter.close();
        bufferedWriter2.flush();
        bufferedWriter2.close();
    }

    /**
     * 写到excel中
     *
     * @param list
     */
    private static void writeToExcel(List<Person> list) {
        List<Person> collect = list.stream().filter(p -> !exclude.contains(p.getName())).filter(p -> p.getDuration() != 0).collect(Collectors.toList());
        String fileName = "C:\\Users\\xuchuanliangbt\\Desktop\\" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN) + "_detail.xlsx";
        EasyExcel.write(fileName, Person.class).sheet("数据").doWrite(collect);

        Map<String, List<Person>> listMap = list.stream().filter(p -> StrUtil.isNotEmpty(p.getEndTime())).collect(Collectors.groupingBy(Person::getName));
        List<PersonTotal> personTotals = new ArrayList<>();
        listMap.entrySet().forEach(e->{
            PersonTotal p = new PersonTotal();
            p.setName(e.getKey());
            p.setDay(e.getValue().size());
            p.setTotalDuration(e.getValue().stream().mapToInt(Person::getDuration).sum());
            p.setTotalDurationHour(NumberUtil.div(p.getTotalDuration(),60,2));
            personTotals.add(p);
        });
        fileName = "C:\\Users\\xuchuanliangbt\\Desktop\\" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN) + "_total.xlsx";
        EasyExcel.write(fileName, PersonTotal.class).sheet("数据").doWrite(personTotals);
    }
}
