package com.staffGauge.survey.cargo.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Component;

/**
 * Created by Mr.F on 2020/4/20
 */
@Component
public class CargoServiceProvider {

    public String getCargoService(String serverIp, int serverPort, String path) {
        TTransport transport = new TSocket(serverIp, serverPort);
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            Transmit.Client client = new Transmit.Client(protocol);

            //接口调用
            String rs = client.sayMsg(path);
            return rs;
        } catch (TTransportException e) {
            e.printStackTrace();
            return "数据读取错误";
        } catch (TException e) {
            e.printStackTrace();
            return "数据读取错误";
        } finally {
            transport.close();
        }
    }

    public static void main(String[] args) {
        TTransport transport = new TSocket("127.0.0.1", 8000);
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            Transmit.Client client = new Transmit.Client(protocol);

            //接口调用
            String rs = client.sayMsg("/home/diamond/桌面/staffGaugesurvey/match/IMG_1249.JPG");
            //打印调用结果
            System.out.println("java client:" + rs);
            transport.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }

    }


}
