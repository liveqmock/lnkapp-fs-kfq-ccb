package org.fbi.fskfq.processor;

import org.fbi.fskfq.domain.tps.txn.TpsTia9905;
import org.fbi.fskfq.domain.tps.txn.TpsToa9906;
import org.fbi.fskfq.helper.ProjectConfigManager;
import org.fbi.linking.processor.ProcessorContext;
import org.fbi.linking.processor.ProcessorException;
import org.fbi.linking.processor.standprotocol10.Stdp10ProcessorRequest;
import org.fbi.linking.processor.standprotocol10.Stdp10ProcessorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by zhanrui on 13-12-31.
 * ǩ�����ף��Զ�����
 */
public class T9905Processor extends AbstractTxnProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void doRequest(Stdp10ProcessorRequest request, Stdp10ProcessorResponse response) throws ProcessorException, IOException {
        TpsTia9905 tpsTia = new TpsTia9905();
        tpsTia.Body.Object.Record.user_code = ProjectConfigManager.getInstance().getProperty("tps.txn.login.user_code");
        tpsTia.Body.Object.Record.password = ProjectConfigManager.getInstance().getProperty("tps.txn.login.password");
        tpsTia.Body.Object.Record.new_password = "";
        tpsTia.getHeader().setSrc(ProjectConfigManager.getInstance().getProperty("tps.txn.login.src"));
        tpsTia.getHeader().setDes(ProjectConfigManager.getInstance().getProperty("tps.txn.login.des"));
        tpsTia.getHeader().setDataType("9905");

        byte[] sendTpsBuf;

        try {
            sendTpsBuf = generateTpsTxMsgHeader(tpsTia, request);
            String dataType = tpsTia.getHeader().getDataType();
            byte[] recvTpsBuf = processThirdPartyServer(sendTpsBuf, dataType);
            String recvTpsMsg = new String(recvTpsBuf, "GBK");

            String rtnDataType = substr(recvTpsMsg, "<dataType>", "</dataType>").trim();
            if ("9906".equals(rtnDataType)) { //ҵ������������ 9906
                int authLen = Integer.parseInt(new String(recvTpsBuf, 51, 3));
                String msgdata = new String(recvTpsBuf, 69 + authLen, recvTpsBuf.length - 69 - authLen);
                logger.info("===���������������ر���(ҵ����Ϣ��)��\n" + msgdata);
                TpsToa9906 tpsToa9906 = new TpsToa9906();
                tpsToa9906 = (TpsToa9906) tpsToa9906.toToa(msgdata);

                //������Ȩ����Ϣ
                ProcessorContext context = request.getProcessorContext();
                context.setAttribute(CONTEXT_TPS_AUTHCODE, tpsToa9906.Body.Object.Record.accredit_code);
            } else {
                //TODO
                logger.error("ǩ�����׷����쳣.");
                throw new RuntimeException("ǩ�����׷����쳣.");
            }
        } catch (Exception e) {
            logger.error("�������������ͨѶ�����쳣.", e);
            throw new RuntimeException("�������������ͨѶ�����쳣.",e);
        }
    }


}
