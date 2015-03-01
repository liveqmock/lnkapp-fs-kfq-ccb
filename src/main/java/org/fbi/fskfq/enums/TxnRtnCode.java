package org.fbi.fskfq.enums;

import java.util.Hashtable;

/**
 * ҵ���׷�����
 */
public enum TxnRtnCode implements EnumApp {

    TXN_EXECUTE_SECCESS("0000", "�������"),
    TXN_PAY_REPEATED("0001", "��Ʊ���ѽɿ�"),  //�뽻�������ͬ��������ɫƽ̨�����ظ��ɿ�

    TXN_EXECUTE_FAILED("1100", "����ʧ��"),

    CBSMSG_AUTHENTICATE_ILLEGAL("2000", "��ɫҵ��ƽ̨������֤ʧ��"),
    CBSMSG_UNMARSHAL_FAILED("2001", "��ɫҵ��ƽ̨���Ľ�������"),
    TPSMSG_MARSHAL_FAILED("2002", "�������������ɴ���"),
    TPSMSG_UNMARSHAL_FAILED("2003", "���������Ľ�������"),

    MSG_RECV_TIMEOUT("3000", "ͨ�ų�ʱ"),
    MSG_COMM_ERROR("3001", "ͨ���쳣"),

    MSG_AMT_ERROR("4001", "������"),
    UNKNOWN_EXCEPTION("9000", "����δ֪�쳣");

    private String code = null;
    private String title = null;
    private static Hashtable<String, TxnRtnCode> aliasEnums;

    TxnRtnCode(String code, String title) {
        this.init(code, title);
    }

    @SuppressWarnings("unchecked")
    private void init(String code, String title) {
        this.code = code;
        this.title = title;
        synchronized (this.getClass()) {
            if (aliasEnums == null) {
                aliasEnums = new Hashtable();
            }
        }
        aliasEnums.put(code, this);
        aliasEnums.put(title, this);
    }

    public static TxnRtnCode valueOfAlias(String alias) {
        return aliasEnums.get(alias);
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String toRtnMsg() {
        return this.code + "|" + this.title;
    }
}
