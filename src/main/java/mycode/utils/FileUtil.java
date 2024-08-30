package mycode.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

//todo 暫時沒想法怎分離出 Log 系列的 Example
public class FileUtil {
    private static FileUtil fileUtil;
    
    public FileUtil(){
        
    }
    
    public static synchronized FileUtil getInstance() {
        return fileUtil == null ? new FileUtil() : fileUtil;
    }
    
    private <T> void getFilePaths(T file) {
        if (file instanceof File) {
            System.out.println(file);
        } else if (file instanceof File[]) {
            List<File> files = Arrays.asList((File[]) file);
            files.forEach(f -> System.out.println(f));
        }

    }

    public static void main(String[] args) {
        FileUtil.getInstance().getFilePaths(new File(".\\主機上安裝軟體清查").listFiles());
        FileUtil.getInstance().getFilePaths(new File(".\\主機上安裝軟體清查"));
    }
    
}
