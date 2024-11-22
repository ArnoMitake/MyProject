package mycode.main.Test;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.openjdk.jol.info.ClassLayout;

import java.lang.instrument.Instrumentation;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class JOLMemoryTest {
    private static volatile Instrumentation globalInstrumentation;

    public static void premain(String agentArgs, Instrumentation inst) {
        globalInstrumentation = inst;
    }

    public static long getObjectSize(Object object) {
        if (globalInstrumentation == null) {
            throw new IllegalStateException("Agent not initialized.");
        }
        return globalInstrumentation.getObjectSize(object);
    }
    
    public static void mains(String[] args) {
        String a = new String();
//        System.out.println(ClassLayout.parseInstance(a).toPrintable());
//        System.out.println("Empty string size: " + globalInstrumentation.getObjectSize(a) + " bytes");

        a = "測試測";
        System.out.println("Test string size: " + globalInstrumentation.getObjectSize(a) + " bytes");
    }

    public static void main(String[] args) {
        System.gc();
        
        try {
            Runtime runtime = Runtime.getRuntime();
            long beforeMemory,afterMemory,totalMemory,freeMemory;
            int patternSize=50;
            int domainSize=10;

            Thread.sleep(15000);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");

            totalMemory = runtime.totalMemory();
            freeMemory = runtime.freeMemory();
            // GC 前的記憶體使用
            beforeMemory = totalMemory - freeMemory;
            System.out.println("beforeMemory Object size: " + (beforeMemory) + " bytes");


            // 創建緩存，設置最大容量為 3
            Cache<String, String> patternCache = Caffeine.newBuilder()
                    .maximumSize(patternSize)    // 設置最大條目數
                    .expireAfterAccess(120, TimeUnit.SECONDS)  // 過期時間（非必要，展示緩存過期機製）
                    .build();

            for(int i=0;i<patternSize;i++)
            {
                patternCache.put("MSG_MITAKE_jackliutest"+i, "測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容");
//                patternCache.put("MSG_MITAKE_jackliutest"+i, i+ UUID.randomUUID().toString() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID() + UUID.randomUUID());
            }


            // 創建緩存，設置最大容量為 3
            Cache<String, String> domainCache = Caffeine.newBuilder()
                    .maximumSize(domainSize)    // 設置最大條目數
                    .expireAfterAccess(10, TimeUnit.SECONDS)  // 過期時間（非必要，展示緩存過期機製）
                    .build();

            for(int i=0;i<domainSize;i++)
            {
//                domainCache.put("MSG_MITAKE_jackliutest"+i, UUID.randomUUID().toString());
                domainCache.put("MSG_MITAKE_jackliutest"+i, "www.www.mitakem.com.tw");
            }

            totalMemory = runtime.totalMemory();
            freeMemory = runtime.freeMemory();
            afterMemory = totalMemory - freeMemory;

            // 查看當前緩存的條目數
            System.out.println("patternCache Size: " + patternCache.estimatedSize());
            System.out.println("patternCache Content: " + patternCache.asMap());
            System.out.println("domainCache Size: " + domainCache.estimatedSize());
            System.out.println("domainCache Content: " + domainCache.asMap());


//     Thread.sleep(2000);
            patternCache.put("MSG_MITAKE_jackliutest51", "測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容測試內容");
            patternCache.cleanUp();

            patternCache.getIfPresent("MSG_MITAKE_jackliutest1");
            System.out.println("MSG_MITAKE_jackliutest1: " + patternCache.getIfPresent("MSG_MITAKE_jackliutest1"));

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
            Thread.sleep(15000);


            System.out.println("afterMemory Object size: " + (afterMemory) + " bytes");
            System.out.println("Object size: " + ((afterMemory - beforeMemory)/(1024.0 * 1024.0)) + " mb");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
