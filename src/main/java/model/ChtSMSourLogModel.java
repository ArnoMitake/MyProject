package model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;

public class ChtSMSourLogModel {
    public ChtSMSourLogModel() {
    }

    private String date;
    private String time;
    private String ip;
    private String partKey;
    private String mid;
    private String sessionId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPartKey() {
        return partKey;
    }

    public void setPartKey(String partKey) {
        this.partKey = partKey;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    /**
     * 取得toString()需要忽略不顯示的欄位清單
     *
     * @return
     */
    protected String[] getExcludeFields() {
        return null;
    }

    @Override
    public String toString() {
        // 改寫本來的toString再前面加上\r\n讓work_detail log看起來較清楚
        return new StringBuffer("\r\n")
                .append((new ReflectionToStringBuilder(this,
                        ToStringStyle.SHORT_PREFIX_STYLE) {
                    protected boolean accept(Field f) {
                        try {
                            return super.accept(f)
                                    && f.get(getObject()) != null; // 欄位在忽略清單或是Null則不顯示
                        } catch (IllegalArgumentException e) {
                            // ignore
                        } catch (IllegalAccessException e) {
                            // ignore
                        }
                        return false;
                    }
                }).setExcludeFieldNames(getExcludeFields()).toString())
                .toString();
    }
}
