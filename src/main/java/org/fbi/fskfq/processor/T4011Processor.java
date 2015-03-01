package org.fbi.fskfq.processor;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.fbi.fskfq.domain.cbs.T4011Request.CbsTia4011;
import org.fbi.fskfq.domain.tps.base.TpsTia;
import org.fbi.fskfq.domain.tps.base.TpsToaXmlBean;
import org.fbi.fskfq.domain.tps.txn.TpsTia2402;
import org.fbi.fskfq.domain.tps.txn.TpsToa9000;
import org.fbi.fskfq.domain.tps.txn.TpsToa9910;
import org.fbi.fskfq.enums.BillStatus;
import org.fbi.fskfq.enums.TxnRtnCode;
import org.fbi.fskfq.helper.FbiBeanUtils;
import org.fbi.fskfq.helper.MybatisFactory;
import org.fbi.fskfq.repository.dao.FsKfqPaymentInfoMapper;
import org.fbi.fskfq.repository.model.FsKfqPaymentInfo;
import org.fbi.fskfq.repository.model.FsKfqPaymentInfoExample;
import org.fbi.linking.codec.dataformat.SeperatedTextDataFormat;
import org.fbi.linking.processor.ProcessorException;
import org.fbi.linking.processor.standprotocol10.Stdp10ProcessorRequest;
import org.fbi.linking.processor.standprotocol10.Stdp10ProcessorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanrui on 13-12-31.
 * �ɿ��
 */
public class T4011Processor extends AbstractTxnProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void doRequest(Stdp10ProcessorRequest request, Stdp10ProcessorResponse response) throws ProcessorException, IOException {
        CbsTia4011 cbsTia;
        try {
            cbsTia = getCbsTia(request.getRequestBody());
        } catch (Exception e) {
            logger.error("��ɫҵ��ƽ̨�����Ľ�������.", e);
            marshalAbnormalCbsResponse(TxnRtnCode.CBSMSG_UNMARSHAL_FAILED, null, response);
            return;
        }

        //��鱾�����ݿ���Ϣ
        FsKfqPaymentInfo paymentInfo = selectNotCanceledPaymentInfoFromDB(cbsTia.getBillNo());
        if (paymentInfo == null) {
            marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, "��������ѯ����.", response);
            return;
        } else {
            String billStatus = paymentInfo.getLnkBillStatus();
            if (billStatus.equals(BillStatus.PAYOFF.getCode())) { //�ѽɿ�
                marshalAbnormalCbsResponse(TxnRtnCode.TXN_PAY_REPEATED, null, response);
                logger.info("===�˱ʽɿ�ѽɿ�.");
                return;
            }else if (!billStatus.equals(BillStatus.INIT.getCode())) {  //�ǳ�ʼ״̬
                marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, "�˱ʽɿ״̬����", response);
                logger.info("===�˱ʽɿ״̬����.");
                return;
            }
        }

        //����������
        TpsToaXmlBean tpsToa = processTpsTx(cbsTia, request, response);
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
                        processTxn(cbsTia, paymentInfo, request);
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
    private TpsToaXmlBean processTpsTx(CbsTia4011 cbsTia, Stdp10ProcessorRequest request, Stdp10ProcessorResponse response) {
        TpsTia tpsTia = assembleTpsRequestBean(cbsTia, request);
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
    private CbsTia4011 getCbsTia(byte[] body) throws Exception {
        CbsTia4011 tia = new CbsTia4011();
        SeperatedTextDataFormat starringDataFormat = new SeperatedTextDataFormat(tia.getClass().getPackage().getName());
        tia = (CbsTia4011) starringDataFormat.fromMessage(new String(body, "GBK"), "CbsTia4011");
        return tia;
    }

    //����δ�����Ľɿ��¼
    private FsKfqPaymentInfo selectNotCanceledPaymentInfoFromDB(String billNo) {
        SqlSessionFactory sqlSessionFactory = MybatisFactory.ORACLE.getInstance();
        FsKfqPaymentInfoMapper mapper;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            mapper = session.getMapper(FsKfqPaymentInfoMapper.class);
            FsKfqPaymentInfoExample example = new FsKfqPaymentInfoExample();
            example.createCriteria()
                    .andBillNoEqualTo(billNo)
                    .andLnkBillStatusNotEqualTo(BillStatus.CANCELED.getCode());
            List<FsKfqPaymentInfo> infos = mapper.selectByExample(example);
            if (infos.size() == 0) {
                return null;
            }
            if (infos.size() != 1) { //ͬһ���ɿ�ţ�δ�������ڱ���ֻ�ܴ���һ����¼
                throw new RuntimeException("��¼״̬����.");
            }
            return infos.get(0);
        }
    }

    //���ɵ����������Ķ�ӦBEAN
    private TpsTia assembleTpsRequestBean(CbsTia4011 cbstia, Stdp10ProcessorRequest request) {
        TpsTia2402 tpstia = new TpsTia2402();
        TpsTia2402.BodyRecord record = ((TpsTia2402.Body) tpstia.getBody()).getObject().getRecord();
        FbiBeanUtils.copyProperties(cbstia, record, true);

        generateTpsBizMsgHeader(tpstia, "2402", request);
        return tpstia;
    }


    //=============
    private void processTxn(CbsTia4011 cbsTia, FsKfqPaymentInfo paymentInfo, Stdp10ProcessorRequest request) {
        SqlSessionFactory sqlSessionFactory = MybatisFactory.ORACLE.getInstance();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            //�ɿ������Ϣ����ɫϵͳ���������ṩ
            paymentInfo.setBankIndate(cbsTia.getBankIndate());
            paymentInfo.setIncomestatus(cbsTia.getIncomestatus());
            paymentInfo.setBusinessId(cbsTia.getBusinessId());
            paymentInfo.setBillMoney(cbsTia.getBillMoney());
            paymentInfo.setPmCode(cbsTia.getPmCode());
            paymentInfo.setChequeNo(cbsTia.getChequeNo());
            paymentInfo.setSetYear(cbsTia.getSetYear());
            paymentInfo.setBilltypeCode(cbsTia.getBilltypeCode());

            //20141210  zhanrui ���������ֶ�
            paymentInfo.setReceiver(cbsTia.getReceiver());
            paymentInfo.setReceiveraccount(cbsTia.getReceiveraccount());
            paymentInfo.setReceiverbank(cbsTia.getReceiverbank());
            //20150105  ����������
            paymentInfo.setAreaCode(cbsTia.getAreaCode());

            //paymentInfo.setBusinessId(request.getHeader("serialNo"));
            paymentInfo.setOperPayBankid(request.getHeader("branchId"));
            paymentInfo.setOperPayTlrid(request.getHeader("tellerId"));
            paymentInfo.setOperPayDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            paymentInfo.setOperPayTime(new SimpleDateFormat("HHmmss").format(new Date()));
            paymentInfo.setOperPayHostsn(request.getHeader("serialNo"));

            paymentInfo.setHostBookFlag("1");
            paymentInfo.setHostChkFlag("0");
            paymentInfo.setFbBookFlag("1");
            paymentInfo.setFbChkFlag("0");

            //paymentInfo.setAreaCode("KaiFaQu-FeiShui");
            paymentInfo.setHostAckFlag("0");
            paymentInfo.setLnkBillStatus(BillStatus.PAYOFF.getCode()); //�ѽɿ�
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

}
