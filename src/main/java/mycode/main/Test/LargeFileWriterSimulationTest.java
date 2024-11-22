package mycode.main.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class LargeFileWriterSimulationTest {
    public static void main(String[] args) {
        // 設定目錄和文件名稱
        String directoryPath = "C:\\Users\\arno\\upload";
        String fileName = "20240117121505082-A000061;LN1183_20240116.TXT";
        int totalLines = 100000; // 模擬文件的總行數
        int delayMillis = 0; // 每行寫入間隔，這裡設定為 10 秒
        
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(directoryPath + File.separator + fileName), Charset.forName("Big5")))) {
            writer.write("FBBANK&BY001&&&30&1&2&&\r\n");
            for (int i = 1; i <= totalLines; i++) {
//                writer.write("無擔作業  &&0007777765&親愛的客戶您好，台北富邦銀行已收到您繳入貸款帳號末四碼7780的還款款項，因有溢繳情形，本行將為您保留並依序抵償各項費用與月付金(包括利息及本金，詳見契約內容)，若您有其他還款需求，請洽本行客服。\r\n");
                writer.write("無擔作業  &&0007777765&親愛的客戶您好，台北富邦銀行已收到您繳入貸款帳號末四碼7780的還款款項，因有溢繳情形，本行將為您保留並依序抵償各項費用與月付金(包括利息及本金，詳見契約內容)，若您有其他還款需求，請洽本行客服。\r\n");
                writer.write("無擔作業  &&0007777765&親愛的客戶您好，台北富邦銀行已收到您繳入貸款帳號末四碼7780的還款款項，因有溢繳情形，本行將為您保留並依序抵償各項費用與月付金(包括利息及本金，詳見契約內容)，若您有其他還款需求，請洽本行客服。\r\n");
                writer.write("無擔作業  &&0007777765&親愛的客戶您好，台北富邦銀行已收到您繳入貸款帳號末四碼7780的還款款項，因有溢繳情形，本行將為您保留並依序抵償各項費用與月付金(包括利息及本金，詳見契約內容)，若您有其他還款需求，請洽本行客服。\r\n");
                writer.write("無擔作業  &&0007777765&親愛的客戶您好，台北富邦銀行已收到您繳入貸款帳號末四碼7780的還款款項，因有溢繳情形，本行將為您保留並依序抵償各項費用與月付金(包括利息及本金，詳見契約內容)，若您有其他還款需求，請洽本行客服。");                
//                writer.write("This is line number " + i);
                writer.newLine();
                writer.flush();
                System.out.println("Wrote line " + i);
                TimeUnit.MILLISECONDS.sleep(delayMillis); // 等待指定的時間
            }
            System.out.println("Finished writing to the file.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        // 建立目錄
//        File directory = new File(directoryPath);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        // 開始寫入檔案
//        File file = new File(directoryPath + "/" + fileName);
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//            for (int i = 1; i <= totalLines; i++) {
//                String line = "這是測試行 " + i;
//                writer.write(line);
//                writer.newLine();
//                writer.flush(); // 確保每行寫入馬上生效
//                System.out.println("寫入行: " + line);
//
//                // 暫停 5 秒鐘
//                Thread.sleep(delayMillis);
//            }
//            System.out.println("檔案寫入完成!");
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
