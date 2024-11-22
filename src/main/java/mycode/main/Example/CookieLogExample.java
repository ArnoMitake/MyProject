package mycode.main.Example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import mycode.model.CookieLogModel;
import mycode.model.PropertiesModel;
import mycode.utils.PropertiesUtil;

/**
 * 解析LOG，找出cookieName=RememberMe的帳號，
 *
 * 2024-08-30 03:26:54.193 [INFO] CookieLog - 135 - 
 * ["帳號=TLSAY"] common/index.jsp remoteAddr=139.71.204.23, "cookieName=RememberMe", 
 * cookieValue=94e0e13f082657b6964a53851de5e7a6a87f20a365016d7f32a7ede0b65e46e1
 * 
 */
public class CookieLogExample {
    private static PropertiesModel propertiesModel;
    private static Pattern pattern;

    static {
        propertiesModel = new PropertiesModel();
        PropertiesUtil.getInstance().getCookieLogProperties(propertiesModel);        
        System.out.println(String.format("[PropertiesModel:%s]", propertiesModel));
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {        
        File file = new File(propertiesModel.getFolderPath());
        List<CookieLogModel> cookieLogModels = new ArrayList<>();
        Set<String> usernames = null;
        StopWatch sw = new StopWatch();
        sw.start();
        System.out.println(" CookieLogExample Start >>>>>>>>>>>>>>>> ");

        checkFileNameToParse(Arrays.asList(file.listFiles()), cookieLogModels);

//        excelExport(cookieLogModels);
        
        usernames = cookieLogModels.stream()
                        .map(CookieLogModel::getUsername)
                        .map(un -> un.trim())
                        .collect(Collectors.toSet());
        readExcel(usernames);
        
        System.out.println(" CookieLogExample End <<<<<<<<<<<<<<<<< ");
        sw.stop();
        System.out.println("CookieLogExample run time :" + sw.getTime() + "ms");        
    }

    public static void checkFileNameToParse(List<File> filePaths, List<CookieLogModel> cookieLogModels) throws IOException {
        for (File file : filePaths) {            
            if (file.isFile() && file.getName().contains("usernameLog.txt")) {
                parseLogTemp(file, cookieLogModels);
            }
            if (file.isFile() && !file.getName().contains(".gz")) {
//                System.out.println(file.getName());
//                parseLog(file, cookieLogModels);
            } else if (file.isFile() && !file.getName().contains(".gz")){
                System.out.println("檔案無法解析: " + file.getName());
            }
        }
    }

    public static void parseLogTemp(File filePath, List<CookieLogModel> cookieLogModels) throws IOException {
        CookieLogModel model = null;
//        pattern = Pattern.compile(propertiesModel.getKeyWord());
        pattern = Pattern.compile("\\[帳號=(.*?)\\].*cookieName=(.*?),");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            int lineNumber = 2;
            br.readLine();//跳過第一行

            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        model = new CookieLogModel();
                        model.setUsername(matcher.group(1));
                        model.setCookieName(matcher.group(2));
                        model.setCount(1);
                        cookieLogModels.add(model);
                    }
                }catch (Exception e) {
                    System.out.println(String.format("第%s行 >> [line:%s]-error:%s",lineNumber,  line, e.getMessage()));
                    e.printStackTrace();
                } finally {
                    lineNumber++;
                }
            }
        }
    }
    

    public static void parseLog(File filePath, List<CookieLogModel> cookieLogModels) throws IOException {
        CookieLogModel model = null;
        pattern = Pattern.compile(propertiesModel.getKeyWord());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            int lineNumber = 2;
            br.readLine();//跳過第一行
            
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        // 查找是否已存在相同的 username
                        CookieLogModel existingModel = findModelByUsername(cookieLogModels, matcher.group(1));                        
                        if (existingModel != null) {
                            existingModel.setCount(existingModel.getCount() + 1);
                        } else {
                            model = new CookieLogModel();
                            model.setUsername(matcher.group(1));
                            model.setCookieName(matcher.group(2));
                            model.setCount(1);
                            cookieLogModels.add(model);
                        }
                    }   
                }catch (Exception e) {
                    System.out.println(String.format("第%s行 >> [line:%s]-error:%s",lineNumber,  line, e.getMessage()));
                    e.printStackTrace();
                } finally {
                    lineNumber++;
                }
            }
        }
    }
    
    public static CookieLogModel findModelByUsername(List<CookieLogModel> cookieLogModels, String username) {
        for (CookieLogModel model : cookieLogModels) {
            if (model.getUsername().equals(username)) {
                return model;
            }
        }
        return null;
    }
    
    public static void readExcel(Set<String> usernames) {
        try (InputStream is = new FileInputStream(".\\CookieLog\\20240601-20240901被亂TRY的帳號紀錄V2.xlsx");
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);//分頁
            int rowNum = sheet.getLastRowNum();
            for (int i = 1; i <= rowNum; i++) {  // 从第1行开始读取，因为第0行是标题
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell usernameCell = row.getCell(2);
                Cell platformCell = row.getCell(1);
                String platform = platformCell.getStringCellValue().trim();//二、三站
                String username = usernameCell.getStringCellValue().trim();
                        
                if (platform.equals("MSG") //三站
//                if (platform.equals("SmExpress") //二站
                        && (usernames.contains(username) || usernames.contains("0" + username))) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    StringBuilder sb = new StringBuilder();
                    while (cellIterator.hasNext()) {
                        sb.append(cellIterator.next());
                        if (cellIterator.hasNext()) {
                            sb.append(",");
                        }
                    }
                    System.out.println(sb);
                }
            }                
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    
    public static void excelExport(List<CookieLogModel> cookieLogModels) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("三站");

        // 自訂標頭
        Row headerRow = sheet.createRow(0);
        String[] columns = {"帳號", "CookieName", "數量"};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            // 可以在這裡設置樣式
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for(CookieLogModel model : cookieLogModels) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(model.getUsername());
            row.createCell(1).setCellValue(model.getCookieName());
            row.createCell(2).setCellValue(model.getCount());
        }

        // 自動調整列寬
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 將Excel文件寫入磁盤
        try (FileOutputStream fileOut = new FileOutputStream("CookieLogData2.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
