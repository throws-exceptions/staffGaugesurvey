package com.staffGauge.survey.cargo.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Mr.F on 2020/4/20
 */
@Component
public class CargoServiceProvider {
    @Value("${thrift.cargo.ip}")
    private String serverIp;
    @Value("{thrift.cargo.port}")
    private int serverPort;
    public void getCargoService(String serverIp,int serverPort){
        TSocket socket=new TSocket(serverIp,serverPort,3000);
        TTransport transport=new TFramedTransport(socket);
        try{
            transport.open();
        }catch (TTransportException e){
            e.printStackTrace();
            //return null;
        }
        TProtocol protocol=new TBinaryProtocol(transport);
        //CargoService.Client client=new CargoService.Client(protocol);
        //return client;
    }
}
