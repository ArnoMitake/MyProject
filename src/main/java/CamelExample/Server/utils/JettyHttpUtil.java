package CamelExample.Server.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import CamelExample.Server.model.RequestModel;

public class JettyHttpUtil {
    private static JettyHttpUtil jettyHttpUtil;

    public JettyHttpUtil() {
    }
    
    public static synchronized JettyHttpUtil getInstance() {
        return jettyHttpUtil == null ? new JettyHttpUtil() : jettyHttpUtil;
    }

    public String createHtml(String responseBody, String url) {
        return "<!DOCTYPE html>\n" +
                "        <html>\n" +
                "        <head>\n" +
                "            <title>API Call Page</title>\n" +
                "            <script>\n" +
                "                function sendApiRequest() {\n" +
                "                    fetch(' " + url + "')\n" +
                "                        .then(response => response.text())\n" +
                "                        .then(data => alert('API Response: ' + data))\n" +
                "                        .catch(error => alert('Error: ' + error));\n" +
                "                }\n" +
                "            </script>\n" +
                "        </head>\n" +
                "        <body>\n" +
                "            <h1>API Call Example</h1>\n" +
                "            <button onclick=\"sendApiRequest()\">Send API Request</button>\n" +
                "    <h2>Response:</h2>\n" +
                "    <div id=\"apiResponse\">" + responseBody + "</div>\n" +
                "        </body>\n" +
                "        </html>";
    }

    public void getRequestInfo(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        Enumeration<String> attributes = request.getAttributeNames();

        System.out.println("getRemoteAddr: " + request.getRemoteAddr());
        System.out.println("getRemoteHost+getRemotePort: " + request.getRemoteHost() + ":" + request.getRemotePort());
        System.out.println("getRequestURI: " + request.getRequestURI());
        System.out.println("getRequestURL: " + request.getRequestURL());
        System.out.println("getServerName: " + request.getServerName());
        System.out.println("getPathInfo: " + request.getPathInfo());
        System.out.println("getPathTranslated: " + request.getPathTranslated());
        System.out.println("getQueryString: " + request.getQueryString());
        System.out.println("getProtocol: " + request.getProtocol());
        System.out.println("getScheme: " + request.getScheme());
        System.out.println("getAttribute: " + request.getAttribute("javax.servlet.request.X509Certificate"));
        System.out.println("getUserPrincipal: " + request.getUserPrincipal());
        System.out.println("getServerPort: " + request.getServerPort());
        System.out.println("getServletContext: " + request.getServletContext());
        System.out.println("getRequestedSessionId: " + request.getRequestedSessionId());

        while (headers.hasMoreElements()){
            String headerName = headers.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }

        while (attributes.hasMoreElements()) {
            String attributeName = attributes.nextElement();
            System.out.println(attributeName + ": " + request.getAttribute(attributeName));
        }
    }

    public RequestModel convertRequestToReqModel(HttpServletRequest request) {
        RequestModel requestModel = new RequestModel();
        requestModel.setMsgid(request.getParameter("msgid"));
        requestModel.setDstaddr(request.getParameter("dstaddr"));
        requestModel.setDlvtime(request.getParameter("dlvtime"));
        requestModel.setDonetime(request.getParameter("donetime"));
        requestModel.setStatusstr(request.getParameter("statusstr"));
        requestModel.setStatuscode(request.getParameter("statuscode"));
        requestModel.setStatusFlag(request.getParameter("StatusFlag"));
        return requestModel;
    }

    public Server createJettySslFactory(String url, int port) {
        //創建 SSL 上下文工廠
        SslContextFactory sslContextFactory = new SslContextFactory();
        // 設置憑證文件和密碼
        sslContextFactory.setKeyStorePath(".\\conf\\LmAPI.jks");//keystore 文件的路徑
        sslContextFactory.setKeyStorePassword("86136982");
        sslContextFactory.setKeyManagerPassword("86136982");

        // 創建 Jetty Server 並設置 HTTPS 連接埠
        Server server = new Server();
        org.eclipse.jetty.server.HttpConfiguration httpsConfig = new org.eclipse.jetty.server.HttpConfiguration();
        org.eclipse.jetty.server.ServerConnector httpsConnector = new org.eclipse.jetty.server.ServerConnector(
                server,
                new org.eclipse.jetty.server.SslConnectionFactory(sslContextFactory, "http/1.1"),
                new org.eclipse.jetty.server.HttpConnectionFactory(httpsConfig)
        );
        httpsConnector.setHost(url);
        httpsConnector.setPort(port);
        server.addConnector(httpsConnector);

        return server;
    }

}
