package CamelExample.Server.CTCB.model;

import java.util.Map;

import mycode.model.BaseModel;

public class CTCBRequestModel extends BaseModel {
	private String vendor;
	private String msgid;
	private String status;
	private String updatetime;
	private String otp;
	private String sectionCnt;
	private Map<String, String> sec;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getSectionCnt() {
        return sectionCnt;
    }

    public void setSectionCnt(String sectionCnt) {
        this.sectionCnt = sectionCnt;
    }

    public Map<String, String> getSec() {
        return sec;
    }

    public void setSec(Map<String, String> sec) {
        this.sec = sec;
    }
}
