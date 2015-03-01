package org.fbi.fskfq.processor;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.fbi.fskfq.domain.cbs.T4040Request.CbsTia4040;
import org.fbi.fskfq.domain.tps.base.TpsTia;
import org.fbi.fskfq.domain.tps.base.TpsToaXmlBean;
import org.fbi.fskfq.domain.tps.txn.TpsTia2409;
import org.fbi.fskfq.domain.tps.txn.TpsTia2458;
import org.fbi.fskfq.domain.tps.txn.TpsToa9000;
import org.fbi.fskfq.domain.tps.txn.TpsToa9910;
import org.fbi.fskfq.enums.BillStatus;
import org.fbi.fskfq.enums.TxnRtnCode;
import org.fbi.fskfq.helper.FbiBeanUtils;
import org.fbi.fskfq.helper.MybatisFactory;
import org.fbi.fskfq.repository.dao.FsKfqPaymentInfoMapper;
import org.fbi.fskfq.repository.dao.common.CommonMapper;
import org.fbi.fskfq.repository.model.FsKfqPaymentInfo;
import org.fbi.fskfq.repository.model.FsKfqPaymentInfoExample;
import org.fbi.linking.codec.dataformat.SeperatedTextDataFormat;
import org.fbi.linking.processor.ProcessorException;
import org.fbi.linking.processor.standprotocol10.Stdp10ProcessorRequest;
import org.fbi.linking.processor.standprotocol10.Stdp10ProcessorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanrui on 13-12-31.
 * ��������
 * �����ѽɿ�Ļ���Ʊ��ɾ����¼���δ�ɿ���ֹ�Ʊ    �ѽɿ���ֹ�Ʊ���Ƚ��г����ɿ��
 * ���ǣ��ڽ��У��ѽɿ���ֹ�ƱƫƫҲҪ�ô˽���һ�ξ���ɳ����ɿ��ɾ������������ע�⿴��ע��
 */
public class T4040Processor extends AbstractTxnProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void doRequest(Stdp10ProcessorRequest request, Stdp10ProcessorResponse response) throws ProcessorException, IOException {
        CbsTia4040 tia;
        try {
            tia = getCbsTia(request.getRequestBody());
        } catch (Exception e) {
            logger.error("��ɫҵ��ƽ̨�����Ľ�������.", e);
            marshalAbnormalCbsResponse(TxnRtnCode.CBSMSG_UNMARSHAL_FAILED, null, response);
            return;
        }

        FsKfqPaymentInfo paymentInfo;

