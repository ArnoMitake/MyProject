package CamelExample.Server.handler;

import org.apache.camel.Exchange;

import javax.servlet.http.HttpServletRequest;

import CamelExample.Server.model.RequestModel;
import CamelExample.Server.model.ResponseModel;
import CamelExample.Server.utils.CacheUtil;
import CamelExample.Server.utils.JettyHttpUtil;
import mycode.model.BaseModel;

/**
 * 目前還不太會用xml語法寫，暫時參考 {@link JettyHttpsServer}
 * https://10.3.2.114:6666/fingerprint?
 */
public class JettyHttpsServerHandler extends BaseModel {
    private String url = "https://10.3.2.114:6666/fingerprint?";
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
