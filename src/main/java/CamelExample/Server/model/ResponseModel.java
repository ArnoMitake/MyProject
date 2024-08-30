package CamelExample.Server.model;

import mycode.model.BaseModel;

public class ResponseModel extends BaseModel {
	private String magicid;
	private String msgid;
    
    public String getMagicid() {
        return magicid;
    }

    public void setMagicid(String magicid) {
        this.magicid = magicid;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String toResponseString() {
        return "magicid="+ magicid + "\nmsgid=" + msgid + "\n";
    }
}
