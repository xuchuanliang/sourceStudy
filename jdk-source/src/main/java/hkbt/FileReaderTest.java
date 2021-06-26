package hkbt;

import cn.hutool.core.util.StrUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Function;

public class FileReaderTest {
    public static void main(String[] args) throws IOException {
//        showII();
        showAll();
    }

    public static void showAll() throws IOException {
        test("D:\\工作文档\\CMMI\\git文件\\CMMI5_HZFYHK",p->{
            String f = p.toString();
            if(!f.equals(".git") && !f.endsWith(".md") && !f.endsWith("DS_Store")){
                String[] cmmi5_hzfyhks = f.split("CMMI5_HZFYHK");
                String substring = cmmi5_hzfyhks[1].substring(9);
                return substring;
            }
            return null;
        });
    }

    public static void showII() throws IOException {
        test("D:\\工作文档\\CMMI\\git文件\\CMMI5_HZFYHK\\03 文档准备\\组织级文档\\组织资产库",p->{
            String f = p.toString();
            if (!f.endsWith("DS_Store")) {
                int i = f.indexOf("03 文档准备");
                return f.substring(i + 7);
            }
            return null;
        });
    }

    public static void showOT() throws IOException {
        Function<Path, String> pathStringFunction = p -> {
            String f = p.toString();
            if (!f.endsWith("DS_Store")) {
                int i = f.indexOf("海康保泰");
                return f.substring(i + 5);
            }
            return null;
        };
        test("D:\\工作文档\\CMMI\\20210510\\海康保泰", pathStringFunction);
    }

    public static void test(String baseDir, Function<Path, String> handler) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\工作文档\\CMMI\\ATM\\a.txt"));
        Path path = Paths.get(baseDir);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String p = handler.apply(file);
                if (StrUtil.isNotEmpty(p)){
                    bufferedWriter.newLine();
                    bufferedWriter.write(p);
                }
                return super.visitFile(file, attrs);
            }
        });
        bufferedWriter.close();
    }
}
