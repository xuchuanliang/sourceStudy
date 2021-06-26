package com.test1.ant;

import java.io.*;

public class HashtableMain {
    private static final int MAX_LINE = 10000;
    public static void main(String[] args) {
        try {
            read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void read() throws IOException {
        int subfix = 1;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\desktop\\fastFiles\\sql\\hikpsis\\baoan_result.sql"));
        while (true) {
            int index = 0;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\desktop\\fastFiles\\sql\\hikpsis\\baoan_result"+subfix+".sql"));
            String line = bufferedReader.readLine();
            if(null==line || line.equals("") || line.length()<1){
                break;
            }
            while (index<MAX_LINE){
                try{
                    bufferedWriter.write(line);
                }catch (Exception e){
                    e.printStackTrace();
                }
                bufferedWriter.write("\r\n");
                line = bufferedReader.readLine();
                index++;
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            subfix++;
        }
        bufferedReader.close();
    }
}
