package CamelExample.Server.handler;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CamelExample.Server.utils.JettyHttpUtil;

public class JettyHttpsServer {
    private static String url = "10.3.2.114";
    private static Integer port = 6666;
    
    public static void main(String[] args) throws Exception {
        
        Server server = JettyHttpUtil.getInstance().createJettySslFactory(url, port);

        // 設置 Servlet Handler
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        
        context.addServlet(new ServletHolder(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
                String responseBody = null;
                resp.setContentType("text/html");
                resp.setStatus(HttpServletResponse.SC_OK);
                try {
                    
                    JettyHttpUtil.getInstance().getRequestInfo(req);
                    
                    responseBody = JettyHttpUtil.getInstance().createHtml("ok!", req.getRequestURL().toString().replace("http", "https"));
                    
                    resp.getWriter().println(responseBody);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }), "/test");

        // 啟動伺服器
        server.start();
        server.join();
    }
}
