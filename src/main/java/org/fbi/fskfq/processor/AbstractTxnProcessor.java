package org.fbi.fskfq.processor;

import org.apache.commons.lang.StringUtils;
import org.fbi.fskfq.domain.cbs.T9999Response.TOA9999;
import org.fbi.fskfq.domain.tps.base.TpsTia;
import org.fbi.fskfq.domain.tps.base.TpsToaXmlBean;
import org.fbi.fskfq.domain.tps.txn.TpsToa9910;
import org.fbi.fskfq.enums.TxnRtnCode;
import org.fbi.fskfq.helper.ProjectConfigManager;
import org.fbi.fskfq.helper.TpsSocketClient;
import org.fbi.fskfq.internal.AppActivator;
import org.fbi.linking.codec.dataformat.SeperatedTextDataFormat;
import org.fbi.linking.processor.ProcessorException;
import org.fbi.linking.processor.standprotocol10.Stdp10Processor;
import org.fbi.linking.processor.standprotocol10.Stdp10ProcessorRequest;
import org.fbi.linking.processor.standprotocol10.Stdp10ProcessorResponse;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: zhanrui
 * Date: 13-12-18
 * Time: ����6:16
 */
public abstract class AbstractTxnProcessor extends Stdp10Processor {
    protected static String CONTEXT_TPS_AUTHCODE = "CONTEXT_TPS_AUTHCODE";
    protected static String TPS_ENCODING = "GBK";  //���������������뷽ʽ
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //protected static String tps_authcode = "";

    @Override
    public void service(Stdp10ProcessorRequest request, Stdp10ProcessorResponse response) throws ProcessorException, IOException {
        String txnCode = request.getHeader("txnCode");
        String tellerId = request.getHeader("tellerId");
        if (StringUtils.isEmpty(tellerId)) {
            tellerId = "TELLERID";
        }

        try {
            MDC.put("txnCode", txnCode);
            MDC.put("tellerId", tellerId);
            logger.info("��ɫƽ̨������:" + request.toString());
            doRequest(request, response);
            logger.info("��ɫƽ̨��Ӧ����:" + response.toString());
        }catch (Exception e){
            response.setHeader("rtnCode", TxnRtnCode.TXN_EXECUTE_FAILED.getCode());
            throw new RuntimeException(e);
        } finally {
            MDC.remove("txnCode");
            MDC.remove("tellerId");
        }
    }

    abstract protected void doRequest(Stdp10ProcessorRequest request, Stdp10ProcessorResponse response) throws ProcessorException, IOException;

