package java8.capter03;

import java.io.BufferedReader;
import java.io.FileReader;

public class OriginalProcessFile {
    public static void main(String[] args) {
        OriginalProcessFile originalProcessFile = new OriginalProcessFile();
        originalProcessFile.process();
        originalProcessFile.process1((p)->p.readLine());
        originalProcessFile.process1((p)->p.readLine().concat(p.readLine()));
    }

    public void process(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(""))){
            bufferedReader.readLine();
        }catch (Exception e){

        }
    }

    public void process1(ProcessorFile processorFile){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(""))){
            processorFile.processor(bufferedReader);
        }catch (Exception e){

        }
    }
}
