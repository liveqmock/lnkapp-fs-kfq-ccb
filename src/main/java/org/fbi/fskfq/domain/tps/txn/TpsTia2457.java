package org.fbi.fskfq.domain.tps.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.fbi.fskfq.domain.tps.base.TpsTia;
import org.fbi.fskfq.domain.tps.base.TpsTiaBody;
import org.fbi.fskfq.domain.tps.base.TpsTiaHeader;
import org.fbi.fskfq.domain.tps.base.TpsTiaSigns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * �ֹ�Ʊ�ɿ�
 */

@XStreamAlias("Root")
public class TpsTia2457 extends TpsTia {
    public TpsTiaHeader Head = new TpsTiaHeader();
    public Body Body = new Body();
    public TpsTiaSigns Signs = new TpsTiaSigns();

    @Override
    public TpsTiaHeader getHeader() {
        return Head;
    }

    @Override
    public TpsTiaBody getBody() {
        return Body;
    }

    @Override
    public TpsTiaSigns getSigns() {
        return Signs;
    }

    public static class Body extends TpsTiaBody{
        private BodyObject Object = new BodyObject();

        public BodyObject getObject() {
            return Object;
        }

        public void setObject(BodyObject object) {
            Object = object;
        }
    }

    public static class BodyObject implements Serializable {
        private BodyRecord Record = new BodyRecord();

        public BodyRecord getRecord() {
            return Record;
        }

        public void setRecord(BodyRecord record) {
            Record = record;
        }
    }

    public static class BodyRecord implements Serializable {
        /*
        rg_code	������
        billtype_code	�ɿ�����ʽ����
        bill_no	Ʊ��
        verify_no	ȫƱ��У����
        pm_code	�ɿʽ����
        ien_code	ִ�յ�λҵ����
        ien_name	ִ�յ�λ����
        consign_ien_code	ί�е�λ����
        consign_ien_name	ί�е�λ����
        bill_money	�տ���
        set_year	���
        bank_user	������
        Bank_no	���б���
        payer	�ɿ���
        payerbank	�ɿ��˿�����
        payeraccount	�ɿ����˺�
        receiver	�տ���ȫ��
        receiverbank	�տ����˻�������
        receiveraccount	�տ����˺�
        is_consign	�Ƿ�ί��
        remark	��ע
         */
        private String rg_code = "";
        private String billtype_code = "";
        private String bill_no = "";
        private String verify_no = "";
        private String pm_code = "";
        private String ien_code = "";
        private String ien_name = "";
        private String consign_ien_code = "";
        private String consign_ien_name = "";
        private String bill_money = "";
        private String set_year = "";
        private String bank_user = "";
        private String bank_no = "";
        private String payer = "";
        private String payerbank = "";
        private String payeraccount = "";
        private String receiver = "";
        private String receiverbank = "";
        private String receiveraccount = "";
        private String is_consign = "";
        private String remark = "";

        private List<DetailRecord> Object
                = new ArrayList<DetailRecord>();

        public String getRg_code() {
            return rg_code;
        }

        public void setRg_code(String rg_code) {
            this.rg_code = rg_code;
        }

        public String getBilltype_code() {
            return billtype_code;
        }

        public void setBilltype_code(String billtype_code) {
            this.billtype_code = billtype_code;
        }

        public String getBill_no() {
            return bill_no;
        }

        public void setBill_no(String bill_no) {
            this.bill_no = bill_no;
        }

        public String getVerify_no() {
            return verify_no;
        }

        public void setVerify_no(String verify_no) {
            this.verify_no = verify_no;
        }

        public String getPm_code() {
            return pm_code;
        }

        public void setPm_code(String pm_code) {
            this.pm_code = pm_code;
        }

        public String getIen_code() {
            return ien_code;
        }

        public void setIen_code(String ien_code) {
            this.ien_code = ien_code;
        }

        public String getIen_name() {
            return ien_name;
        }

        public void setIen_name(String ien_name) {
            this.ien_name = ien_name;
        }

        public String getConsign_ien_code() {
            return consign_ien_code;
        }

        public void setConsign_ien_code(String consign_ien_code) {
            this.consign_ien_code = consign_ien_code;
        }

        public String getConsign_ien_name() {
            return consign_ien_name;
        }

        public void setConsign_ien_name(String consign_ien_name) {
            this.consign_ien_name = consign_ien_name;
        }

        public String getBill_money() {
            return bill_money;
        }

        public void setBill_money(String bill_money) {
            this.bill_money = bill_money;
        }

        public String getSet_year() {
            return set_year;
        }

        public void setSet_year(String set_year) {
            this.set_year = set_year;
        }

        public String getBank_user() {
            return bank_user;
        }

        public void setBank_user(String bank_user) {
            this.bank_user = bank_user;
        }

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }

        public String getPayer() {
            return payer;
        }

        public void setPayer(String payer) {
            this.payer = payer;
        }

        public String getPayerbank() {
            return payerbank;
        }

        public void setPayerbank(String payerbank) {
            this.payerbank = payerbank;
        }

        public String getPayeraccount() {
            return payeraccount;
        }

        public void setPayeraccount(String payeraccount) {
            this.payeraccount = payeraccount;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getReceiverbank() {
            return receiverbank;
        }

        public void setReceiverbank(String receiverbank) {
            this.receiverbank = receiverbank;
        }

        public String getReceiveraccount() {
            return receiveraccount;
        }

        public void setReceiveraccount(String receiveraccount) {
            this.receiveraccount = receiveraccount;
        }

        public String getIs_consign() {
            return is_consign;
        }

        public void setIs_consign(String is_consign) {
            this.is_consign = is_consign;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public List<DetailRecord> getObject() {
            return Object;
        }

        public void setObject(List<DetailRecord> object) {
            Object = object;
        }

        @XStreamAlias("Record")
        public static class DetailRecord {
            /*
            in_bis_code	������Ŀҵ����
            in_bis_name	������Ŀ����
            chargenum	��������
            chargemoney	������
             */
            private String in_bis_code = "";
            private String in_bis_name = "";
            private String chargenum = "";
            private String chargemoney = "";

            public String getIn_bis_code() {
                return in_bis_code;
            }

            public void setIn_bis_code(String in_bis_code) {
                this.in_bis_code = in_bis_code;
            }

            public String getIn_bis_name() {
                return in_bis_name;
            }

            public void setIn_bis_name(String in_bis_name) {
                this.in_bis_name = in_bis_name;
            }

            public String getChargenum() {
                return chargenum;
            }

            public void setChargenum(String chargenum) {
                this.chargenum = chargenum;
            }

            public String getChargemoney() {
                return chargemoney;
            }

            public void setChargemoney(String chargemoney) {
                this.chargemoney = chargemoney;
            }
        }
    }

    @Override
    public String toString() {
        XmlFriendlyNameCoder replacer = new XmlFriendlyNameCoder("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(TpsTia2457.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }
}