        FsKfqPaymentInfo handBillInfo = selectHandNoPayPaymentInfoFromDB(tia.getBillNo());
        if (handBillInfo != null) {
            paymentInfo = handBillInfo;
        } else {
            //��鱾�����ݿ���Ϣ
            paymentInfo = selectPayoffPaymentInfoFromDB(tia.getBillNo());
            if (paymentInfo == null) {
                marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, "�����ڸýɿ�Ʊ��", response);
                return;
            }
        }

        //����������
        String manualFlag = paymentInfo.getManualFlag();
        if (StringUtils.isEmpty(manualFlag)) {
            logger.error("�ֹ�Ʊ��־����Ϊ��" + paymentInfo.getBillNo());
            marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, "�ֹ�Ʊ��־����Ϊ��", response);
            return;
        }
        int num = 1;
        if ("1".equals(paymentInfo.getManualFlag()) && "1".equals(paymentInfo.getLnkBillStatus())) { // �ѽɿ��ֹ�Ʊ �������ν��׼�����ɳ����ɿ��ɾ��
            num = 2;
        }
        for (int i = 0; i < num; i++) {
            doCcbRequest(tia, request, response, paymentInfo);
        }

    }

    private void doCcbRequest(CbsTia4040 tia, Stdp10ProcessorRequest request, Stdp10ProcessorResponse response, FsKfqPaymentInfo paymentInfo) {


        TpsToaXmlBean tpsToa = processTpsTx(tia, request, response, paymentInfo);
        if (tpsToa == null) { //�����쳣
            return;
        }

        //�ж�����
        String result = tpsToa.getMaininfoMap().get("RESULT");
        if (result != null) { //�쳣ҵ����
            TpsToa9000 tpsToa9000 = new TpsToa9000();
            try {
                FbiBeanUtils.copyProperties(tpsToa.getMaininfoMap(), tpsToa9000, true);
                marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, tpsToa9000.getAddWord(), response);
            } catch (Exception e) {
                logger.error("��������������Ӧ���Ľ����쳣.", e);
                marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, "��������������Ӧ���Ľ����쳣.", response);
            }
        } else { //���������߼�����
            try {
                String rtnStatus = tpsToa.getMaininfoMap().get("SUCC_CODE");
                String chr_id = tpsToa.getMaininfoMap().get("CHR_ID");
                String bill_no = tpsToa.getMaininfoMap().get("BILL_NO");
                if (!paymentInfo.getChrId().equals(chr_id) || !paymentInfo.getBillNo().equals(bill_no)) {
                    marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, "���Ų�����", response);
                } else {
                    if (!"OK".equals(rtnStatus)) {
                        marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, rtnStatus, response);
                    } else {
                        processTxn(paymentInfo, request);
                        marshalSuccessTxnCbsResponse(response);
                    }
                }
            } catch (Exception e) {
                marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, e.getMessage(), response);
                logger.error("ҵ����ʧ��.", e);
            }
        }
    }

    //������ͨѶ����
    private TpsToaXmlBean processTpsTx(CbsTia4040 tia, Stdp10ProcessorRequest request, Stdp10ProcessorResponse response, FsKfqPaymentInfo paymentInfo) {
        TpsTia tpsTia;
        // δ�ɿ���ֹ�Ʊִ��2458���� �ѽɿ���ֹ�Ʊ����ִ�г����ɿ�2409
        if ("1".equals(paymentInfo.getManualFlag()) && "0".equals(paymentInfo.getLnkBillStatus())) { //δ�ɿ��ֹ�Ʊ

            tpsTia = assembleTpsRequestBean_2458(tia, request);
            TpsTia2458.BodyRecord record = ((TpsTia2458.Body) tpsTia.getBody()).getObject().getRecord();
            // �����������������
            record.setBill_money(qryBillMoney(paymentInfo.getPkid()).toString());
            record.setRg_code(paymentInfo.getRgCode());
        } else {
            tpsTia = assembleTpsRequestBean_2409(tia, request);
        }
        TpsToaXmlBean tpsToa = new TpsToaXmlBean();

        byte[] sendTpsBuf;
        try {
            sendTpsBuf = generateTpsTxMsgHeader(tpsTia, request);
        } catch (Exception e) {
            logger.error("���ɵ�����������������ʱ����.", e);
            response.setHeader("rtnCode", TxnRtnCode.TPSMSG_MARSHAL_FAILED.getCode());
            return tpsToa;
        }

        try {
            String dataType = tpsTia.getHeader().getDataType();
            byte[] recvTpsBuf = processThirdPartyServer(sendTpsBuf, dataType);
            String recvTpsMsg = new String(recvTpsBuf, "GBK");

            String rtnDataType = substr(recvTpsMsg, "<dataType>", "</dataType>").trim();
            if ("9910".equals(rtnDataType)) { //�������쳣���� 9910
                TpsToa9910 tpsToa9910 = transXmlToBeanForTps9910(recvTpsBuf);
                String errType = tpsToa9910.Body.Object.Record.result;
                String errMsg = tpsToa9910.Body.Object.Record.add_word;
                if (StringUtils.isNotEmpty(errType) && "E301".equals(errType)) { //����ǩ������
                    T9905Processor t9905Processor = new T9905Processor();
                    t9905Processor.doRequest(request, response);
                    errMsg = "��Ȩ��䶯,�����·�����.";
                } else { //����ǰ̨������Ϣ
                    if (StringUtils.isEmpty(errMsg)) errMsg = "��������:�������쳣";
                    else errMsg = "��������:" + errMsg;
                }
                marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, errMsg, response);
                logger.info("===���������������ر���(�쳣ҵ����Ϣ��)��\n" + tpsToa9910.toString());
                return null;
            } else { //ҵ�����������쳣���� 1402
                tpsToa = transXmlToBeanForTps(recvTpsBuf);
            }
        } catch (SocketTimeoutException e) {
            logger.error("�������������ͨѶ����ʱ.", e);
            response.setHeader("rtnCode", TxnRtnCode.MSG_RECV_TIMEOUT.getCode());
            return null;
        } catch (Exception e) {
            logger.error("�������������ͨѶ�����쳣.", e);
            response.setHeader("rtnCode", TxnRtnCode.MSG_COMM_ERROR.getCode());
            return null;
        }

        return tpsToa;
    }

    //====
    //����Starring������
    private CbsTia4040 getCbsTia(byte[] body) throws Exception {
        CbsTia4040 tia = new CbsTia4040();
        SeperatedTextDataFormat starringDataFormat = new SeperatedTextDataFormat(tia.getClass().getPackage().getName());
        tia = (CbsTia4040) starringDataFormat.fromMessage(new String(body, "GBK"), "CbsTia4040");
        return tia;
    }

    //����δ�ɿ���ֹ�Ʊ�ɿ��¼
    private FsKfqPaymentInfo selectHandNoPayPaymentInfoFromDB(String billNo) {
        SqlSessionFactory sqlSessionFactory = MybatisFactory.ORACLE.getInstance();
        FsKfqPaymentInfoMapper mapper;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            mapper = session.getMapper(FsKfqPaymentInfoMapper.class);
            FsKfqPaymentInfoExample example = new FsKfqPaymentInfoExample();
            example.createCriteria()
                    .andBillNoEqualTo(billNo)
                    .andLnkBillStatusEqualTo(BillStatus.INIT.getCode())
                    .andManualFlagEqualTo("1");

            List<FsKfqPaymentInfo> infos = mapper.selectByExample(example);
            if (infos.size() == 0) {
                return null;
            }
            if (infos.size() != 1) {
                throw new RuntimeException("��¼״̬����.");
            }
            return infos.get(0);
        }
    }

    //�����ѽɿ�δ�����Ľɿ��¼
    private FsKfqPaymentInfo selectPayoffPaymentInfoFromDB(String billNo) {
        SqlSessionFactory sqlSessionFactory = MybatisFactory.ORACLE.getInstance();
        FsKfqPaymentInfoMapper mapper;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            mapper = session.getMapper(FsKfqPaymentInfoMapper.class);
            FsKfqPaymentInfoExample example = new FsKfqPaymentInfoExample();
            example.createCriteria()
                    .andBillNoEqualTo(billNo)
                    .andLnkBillStatusEqualTo(BillStatus.PAYOFF.getCode());
            List<FsKfqPaymentInfo> infos = mapper.selectByExample(example);
            if (infos.size() == 0) {
                return null;
            }
            if (infos.size() != 1) { //ͬһ���ɿ�ţ��ѽɿ�δ�������ڱ���ֻ�ܴ���һ����¼
                throw new RuntimeException("��¼״̬����.");
            }
            return infos.get(0);
        }
    }

    //���ɵ����������Ķ�ӦBEAN
    private TpsTia assembleTpsRequestBean_2409(CbsTia4040 cbstia, Stdp10ProcessorRequest request) {
        TpsTia2409 tpstia = new TpsTia2409();
        TpsTia2409.BodyRecord record = ((TpsTia2409.Body) tpstia.getBody()).getObject().getRecord();
        FbiBeanUtils.copyProperties(cbstia, record, true);

        generateTpsBizMsgHeader(tpstia, "2409", request);
        return tpstia;
    }

    //�ֹ�Ʊ
    private TpsTia assembleTpsRequestBean_2458(CbsTia4040 cbstia, Stdp10ProcessorRequest request) {
        TpsTia2458 tpstia = new TpsTia2458();
        TpsTia2458.BodyRecord record = ((TpsTia2458.Body) tpstia.getBody()).getObject().getRecord();
        FbiBeanUtils.copyProperties(cbstia, record, true);

        generateTpsBizMsgHeader(tpstia, "2458", request);
        return tpstia;
    }


    //=============
    private void processTxn(FsKfqPaymentInfo paymentInfo, Stdp10ProcessorRequest request) {
        SqlSessionFactory sqlSessionFactory = MybatisFactory.ORACLE.getInstance();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            paymentInfo.setOperCancelBankid(request.getHeader("branchId"));
            paymentInfo.setOperCancelTlrid(request.getHeader("tellerId"));
            paymentInfo.setOperCancelDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            paymentInfo.setOperCancelTime(new SimpleDateFormat("HHmmss").format(new Date()));
            paymentInfo.setOperCancelHostsn(request.getHeader("serialNo"));

/*
            paymentInfo.setHostBookFlag("1");
            paymentInfo.setHostChkFlag("0");
            paymentInfo.setFbBookFlag("1");
            paymentInfo.setFbChkFlag("0");
*/

//            paymentInfo.setAreaCode("KaiFaQu-FeiShui");
//            paymentInfo.setHostAckFlag("0");
            // �ѽɿ��ֹ�Ʊ���������ɿ�����ָ�δ�ɿ�״̬
            if ("1".equals(paymentInfo.getManualFlag()) && "1".equals(paymentInfo.getLnkBillStatus())) {
                paymentInfo.setLnkBillStatus(BillStatus.INIT.getCode()); // δ�ɿ�
                // ���������Ͳ����־�δ����
                paymentInfo.setHostBookFlag("0");
                paymentInfo.setFbBookFlag("0");
            } else {
                paymentInfo.setLnkBillStatus(BillStatus.CANCELED.getCode()); //�ѳ���
            }

            //TODO Ӧ��¼�������׵�������ˮ��

            FsKfqPaymentInfoMapper infoMapper = session.getMapper(FsKfqPaymentInfoMapper.class);
            infoMapper.updateByPrimaryKey(paymentInfo);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw new RuntimeException("ҵ���߼�����ʧ�ܡ�", e);
        } finally {
            session.close();
        }
    }

    private BigDecimal qryBillMoney(String pkid) {
        SqlSessionFactory sqlSessionFactory = MybatisFactory.ORACLE.getInstance();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            CommonMapper commonMapper = session.getMapper(CommonMapper.class);
            return commonMapper.qryBillMoney(pkid);
        }
    }
}
