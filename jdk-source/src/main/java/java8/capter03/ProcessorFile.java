package java8.capter03;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
public interface ProcessorFile {
    void processor(BufferedReader bufferedReader)throws IOException;
}
