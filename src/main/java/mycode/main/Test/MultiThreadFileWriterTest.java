package mycode.main.Test;

import org.springframework.util.StopWatch;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadFileWriterTest {

    private static final String FILE_PATH = "Camels_output2.txt";
    private static final String IMAGE_FILE_PATH = "\\\\\\wsl$\\Ubuntu\\home\\arno\\gw_mms_att\\Camels_image_output_";
    private static final String INPUT_IMAGE_PATH = "C:\\Users\\arno\\Pictures\\Camels.jpg"; //圖片路徑
    //設定執行續數量
    private static final int THREAD_POOL = 5;
    
    
    public static void main(String[] args) {
        StopWatch sw = new StopWatch("main");
        sw.start("main");
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);

        for (int i = 1; i <= 200; i++) {
            int threadNumber = i;
            
            executor.submit(() -> writeToFile(threadNumber));
            
        }        
        executor.shutdown();
        try {
            // 等待所有任务执行完成
            if (executor.awaitTermination(1, TimeUnit.MINUTES)) {
                sw.stop();
                System.out.println(sw);
            } else {
                System.out.println("Tasks did not finish in the given time.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(int threadNumber) {
        StopWatch sw  = new StopWatch("writeToFile");
        sw.start("writeToFile" + threadNumber);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            byte[] imageBytes = Files.readAllBytes(Paths.get(INPUT_IMAGE_PATH));
            for (int i = 0; i < 10; i++) {
                writer.write("Thread " + threadNumber + " is writing line " + (i + 1) + "\n");
            }

            
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(IMAGE_FILE_PATH + threadNumber + ".jpg"))) {
                bos.write(imageBytes);
                bos.flush();
            }
            sw.stop();
            System.out.println(sw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
