package CamelExample.Server.handler;

import org.apache.camel.Exchange;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import CamelExample.Server.model.RequestModel;
import CamelExample.Server.model.ResponseModel;
import mycode.main.Example.CacheExample;
import mycode.model.BaseModel;

/**
 * http://127.0.0.1:7777/callbackurl?msgid=A0000000013&dstaddr=85236690084&dlvtime=20240617144526&donetime=20240617144526&statusstr=DELIVRD&statuscode=0&StatusFlag=4
 */
public class JettyServerHandler extends BaseModel {
    private CacheExample cacheExample;
    
    public void handle(Exchange exchange) throws Exception {
        HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
        
        this.getRequestInfo(request);
        
        RequestModel requestModel = this.convertRequestToReqModel(request);        

        System.out.println(requestModel);
        
        ResponseModel responseModel = new ResponseModel();
//        responseModel.setMagicid("sms_gateway_rpack");
        responseModel.setMagicid("magicid");
        responseModel.setMsgid(requestModel.getMsgid());
        
        String responseBody = responseModel.toResponseString().replace("\n", "\n");
        
        exchange.getOut().setBody(responseBody);
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "text/plain");
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        
        cacheExample.getTest(requestModel.getMsgid());
        
    }
    
    private void getRequestInfo(HttpServletRequest request) {
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
    
    private RequestModel convertRequestToReqModel(HttpServletRequest request) {
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
    

    public CacheExample getCacheExample() {
        return cacheExample;
    }

    public void setCacheExample(CacheExample cacheExample) {
        this.cacheExample = cacheExample;
    }
}
