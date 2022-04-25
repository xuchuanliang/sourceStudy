package hkbt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析iot日志中某一个具体设备编码的所有日志
 */
public class LogFilterFilter {
    private static final String KEY = "202111130000009";
    private static List<String> filesPaths = new ArrayList<>();
    static {
        for (File file : new File("C:\\Users\\xuchuanliangbt\\Desktop\\log\\info").listFiles()) {
            filesPaths.add(file.getAbsolutePath());
        }
        for (File file : new File("C:\\Users\\xuchuanliangbt\\Desktop\\log\\warn").listFiles()) {
            filesPaths.add(file.getAbsolutePath());
        }
        for (File file : new File("C:\\Users\\xuchuanliangbt\\Desktop\\log\\error").listFiles()) {
            filesPaths.add(file.getAbsolutePath());
        }
        filesPaths.add("C:\\Users\\xuchuanliangbt\\Desktop\\log\\log_error.log");
        filesPaths.add("C:\\Users\\xuchuanliangbt\\Desktop\\log\\log_info.log");
        filesPaths.add("C:\\Users\\xuchuanliangbt\\Desktop\\log\\log_warn.log");
    }
    public static void main(String[] args) {
        File new_error = new File("C:\\Users\\xuchuanliangbt\\Desktop\\log\\new_log_error.log");
        File new_warn = new File("C:\\Users\\xuchuanliangbt\\Desktop\\log\\new_log_warn.log");
        File new_info = new File("C:\\Users\\xuchuanliangbt\\Desktop\\log\\new_log_info.log");
        try {
            BufferedWriter newErrorWriter = new BufferedWriter(new FileWriter(new_error));
            BufferedWriter newWarnWriter = new BufferedWriter(new FileWriter(new_warn));
            BufferedWriter newInfoWriter = new BufferedWriter(new FileWriter(new_info));
            filesPaths.forEach(f->{
                File file = new File(f);
                BufferedWriter tempBufferedWriter = null;
                if(file.getName().contains("error")){
                    tempBufferedWriter = newErrorWriter;
                }else if(file.getName().contains("warn")){
                    tempBufferedWriter = newWarnWriter;
                }else if(file.getName().contains("info")){
                    tempBufferedWriter = newInfoWriter;
                }else{
                    System.out.println("跳过该文件："+file.getName());
                    return;
                }
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
                    String line = bufferedReader.readLine();
                    while (line !=null){
                        if(line.contains(KEY)){
                            tempBufferedWriter.write(line);
                            tempBufferedWriter.write("\r\n");
                        }
                        line = bufferedReader.readLine();
                    }
                    //文件读完
                    tempBufferedWriter.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            newErrorWriter.flush();
            newWarnWriter.flush();
            newInfoWriter.flush();
            newErrorWriter.close();
            newWarnWriter.close();
            newInfoWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
