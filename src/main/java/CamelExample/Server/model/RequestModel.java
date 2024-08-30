package CamelExample.Server.model;

import mycode.model.BaseModel;

public class RequestModel extends BaseModel {
	private String msgid;
	private String dstaddr;
	private String dlvtime;
	private String donetime;
	private String statuscode;
	private String statusstr;
	private String StatusFlag;
    
    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getDstaddr() {
        return dstaddr;
    }

    public void setDstaddr(String dstaddr) {
        this.dstaddr = dstaddr;
    }

    public String getDlvtime() {
        return dlvtime;
    }

    public void setDlvtime(String dlvtime) {
        this.dlvtime = dlvtime;
    }

    public String getDonetime() {
        return donetime;
    }

    public void setDonetime(String donetime) {
        this.donetime = donetime;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatusstr() {
        return statusstr;
    }

    public void setStatusstr(String statusstr) {
        this.statusstr = statusstr;
    }

    public String getStatusFlag() {
        return StatusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        StatusFlag = statusFlag;
    }
}
