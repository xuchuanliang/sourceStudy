package com.deepJVM.capter02;

import java.util.ArrayList;
import java.util.List;

/**
 * java堆内存溢出
 * -Xmx50m -Xms50m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {
    private static List<Placeholder> list = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            list.add(new Placeholder());
        }
    }

    private static class Placeholder {

        byte[] _1KB;

        public Placeholder() {
            _1KB = new byte[1024];
        }
    }
}
