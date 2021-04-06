package nio.capter01;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 使用Files以及Path相关api快速遍历目录
 */
public class WalkFileTree {
    public static void main(String[] args) throws IOException {
//        countFileInfo("D:\\studysoft");
//        countSuffixFiles(".jar");
//        deletes();
    }

    private static void deletes() throws IOException {
        Files.walkFileTree(Paths.get("D:\\git - 副本"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    /**
     * 遍历包含指定后缀的文件数量
     * @throws IOException
     */
    private static void countSuffixFiles(final String suffix) throws IOException {
        AtomicLong fileCount = new AtomicLong();
        Files.walkFileTree(Paths.get("D:\\studysoft"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                //注意，不能直接使用Path的endsWith方法，因为这个方法是针对路径的
                if(file.toString().endsWith(suffix)){
                    fileCount.incrementAndGet();
                    System.out.println(file);
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("一共包含jar包："+fileCount);
    }


    /**
     * 遍历指定目录有多少文件夹，多少文件
     * @throws IOException
     */
    private static void countFileInfo(String path) throws IOException {
        AtomicLong dirCount = new AtomicLong();
        AtomicLong fileCount = new AtomicLong();

        Files.walkFileTree(Paths.get(path),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                //访问到文件夹之前的回调方法
                dirCount.incrementAndGet();
                System.out.println("=====>"+dir.toString());
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                fileCount.incrementAndGet();
                System.out.println(file.toString());
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("一共的目录数量是："+dirCount.get());
        System.out.println("一共的文件数量是："+fileCount.get());
    }
}
