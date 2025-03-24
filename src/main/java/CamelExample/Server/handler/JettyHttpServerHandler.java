package CamelExample.Server.handler;

import org.apache.camel.Exchange;

import javax.servlet.http.HttpServletRequest;
import CamelExample.Server.model.RequestModel;
import CamelExample.Server.model.ResponseModel;
import CamelExample.Server.utils.CacheUtil;
import CamelExample.Server.utils.JettyHttpUtil;
import mycode.model.BaseModel;

/**
 * http://127.0.0.1:7777/callbackurl?msgid=A0000000013&dstaddr=85236690084&dlvtime=20240617144526&donetime=20240617144526&statusstr=DELIVRD&statuscode=0&StatusFlag=4
 */
public class JettyHttpServerHandler extends BaseModel {
    private String url = "http://10.3.2.114:7777/callbackurl?msgid=A0000000013&dstaddr=85236690084&dlvtime=20240617144526&donetime=20240617144526&statusstr=DELIVRD&statuscode=0&StatusFlag=4";
    private CacheUtil cacheUtil;
    
    public void handle(Exchange exchange) throws Exception {
        HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
        
        JettyHttpUtil.getInstance().getRequestInfo(request);
        
        RequestModel requestModel = JettyHttpUtil.getInstance().convertRequestToReqModel(request);        

        System.out.println(requestModel);
        
        ResponseModel responseModel = new ResponseModel();
//        responseModel.setMagicid("sms_gateway_rpack");
        responseModel.setMagicid("magicid");
        responseModel.setMsgid(requestModel.getMsgid());
        
        String responseBody = responseModel.toResponseString().replace("\n", "\n");
        
        exchange.getOut().setBody(JettyHttpUtil.getInstance().createHtml(responseBody, url));
//        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "text/plain");
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "text/html");
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);

        cacheUtil.getTest(requestModel.getMsgid());
        
    }

    public CacheUtil getCacheUtil() {
        return cacheUtil;
    }

    public void setCacheUtil(CacheUtil cacheUtil) {
        this.cacheUtil = cacheUtil;
    }

}
