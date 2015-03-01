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

/**
 * Ӧ�����ݲ�ѯ����
 */

@XStreamAlias("Root")
public class TpsTia2401 extends TpsTia {
    private TpsTiaHeader Head = new TpsTiaHeader();
    private Body Body = new Body();
    private TpsTiaSigns Signs = new TpsTiaSigns();

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

    public static class Body extends TpsTiaBody {
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


    /*
        billtype_code	�ɿ�����ʽ����
        bill_no	Ʊ��
        verify_no	ȫƱ��У����
        bill_money	�տ���
        set_year	���
     */
    public static class BodyRecord implements Serializable {
        private String billtype_code = "";
        private String bill_no = "";
        private String verify_no = "";
        private String bill_money = "";
        private String set_year = "";

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
    }

    //==================================
    @Override
    public String toString() {
        XmlFriendlyNameCoder replacer = new XmlFriendlyNameCoder("$", "_");
        HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
        XStream xs = new XStream(hierarchicalStreamDriver);
        xs.processAnnotations(TpsTia2401.class);
        return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "\n" + xs.toXML(this);
    }
}