    //���cbs���׳ɹ�����
    protected void marshalSuccessTxnCbsResponse(Stdp10ProcessorResponse response) {
        String msg = "���׳ɹ�";
        try {
            response.setHeader("rtnCode", TxnRtnCode.TXN_EXECUTE_SECCESS.getCode());
            response.setResponseBody(msg.getBytes(response.getCharacterEncoding()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("�������", e);
        }
    }
    //���cbs�쳣����
    protected void marshalAbnormalCbsResponse(TxnRtnCode txnRtnCode, String errMsg, Stdp10ProcessorResponse response) {
        if (errMsg == null) {
            errMsg = txnRtnCode.getTitle();
        }
        if(errMsg.length()>17){
            errMsg = errMsg.substring(0,17);
        }
        String msg = getErrorRespMsgForStarring(errMsg);
        response.setHeader("rtnCode", txnRtnCode.getCode());
        try {
            response.setResponseBody(msg.getBytes(response.getCharacterEncoding()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("�������", e);
        }
    }

    //��ͳһ�Ĵ�����Ӧ���� txtMsg
    private String getErrorRespMsgForStarring(String errMsg) {
        TOA9999 toa = new TOA9999();
        toa.setErrMsg(errMsg);
        String starringRespMsg;
        Map<String, Object> modelObjectsMap = new HashMap<String, Object>();
        modelObjectsMap.put(toa.getClass().getName(), toa);
        SeperatedTextDataFormat starringDataFormat = new SeperatedTextDataFormat(toa.getClass().getPackage().getName());
        try {
            starringRespMsg = (String) starringDataFormat.toMessage(modelObjectsMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return starringRespMsg;
    }

    //���ݵ������������ķ�����������ļ��л�ȡ��Ӧ����Ϣ
    private String getRtnMsg(String rtnCode) {
        BundleContext bundleContext = AppActivator.getBundleContext();
        URL url = bundleContext.getBundle().getEntry("rtncode.properties");

        Properties props = new Properties();
        try {
            props.load(url.openConnection().getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("�����������ļ���������", e);
        }
        String property = props.getProperty(rtnCode);
        if (property == null) {
            property = "δ�����Ӧ�Ĵ�����Ϣ(������:" + rtnCode + ")";
        }
        return property;
    }

    //���ɵ�����������ͨѶ����ͷ
    protected byte[] generateTpsTxMsgHeader(TpsTia tpstia, Stdp10ProcessorRequest request) throws UnsupportedEncodingException {
        String isSign = "0";

        String authCode = (String)request.getProcessorContext().getAttribute(CONTEXT_TPS_AUTHCODE);
        if (StringUtils.isEmpty(authCode)) {
            authCode = ProjectConfigManager.getInstance().getProperty("authCode");
        }

        //String authCode = this.tps_authcode;
        if (StringUtils.isEmpty(authCode)) {
            authCode = ProjectConfigManager.getInstance().getProperty("authCode");
        }
        String authLen = "" + authCode.length();
        String reserve = "               ";

        String sendTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // �����ĵ��е���������Ϊ������
        String msg = StringUtils.leftPad(tpstia.getHeader().getDataType(), 6, " ")
                + StringUtils.leftPad(tpstia.getHeader().getSrc(), 15, " ")
                + StringUtils.leftPad(tpstia.getHeader().getDes(), 15, " ")
                + sendTime
                + isSign
                + StringUtils.leftPad(authLen, 3, "0")
                + reserve
                + authCode
                + tpstia.toString();

        int msglength = msg.getBytes("GBK").length + 8;
        String len = StringUtils.leftPad("" + msglength, 8, "0");

        byte[] buffer = new byte[msglength];
        System.arraycopy(len.getBytes(), 0, buffer, 0, 8);
        System.arraycopy(msg.getBytes("GBK"), 0, buffer, 8, msglength - 8);

        return buffer;
    }

    //���ɵ�����������ҵ����ͷ
    protected void generateTpsBizMsgHeader(TpsTia tpstia, String dataType, Stdp10ProcessorRequest request) {
        //������ͷ TODO ȷ��msgId�ĳ���
        tpstia.getHeader().setMsgId(request.getHeader("txnTime") + request.getHeader("serialNo"));
        tpstia.getHeader().setMsgRef(request.getHeader("serialNo"));
        tpstia.getHeader().setWorkDate(request.getHeader("txnTime").substring(0, 8));

        tpstia.getHeader().setSrc(ProjectConfigManager.getInstance().getProperty("tps.txn.login.src"));
        tpstia.getHeader().setDes(ProjectConfigManager.getInstance().getProperty("tps.txn.login.des"));
        tpstia.getHeader().setDataType(dataType);
    }


    //�������������ɸ��ݽ��׺����ò�ͬ�ĳ�ʱʱ��
    protected byte[] processThirdPartyServer(byte[] sendTpsBuf, String txnCode) throws Exception {
        String servIp = ProjectConfigManager.getInstance().getProperty("tps.server.ip");
        int servPort = Integer.parseInt(ProjectConfigManager.getInstance().getProperty("tps.server.port"));
        TpsSocketClient client = new TpsSocketClient(servIp, servPort);

        String timeoutCfg = ProjectConfigManager.getInstance().getProperty("tps.server.timeout.txn." + txnCode);
        if (timeoutCfg != null) {
            int timeout = Integer.parseInt(timeoutCfg);
            client.setTimeout(timeout);
        } else {
            timeoutCfg = ProjectConfigManager.getInstance().getProperty("tps.server.timeout");
            if (timeoutCfg != null) {
                int timeout = Integer.parseInt(timeoutCfg);
                client.setTimeout(timeout);
            }
        }

        logger.info("������������������:" + new String(sendTpsBuf, TPS_ENCODING));
        byte[] rcvTpsBuf = client.call(sendTpsBuf);
        logger.info("��������������Ӧ����:" + new String(rcvTpsBuf, TPS_ENCODING));
        return rcvTpsBuf;
    }

    //һ�㼼�����쳣���Ĵ��� 9910
    protected TpsToa9910 transXmlToBeanForTps9910(byte[] buf) {
        int authLen = Integer.parseInt(new String(buf, 51, 3));
        String msgdata = new String(buf, 69 + authLen, buf.length - 69 - authLen);
        System.out.println("===XML�����壺\n" + msgdata);


        TpsToa9910 toa = new TpsToa9910();
        return (TpsToa9910) toa.toToa(msgdata);
    }

    protected TpsToaXmlBean transXmlToBeanForTps(byte[] buf) {
        String txnCode = new String(buf, 0, 6).trim();
        int authLen = Integer.parseInt(new String(buf, 51, 3));
        String msgdata = new String(buf, 69 + authLen, buf.length - 69 - authLen);

        System.out.println("===XML�����壺\n" + msgdata);

        TpsToaXmlBean toa = new TpsToaXmlBean();
        toa = (TpsToaXmlBean) toa.toToa(msgdata);
        return toa;
    }

    protected String substr(String content, String startStr, String endStr) {
        int length = startStr.length();
        int start = content.indexOf(startStr) + length;
        int end = content.indexOf(endStr);
        return content.substring(start, end);
    }

    //======================
}
