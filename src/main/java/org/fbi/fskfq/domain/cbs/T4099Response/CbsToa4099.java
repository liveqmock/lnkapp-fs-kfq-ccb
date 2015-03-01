package org.fbi.fskfq.domain.cbs.T4099Response;


import org.fbi.linking.codec.dataformat.annotation.DataField;
import org.fbi.linking.codec.dataformat.annotation.SeperatedTextMessage;

import java.math.BigDecimal;

/**
 * User: zhanrui
 * Date: 2014-1-14
 */
@SeperatedTextMessage(separator = "\\|", mainClass = true)
public class CbsToa4099 {
    @DataField(seq = 1)
    private String chrId;            //�ɿ���ID
    @DataField(seq = 2)
    private String billtypeCode;     //�ɿ�����ʽ����
    @DataField(seq = 3)
    private String billtypeName;     //�ɿ�����ʽ����
    @DataField(seq = 4)
    private String billNo;           //Ʊ��
    @DataField(seq = 5)
    private String makedate;         //��Ʊ����
    @DataField(seq = 6)
    private String setYear;          //ҵ�����
    @DataField(seq = 7)
    private BigDecimal billMoney;    //�տ���
    @DataField(seq = 8)
    private String operPayTlrid;     //�ɿ��Ա
    @DataField(seq = 9)
    private String operPayDate;      //�ɿ�����
    @DataField(seq = 10)
    private String operPayTime;      //�ɿ�ʱ��

    public String getChrId() {
        return chrId;
    }

    public void setChrId(String chrId) {
        this.chrId = chrId;
    }

    public String getBilltypeName() {
        return billtypeName;
    }

    public void setBilltypeName(String billtypeName) {
        this.billtypeName = billtypeName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getSetYear() {
        return setYear;
    }

    public void setSetYear(String setYear) {
        this.setYear = setYear;
    }

    public BigDecimal getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(BigDecimal billMoney) {
        this.billMoney = billMoney;
    }

    public String getOperPayTlrid() {
        return operPayTlrid;
    }

    public void setOperPayTlrid(String operPayTlrid) {
        this.operPayTlrid = operPayTlrid;
    }

    public String getOperPayDate() {
        return operPayDate;
    }

    public void setOperPayDate(String operPayDate) {
        this.operPayDate = operPayDate;
    }

    public String getOperPayTime() {
        return operPayTime;
    }

    public void setOperPayTime(String operPayTime) {
        this.operPayTime = operPayTime;
    }

    public String getBilltypeCode() {
        return billtypeCode;
    }

    public void setBilltypeCode(String billtypeCode) {
        this.billtypeCode = billtypeCode;
    }

    @Override
    public String toString() {
        return "CbsToa4099{" +
                "chrId='" + chrId + '\'' +
                ", billtypeCode='" + billtypeCode + '\'' +
                ", billtypeName='" + billtypeName + '\'' +
                ", billNo='" + billNo + '\'' +
                ", makedate='" + makedate + '\'' +
                ", setYear='" + setYear + '\'' +
                ", billMoney=" + billMoney +
                ", operPayTlrid='" + operPayTlrid + '\'' +
                ", operPayDate='" + operPayDate + '\'' +
                ", operPayTime='" + operPayTime + '\'' +
                '}';
    }
}
