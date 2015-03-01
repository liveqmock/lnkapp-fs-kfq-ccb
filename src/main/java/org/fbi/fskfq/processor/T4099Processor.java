package org.fbi.fskfq.processor;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.fbi.fskfq.domain.cbs.T4099Request.CbsTia4099;
import org.fbi.fskfq.domain.cbs.T4099Response.CbsToa4099;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1534099 ���ݵ��Ų�ѯ�ɿ��Ϣ(�ѽɿ�δ����) for ��������
 * zhanrui
 * 20140114
 */
public class T4099Processor extends AbstractTxnProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doRequest(Stdp10ProcessorRequest request, Stdp10ProcessorResponse response) throws ProcessorException, IOException {
        CbsTia4099 tia;
        try {
            tia = getCbsTia(request.getRequestBody());
            logger.info("��ɫҵ��ƽ̨������TIA:" + tia.toString());
        } catch (Exception e) {
            logger.error("��ɫҵ��ƽ̨�����Ľ�������.", e);
            marshalAbnormalCbsResponse(TxnRtnCode.CBSMSG_UNMARSHAL_FAILED, null, response);
            return;
        }


        //��ȡ�������ݿ���Ϣ
        List<FsKfqPaymentInfo> infos = selectPayoffPaymentInfos(tia.getBillNo());

        if (infos.size() == 1) {
            String cbsRespMsg = generateCbsRespMsg(infos.get(0));
            response.setHeader("rtnCode", TxnRtnCode.TXN_EXECUTE_SECCESS.getCode());
            response.setResponseBody(cbsRespMsg.getBytes(response.getCharacterEncoding()));
        }else if (infos.size() == 0) {
            marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, "δ��ѯ����ؼ�¼", response);
        } else {
            marshalAbnormalCbsResponse(TxnRtnCode.TXN_EXECUTE_FAILED, "�ɿ״̬����", response);
        }
    }

    private String generateCbsRespMsg(FsKfqPaymentInfo paymentInfo) {
        CbsToa4099 cbsToa = new CbsToa4099();
        FbiBeanUtils.copyProperties(paymentInfo, cbsToa);

        String cbsRespMsg = "";
        Map<String, Object> modelObjectsMap = new HashMap<String, Object>();
        modelObjectsMap.put(cbsToa.getClass().getName(), cbsToa);
        SeperatedTextDataFormat cbsDataFormat = new SeperatedTextDataFormat(cbsToa.getClass().getPackage().getName());
        try {
            cbsRespMsg = (String) cbsDataFormat.toMessage(modelObjectsMap);
        } catch (Exception e) {
            throw new RuntimeException("��ɫƽ̨����ת��ʧ��.", e);
        }
        return cbsRespMsg;

    }


    //����CBS������
    private CbsTia4099 getCbsTia(byte[] body) throws Exception {
        CbsTia4099 tia = new CbsTia4099();
        SeperatedTextDataFormat starringDataFormat = new SeperatedTextDataFormat(tia.getClass().getPackage().getName());
        tia = (CbsTia4099) starringDataFormat.fromMessage(new String(body, "GBK"), "CbsTia4099");
        return tia;
    }



    //=======ҵ���߼�����=================================================
    //�����ѽɿ�δ�����Ľɿ��¼
    private List<FsKfqPaymentInfo>  selectPayoffPaymentInfos(String billNo) {
        SqlSessionFactory sqlSessionFactory = MybatisFactory.ORACLE.getInstance();
        FsKfqPaymentInfoMapper mapper;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            mapper = session.getMapper(FsKfqPaymentInfoMapper.class);
            FsKfqPaymentInfoExample example = new FsKfqPaymentInfoExample();
            example.createCriteria()
                    .andBillNoEqualTo(billNo)
                    .andLnkBillStatusEqualTo(BillStatus.PAYOFF.getCode());
            return  mapper.selectByExample(example);
        }
    }
}
