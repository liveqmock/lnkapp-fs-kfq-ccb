package org.fbi.fskfq.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * ������������ ����E��ͨ client
 * User: zhanrui
 * Date: 13-11-27
 */
public class TpsSocketClient {
    private String ip;
    private int port;
    private int timeout = 30000; //��ʱʱ�䣺ms  ���ӳ�ʱ�����ʱͳһ

    public TpsSocketClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public byte[] call(byte[] sendbuf) throws Exception {
        byte[] recvbuf = null;

        InetAddress addr = InetAddress.getByName(ip);
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(addr, port), timeout);
            //socket.setSendBufferSize(100);
            socket.setSoTimeout(timeout);

            OutputStream os = socket.getOutputStream();
            os.write(sendbuf);
            os.flush();

            InputStream is = socket.getInputStream();
            recvbuf = new byte[8];
            int readNum = is.read(recvbuf);
            if (readNum == -1) {
                throw new RuntimeException("�����������ѹر�!");
            }
            if (readNum < 8) {
                throw new RuntimeException("��ȡ����ͷ���Ȳ��ִ���...");
            }
            int msgLen = Integer.parseInt(new String(recvbuf).trim());
            recvbuf = new byte[msgLen - 8];

            //TODO
            Thread.sleep(500);

            readNum = is.read(recvbuf);   //������
            if (readNum != msgLen - 8) {
                throw new RuntimeException("���ĳ��ȴ���,����ͷָʾ����:[" + msgLen + "], ʵ�ʻ�ȡ����:[" + readNum +"]");
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                //
            }
        }
        //System.out.println("---:" +  new String(recvbuf,"GBK"));
        return recvbuf;
    }


    public static void main(String... argv) throws UnsupportedEncodingException {
        TpsSocketClient mock = new TpsSocketClient("127.0.0.1", 60001);

        //1070�����ʵǼ�Ԥ����
        String msg = "" +
                "1070" + //������
                "02" + //���д���	2	CHAR	���д���ͳһʹ��01
                "1111111" + //��Ա��	7	CHAR	�Ҳ��ո�
                "22222" +  //������	5	CHAR	�Ҳ��ո�
                "3333" +   //������	4	CHAR	�Ҳ��ո�
                "44" +  //���ֱ̾��	2	CHAR
                "12345678901234567890123456789012"; //Ԥ�ǼǺ�	32	CHAR	�Ҳ��ո�

        String strLen = null;
        strLen = "" + (msg.getBytes("GBK").length + 4);
        String lpad = "";
        for (int i = 0; i < 4 - strLen.length(); i++) {
            lpad += "0";
        }
        strLen = lpad + strLen;


        byte[] recvbuf = new byte[0];
        try {
            recvbuf = mock.call((strLen + msg).getBytes("GBK"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.printf("���������أ�%s\n", new String(recvbuf, "GBK"));
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
