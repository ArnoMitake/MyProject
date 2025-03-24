package CamelExample.Server.CTCB.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import CamelExample.Server.CTCB.model.CTCBRequestModel;
import CamelExample.Server.CTCB.model.CTCBResponseModel;
import mycode.model.BaseModel;

/**
 * Dr短訊:
 * http://127.0.0.1:8080/mip/ReturnStatus?vendor=1&msgid=%30%32%30%32%34%30%39%31%30%20&status=2&updatetime=20240910091712&otp=N 
 * Dr長訊:
 * http://127.0.0.1:8080/mip/ReturnStatus?vendor=1&msgid=%23%32%30%32%34%30%39%31%30%20&status=2&updatetime=20240910091712&otp=N&sectionCnt=2&sec1=2&sec2=2
 * Mo:
 * http://127.0.0.1:8081/smsdr/ReplyMsg?vendor=1&telno=0911111111&code=168168&content=MoTest2&cust_recv_time=20250214155904
 */
public class CTCBJettyServerHandler extends BaseModel {
    
    public void handle(Exchange exchange) throws Exception {
        HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
        
        CTCBRequestModel requestModel = new CTCBRequestModel();
        Map<String, String> secMap = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        
        for (String paramName : parameterMap.keySet()) {
            String[] values = parameterMap.get(paramName);
            if (values != null && values.length > 0) {
                if ("vendor".equals(paramName)) {
                    requestModel.setVendor(values[0]);
                } else if ("msgid".equals(paramName)) {
                    requestModel.setMsgid(values[0]);
                } else if ("status".equals(paramName)) {
                    requestModel.setStatus(values[0]);
                } else if ("updatetime".equals(paramName)) {
                    requestModel.setUpdatetime(values[0]);
                } else if ("otp".equals(paramName)) {
                    requestModel.setOtp(values[0]);
                } else if ("sectionCnt".equals(paramName)) {
                    requestModel.setSectionCnt(values[0]);
                } else if (StringUtils.trimToEmpty(paramName).startsWith("sec")) {
                    secMap.put(paramName, values[0]);
                } else if ("telno".equals(paramName)) {
					requestModel.setTelno(values[0]);
				} else if ("code".equals(paramName)) {
					requestModel.setCode(values[0]);
				} else if ("content".equals(paramName)) {
					requestModel.setContent(values[0]);
				} else if ("cust_recv_time".equals(paramName)) {
					requestModel.setCust_recv_time(values[0]);
				}
            }
        }

        requestModel.setSec(secMap);
        
        System.out.println(requestModel);

        CTCBResponseModel responseModel = new CTCBResponseModel();
        responseModel.setResponse("OK");
//        responseModel.setResponse("NO");
        
        exchange.getOut().setBody(responseModel);
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "text/plain");
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
//        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 300);
    }
}
