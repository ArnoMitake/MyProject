package CamelExample.Server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerAppMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
                "System_Camel.cfgBean.xml");
    }
}
