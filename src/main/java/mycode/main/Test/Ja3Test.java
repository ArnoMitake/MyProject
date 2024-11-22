package mycode.main.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ja3Test {
    
    static String ex = "771-4865-4866-4867-49195-49199-49196-49200-52393-52392-49171-49172-156-157-47-53-51-0-27-17513-65037-13-5-35-23-16-18-45-43-11-10-65281-25497-29-23-24-0";    
    static String my = "771-4865-4866-4867-49195-49199-49196-49200-52393-52392-49171-49172-156-157-47-53-51-35-65281-17513-23-10-45-18-16-27-13-65037-5-0-11-43-41-25497-29-23-24-0";
    
    public static void main(String[] args) {        
        List<String> exampleTest = Arrays.stream(ex.split("-")).sorted().collect(Collectors.toList());
        System.out.println(exampleTest);
        List<String> myTest = Arrays.stream(my.split("-")).sorted().collect(Collectors.toList());
        System.out.println(myTest);
        
    }
}
//[0, 10, 11, 13, 156, 157, 16, 17513, 18, 23, 23, 24-0, 27, 29, 35, 43, 45, 47, 4866, 4867, 49171, 49172, 49195, 49196, 49199, 49200, 5, 52392, 52393, 53|51, 65037, 65281|25497, 771|4865]
//        [0, 10, 11, 13, 156, 157, 17513, 18, 23, 23, 24|0, 27, 29, 35, 41|25497, 43, 45, 47, 4866, 4867, 49171, 49172, 49195, 49196, 49199, 49200, 5, 51, 52392, 52393, 53|16, 65037, 65281, 771|4865]