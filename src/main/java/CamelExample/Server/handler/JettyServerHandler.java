package CamelExample.Server.handler;

import org.apache.camel.Exchange;

import javax.servlet.http.HttpServletRequest;

import CamelExample.Server.model.RequestModel;
import CamelExample.Server.model.ResponseModel;
import model.BaseModel;

/**
 * http://127.0.0.1:7777/callbackurl?msgid=A0000000013&dstaddr=85236690084&dlvtime=20240617144526&donetime=20240617144526&statusstr=DELIVRD&statuscode=0&StatusFlag=4
 */
public class JettyServerHandler extends BaseModel {
    
    public void handle(Exchange exchange) throws Exception {
        HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);

        RequestModel requestModel = new RequestModel();
        requestModel.setMsgid(request.getParameter("msgid"));
        requestModel.setDstaddr(request.getParameter("dstaddr"));
        requestModel.setDlvtime(request.getParameter("dlvtime"));
        requestModel.setDonetime(request.getParameter("donetime"));
        requestModel.setStatusstr(request.getParameter("statusstr"));
        requestModel.setStatuscode(request.getParameter("statuscode"));
        requestModel.setStatusFlag(request.getParameter("StatusFlag"));

        System.out.println(requestModel);
        
        ResponseModel responseModel = new ResponseModel();
//        responseModel.setMagicid("sms_gateway_rpack");
        responseModel.setMagicid("magicid");
        responseModel.setMsgid(requestModel.getMsgid());
        
        String responseBody = responseModel.toResponseString().replace("\n", "\n");
        
        exchange.getOut().setBody(responseBody);
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "text/plain");
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }
}
