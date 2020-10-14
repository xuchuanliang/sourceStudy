package hkbt;

import com.github.houbb.heaven.util.lang.StringUtil;
import lombok.Data;
import lombok.ToString;

import java.io.*;
import java.util.*;

/**
 * 日志读取器
 */
public class TimeLongReader {

    private static Map<String,TimeVo>  map = new HashMap<>(100);

    public static void main(String[] args) {
        analyse();
    }

    public static void analyse(){
        String resultDir = "D:\\徐传良的文件\\海康保泰\\省厅保安系统\\1014\\log\\logs";
        try {
            doRead("D:\\徐传良的文件\\海康保泰\\省厅保安系统\\1014\\log\\logs\\alarm");
            doRead("D:\\徐传良的文件\\海康保泰\\省厅保安系统\\1014\\log\\logs\\approval");
            doRead("D:\\徐传良的文件\\海康保泰\\省厅保安系统\\1014\\log\\logs\\archives");
            doRead("D:\\徐传良的文件\\海康保泰\\省厅保安系统\\1014\\log\\logs\\exam");
            doRead("D:\\徐传良的文件\\海康保泰\\省厅保安系统\\1014\\log\\logs\\log");
            doRead("D:\\徐传良的文件\\海康保泰\\省厅保安系统\\1014\\log\\logs\\message");
            doRead("D:\\徐传良的文件\\海康保泰\\省厅保安系统\\1014\\log\\logs\\portal");
            doRead("D:\\徐传良的文件\\海康保泰\\省厅保安系统\\1014\\log\\logs\\report");
            doWrite(resultDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void doRead(String baseDir){
        File file = new File(baseDir);
        List<File> logFiles = new ArrayList<>();
        fillLogFiles(file,logFiles);
        if(null!=logFiles && logFiles.size()>0){
            logFiles.forEach(l->{
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(l));
                    String line = null;
                    while ((line=bufferedReader.readLine())!=null){
                        transferTimeVo(line);
                    }
                    bufferedReader.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void doWrite(String resultDir) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultDir+"\\"+"_result.txt"));
        map.values().stream().sorted(Comparator.comparingInt(TimeVo::getNum)).forEach(timeVo -> {
            try {
                bufferedWriter.write("方法名称："+timeVo.getName()+"\r\n总调用次数："
                        +timeVo.getNum()+"                 总花费时间："
                        +timeVo.getTotalTime()+"                 平均调用时间："
                        +timeVo.getAvg());
                bufferedWriter.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static void fillLogFiles(File file,List<File> logFiles){
        if(!file.isDirectory()){
            logFiles.add(file);
            return;
        }
        File[] files = file.listFiles();
        if(null!=file && files.length>0){
            for(File f:files){
                fillLogFiles(f,logFiles);
            }
        }
    }

    public static void transferTimeVo(String line){
        if(StringUtil.isNotEmpty(line) && line.contains("请求时间：")){
            String[] strings1 = line.split("请求时间：");
            String temp = strings1[1];
            String[] strings2 = temp.split("请求方法为：");
            String timeStr = strings2[0].split("；")[0];
            String nameStr = strings2[1];
            TimeVo timeVo = map.get(nameStr);
            boolean isNew = false;
            if(Objects.isNull(timeVo)){
                timeVo = new TimeVo();
                timeVo.setName(nameStr);
                map.put(nameStr,timeVo);
                isNew = true;
            }
            timeVo.add(Long.parseLong(timeStr));
            if(isNew){
                map.put(nameStr,timeVo);
            }
        }
    }
}
@Data
@ToString
class TimeVo{
    private String name;
    private long totalTime = 0;
    private int num = 0;
    private long avg = 0;
    public void add(long time){
        this.totalTime+=time;
        this.num++;
        avg = totalTime/num;
    }

}
