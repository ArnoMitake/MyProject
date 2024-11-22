package mycode.main.Test;
//package com.mitake;
//
//import com.github.benmanes.caffeine.cache.Cache;
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.gliwka.hyperscan.util.PatternFilter;
//import com.gliwka.hyperscan.wrapper.CompileErrorException;
//
//import org.github.jamm.MemoryMeter;
//import org.openjdk.jol.info.GraphLayout;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//public class CacheMemoryTest {
//    private static double mb = (1024.0 * 1024.0);
//    private static int accountSize;
//    private static int patternSize;
//    private static int domainSize;
//    private static String patternType;
//    private static String domainType;
//    private static String patternContext;
//    private static String domainContext;
//
//    static {
//        Properties prop = new Properties();
//        try (FileInputStream input = new FileInputStream("conf/AppSettings.properties");
//             InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
//            prop.load(reader);
//            accountSize = Integer.parseInt(prop.getProperty("accountSize"));
//            patternSize = Integer.parseInt(prop.getProperty("patternSize"));
//            domainSize = Integer.parseInt(prop.getProperty("domainSize"));
//            patternType = prop.getProperty("patternType");
//            domainType = prop.getProperty("domainType");
//            patternContext = prop.getProperty("patternContext");
//            domainContext = prop.getProperty("domainContext");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void mains(String[] args) {
//        Map<String, List<String>> map = new HashMap<>();        
//        List<String> a = Arrays.asList("測試測試測試測試","123","ABC", "cccc");
//        map.put("a", a);
////        map.put("b", a);
////        map.put("c", a);
//        System.out.println("1: " + GraphLayout.parseInstance(map).totalSize());
//        System.out.println("2: " + GraphLayout.parseInstance(map).totalCount());
//        System.out.println("3: " + MyAgent.getObjectSize(map));
////        System.out.println("4: " + MyAgent.getTotalSize(map));
//        MemoryMeter meter = MemoryMeter.builder().build();
//
//        ///計算 List<String> 的淺層大小（只計算物件本身，不包含其引用的物件）
//        System.out.println("5: " + meter.measure(map));
//        // 計算 List<String> 的深層大小（包括其引用的物件）
//        System.out.println("6: " + meter.measureDeep(map));       
//
//        for (Map.Entry mm : map.entrySet()){
//            long msize = meter.measureDeep(mm.getKey()) + meter.measureDeep(mm.getValue()); 
//            System.out.println(mm.getKey() + ": " + msize);
//        }
//        
////        int i = 0;
////        for(String b : a) {
////            System.out.println(b + ": " + meter.measureDeep(b));
////            i += b.getBytes(StandardCharsets.UTF_8).length;
////        }
////        System.out.println("7: " + i);
//    }
//
//    public static void mainss(String[] args) {
//        System.gc();
//     
//                
//        try {
//            Runtime runtime = Runtime.getRuntime();
//            long beforeMemory, afterMemory, totalMemory, freeMemory;
//
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
//
//            totalMemory = runtime.totalMemory();
//            freeMemory = runtime.freeMemory();
//            // GC 前的記憶體使用
//            beforeMemory = totalMemory - freeMemory;
//            System.out.println("beforeMemory Object size: " + (beforeMemory) + " bytes");
//            
//            // 創建緩存，設置最大容量為 3
//            Cache<String, String> patternCache = Caffeine.newBuilder()
//                    .removalListener(((key, value, cause) -> {
//                        System.out.printf("Removed key: %s, value: %s, cause: %s%n", key, value, cause);
//                    }))
//                    .maximumSize(patternSize)    // 設置最大條目數
//                    .expireAfterAccess(1200000, TimeUnit.SECONDS)  // 過期時間（非必要，展示緩存過期機製）
//                    .build();
//
//            for (int i = 0; i < patternSize; i++) {
//                if (patternType.equals("N")) {
//                    patternCache.put("MSG_MITAKE_jackliutest" + i, patternContext);
//                } else {
//                    patternCache.put("MSG_MITAKE_jackliutest" + i, i + UUID.randomUUID().toString() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID());
//                }
//            }
//
//            // 創建緩存，設置最大容量為 3
//            Cache<String, String> domainCache = Caffeine.newBuilder()
//                    .removalListener(((key, value, cause) -> {
//                        System.out.printf("Removed key: %s, value: %s, cause: %s%n", key, value, cause);
//                    }))
//                    .maximumSize(domainSize)    // 設置最大條目數
//                    .expireAfterAccess(1200000, TimeUnit.SECONDS)  // 過期時間（非必要，展示緩存過期機製）
//                    .build();
//
//            for (int i = 0; i < domainSize; i++) {
//                if (domainType.equals("Y")) {
//                    domainCache.put("MSG_MITAKE_jackliutest" + i, domainContext);
//                } else {
//                    domainCache.put("MSG_MITAKE_jackliutest"+i, UUID.randomUUID().toString());
//                }
//            }
//            
//            totalMemory = runtime.totalMemory();
//            freeMemory = runtime.freeMemory();
//            afterMemory = totalMemory - freeMemory;
//            System.out.println("afterMemory Object size: " + (afterMemory) + " bytes");
//
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
//            System.out.println("Object size: " + ((afterMemory - beforeMemory) / (1024.0 * 1024.0)) + " mb");
//
////            long memory = GraphLayout.parseInstance(patternCache).totalSize() + GraphLayout.parseInstance(domainCache).totalSize();
////            System.out.println(memory / (1024.0 * 1024.0));
////            System.out.println(memory);
//
//            System.out.println(GraphLayout.parseInstance(patternCache).totalSize()/ mb);
//            System.out.println(GraphLayout.parseInstance(domainCache).totalSize()/ mb);
//            System.out.println(MyAgent.getObjectSize(patternCache)/mb);
//            System.out.println(MyAgent.getObjectSize(domainCache)/mb);
//            System.out.println(MyAgent.getTotalSize(patternCache)/mb);
//            System.out.println(MyAgent.getTotalSize(domainCache)/mb);
//            MemoryMeter meter = MemoryMeter.builder().build();
//            System.out.println(meter.measureDeep(patternCache)/ mb);
//            System.out.println(meter.measureDeep(domainCache)/ mb);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        System.gc();
//        try {
//            
//            System.out.println(" CacheMemoryTest start >>>>>>>>>>>>>>>>>>>>>>>>>");
//            //1個帳號，多個key(先預設1個key)，多個pattern
//            Map<String, Map<String, PatternFilter>> patternMap = null;
//            //1個帳號，多個domain
//            Map<String, List<String>> domainMap = null;
//            
//            Cache<String, List<String>> patternCache = Caffeine.newBuilder()
//                    .removalListener(((key, value, cause) -> {
//                        System.out.printf("Removed key: %s, value: %s, cause: %s%n", key, value, cause);
//                    }))
//                    .maximumWeight(132)
//                    .weigher((k,v) -> {
//                        MemoryMeter meter = MemoryMeter.builder().build();
//                        Map<String, List<String>> map = (Map<String, List<String>>) k;
//                        return (int) meter.measureDeep(map);
//                    })
////                    .maximumSize(patternSize)    // 設置最大條目數
//                    .expireAfterAccess(120, TimeUnit.SECONDS)  // 過期時間（非必要，展示緩存過期機製）
//                    .build();
//
//
//            
//            
//        } catch (Exception e) {
//            
//        }
//    }
//}
