package org.fbi.fskfq.domain.tps.txn;

/**
 * ͨ�����׻�ִ
 */

public class TpsToa9000 {
    /*
    ori_datatype	ԭ��������	NString	4		M
    ori_send_orgcode	ԭ���𷽱���	NString	[1,15]		M
    ori_entrust_date	ԭί������	Date		����������	M
    result	����������	String	4		M
    add_word	����	GBString	[1,60]		O
     */
    private String oriDatatype = "";
    private String oriSendOrgcode = "";
    private String oriEntrustDate = "";
    private String result = "";
    private String addWord = "";

    public String getOriDatatype() {
        return oriDatatype;
    }

    public void setOriDatatype(String oriDatatype) {
        this.oriDatatype = oriDatatype;
    }

    public String getOriSendOrgcode() {
        return oriSendOrgcode;
    }

    public void setOriSendOrgcode(String oriSendOrgcode) {
        this.oriSendOrgcode = oriSendOrgcode;
    }

    public String getOriEntrustDate() {
        return oriEntrustDate;
    }

    public void setOriEntrustDate(String oriEntrustDate) {
        this.oriEntrustDate = oriEntrustDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAddWord() {
        return addWord;
    }

    public void setAddWord(String addWord) {
        this.addWord = addWord;
    }
}
