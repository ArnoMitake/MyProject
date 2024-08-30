package Example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.File;

public class FtpExample extends RouteBuilder {
    public static void main(String[] args) throws Exception {
        
//        CamelContext camelContext = new DefaultCamelContext();
//        camelContext.addRoutes(new FtpExample());

//        camelContext.start();
        //Thread.sleep(10000);
        //camelContext.stop();
    }

    @Override
    public void configure() throws Exception {
        from("sftp://10.99.0.191:5022//CHT_NP/?" +
                "username=Administrator" +
                "&password=mtk@10.99.0.191" +
                "&include=ReceiverList_\\d{8}\\.csv" +
                "&download=true" +
                "&stepwise=false" +
                "&delete=true" +
                "&streamDownload=true" +
                "&readLockMinLength=0" +
                "&readLock=changed" )
//                "&localWorkDirectory=.\\tmp")
                .to("file:.\\testest")
                .log("File downloaded successfully: ${file:name}");
    }
}
