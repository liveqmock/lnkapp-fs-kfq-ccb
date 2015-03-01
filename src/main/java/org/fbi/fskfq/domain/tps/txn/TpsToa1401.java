package org.fbi.fskfq.domain.tps.txn;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.fbi.fskfq.domain.tps.base.TpsToa;
import org.fbi.fskfq.domain.tps.base.TpsToaHeader;
import org.fbi.fskfq.domain.tps.base.TpsToaSigns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Ӧ�����ݲ�ѯ��ִ
 */

public class TpsToa1401 extends TpsToa {
    public TpsToaHeader Head = new TpsToaHeader();
    public Body Body = new Body();
    public TpsToaSigns Signs = new TpsToaSigns();


    public static class Body implements Serializable {

        public Object Object = new Object();
    }

    public static class Object implements Serializable {

        public Record Record = new Record();
    }


    /*
    chr_id	�ɿ���ID	String	38		M
    billtype_code	�ɿ�����ʽ����	NString	[1,20]		M
    billtype_name	�ɿ�����ʽ����	GBString	[1,60]		M
    bill_no	Ʊ��	String	[1,42]		M
    makedate	��Ʊ����	Date			M
    ien_code	ִ�յ�λҵ����	NString	[1,42]		M
    ien_name	ִ�յ�λ����	GBString	[1,80]		M
    consign_ien_code	ί�е�λҵ����	NString	[1,42]		M
    consign_ien_name	ί�е�λ����	GBString	[1,80]		M
    pm_code	�ɿʽ����	NString	[1,42]		M
    pm_name	�ɿʽ����	GBString	[1,50]		M
    cheque_no	�����	String	[1,20]		O
    payer	�ɿ���ȫ��	GBString	[1,100]		M
    payerbank	�ɿ����˻�������	GBString	[1,100]		O
    payeraccount	�ɿ����˺�	String	[1,42]		O
    receiver	�տ���ȫ��	GBString	[1,100]		M
    receiverbank	�տ����˻�������	GBString	[1,100]		M
    receiveraccount	�տ����˺�	String	[1,42]		M
    verify_no	ȫƱ��У����	String	[1,20]		O
    rg_code	������	NString	[1,42]		O
    receivetype	���շ�ʽ	NString	1	1:ֱ�ӽ��
    2:���л��
    4:��������
    5:��������	M
    inputername	����������	GBString	[1,60]		O
    is_consign	�Ƿ�ί��	Boolean		false����
    true����	M
    lateflag	�Ƿ�¼	Boolean		false����
    true����	M
    nosource_ids	��������ID����	String	[1,1000]	��Ӧ�ڴ�������ID��ÿ����������ID�Զ��ŷָ�	O
    supplytemplet_id	��������ģ��ID	String	[1,1000]	��Ӧ����������ģ��ID��ÿ����������ģ��ID�Զ��ŷָ�	O
    remark	��ע	String	[1,200]		O
     */
    public static class Record implements Serializable {

        public String chr_id = "";
        public String billtype_code = "";
        public String billtype_name = "";
        public String bill_no = "";
        public String makedate = "";
        public String ien_code = "";
        public String ien_name = "";
        public String consign_ien_code = "";
        public String consign_ien_name = "";
        public String pm_code = "";
        public String pm_name = "";
        public String cheque_no = "";
        public String payer = "";
        public String payerbank = "";
        public String payeraccount = "";
        public String receiver = "";
        public String receiverbank = "";
        public String receiveraccount = "";
        public String verify_no = "";
        public String rg_code = "";
        public String receivetype = "";
        public String inputername = "";
        public String is_consign = "";
        public String lateflag = "";
        public String nosource_ids = "";
        public String supplytemplet_id = "";
        public String remark = "";

        public List<DetailRecord> Object = new ArrayList<DetailRecord>();

        @XStreamAlias("Record")
        public static class DetailRecord {
            /*
            chr_id	�ɿ�����ϸID
            main_id	�ɿ���ID
            in_bis_code	������Ŀҵ����
            in_bis_name	������Ŀ����
            measure	���յ�λ
            chargenum	��������
            chargestandard	�շѱ�׼
            chargemoney	������
            item_chkcode	��λ��ĿУ����
             */
            public String chr_id = "";
            public String main_id = "";
            public String in_bis_code = "";
            public String in_bis_name = "";
            public String measure = "";
            public String chargenum = "";
            public String chargestandard = "";
            public String chargemoney = "";
            public String item_chkcode = "";
        }

    }

    @Override
    public TpsToa toToa(String xml) {
        XStream xs = new XStream(new DomDriver());
        xs.processAnnotations(TpsToa1401.class);
        return (TpsToa1401) xs.fromXML(xml);
    }

}
