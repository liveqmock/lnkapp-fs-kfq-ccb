package org.fbi.fskfq.domain.tps.base;

import java.io.Serializable;

public class TpsTiaHeader implements Serializable {
    private String src = "";                   // ���ͷ�����
    private String des = "";                   // ���շ�����
    private String dataType = "";
    private String msgId = "";                 // ���ı�ʶ��
    private String msgRef = "";                // ���Ĳο���  ����������ʱ���Ĳο���ͬ���ı�ʶ��
    private String workDate = "";              // ��������

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgRef() {
        return msgRef;
    }

    public void setMsgRef(String msgRef) {
        this.msgRef = msgRef;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    @Override
    public String toString() {
        return "TpsTiaHeader{" +
                "src='" + src + '\'' +
                ", des='" + des + '\'' +
                ", dataType='" + dataType + '\'' +
                ", msgId='" + msgId + '\'' +
                ", msgRef='" + msgRef + '\'' +
                ", workDate='" + workDate + '\'' +
                '}';
    }
}
