package com.sinergiteknologiutama.snmp.execute;

import com.sinergiteknologiutama.snmp.dao.DeviceNodesDao;
import com.sinergiteknologiutama.snmp.device.*;
import com.sinergiteknologiutama.snmp.model.DeviceNodesModel;
import org.snmp4j.*;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

@Component
public class TrapReceiver implements CommandResponder {

    private MultiThreadedMessageDispatcher dispatcher;
    private Snmp snmp = null;
    private Address listenAddress;
    private ThreadPool threadPool;
    private int n = 0;
    private long start = -1;
    private VariableBinding trapOID;
    private VariableBinding trapOID2;
    private VariableBinding trapValue;


    final TrapHandler trapHandler;
    final DeviceNodesDao deviceNodesDao;
    final TSM100XS tsm100XS;
    final TSM100 tsm100;
    final TSM100P tsm100P;
    final ImPower imPower;
    final SmartPack smartPack;
    final SinelithLPF50A sinelithLPF50A;
    final SinelithLPF100A sinelithLPF100A;
    final SC200EATON sc200EATON;
    final EX4GKF ex4GKF;


    @Value("${trap.port}")
    public String portTrapSNMP;

    @Value("${trap.server}")
    public String severTrapSNMP;

    @Value("${trap.pool.core-size}")
    private int corePoolSize;

    //@Value("${email.notification}")
    //private String emailNotification;


    public TrapReceiver(TrapHandler trapHandler, DeviceNodesDao deviceNodesDao, TSM100XS tsm100XS, TSM100 tsm100, TSM100P tsm100P, ImPower imPower,
                        SmartPack smartPack, SinelithLPF50A sinelithLPF50A, SinelithLPF100A sinelithLPF100A, SC200EATON sc200EATON, EX4GKF ex4GKF) {

        this.trapHandler = trapHandler;
        this.deviceNodesDao = deviceNodesDao;
        this.tsm100XS = tsm100XS;
        this.tsm100 = tsm100;
        this.tsm100P = tsm100P;
        this.imPower = imPower;
        this.smartPack = smartPack;
        this.sinelithLPF50A = sinelithLPF50A;
        this.sinelithLPF100A = sinelithLPF100A;
        this.sc200EATON = sc200EATON;
        this.ex4GKF = ex4GKF;
    }


    public void run() {

        try {

            init();
            snmp.addCommandResponder(this);
            System.out.println("TRAP SNMP RUN ON SERVER:" + severTrapSNMP);
            System.out.println("START LISTENING ON PORT:" + portTrapSNMP);
            //System.out.println("EMAIL ROUTE :" + emailNotification);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void init() throws UnknownHostException, IOException {

        //core pool size
        //threadPool = ThreadPool.create("Trap", 10);
        threadPool = ThreadPool.create("Trap", corePoolSize);

        dispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());

        //TRANSPORT
        // TODO: 3/5/2020 : get from application.properties
        //String serverTrapIpAddress = "192.168.1.13";
        //String portTrapSNMP = "162";

        listenAddress = GenericAddress.parse(System.getProperty(
                "snmp4j.listenAddress", "udp:" + severTrapSNMP + "/" + portTrapSNMP));  //SET THIS
        TransportMapping<?> transport;
        if (listenAddress instanceof UdpAddress) {
            transport = new DefaultUdpTransportMapping(
                    (UdpAddress) listenAddress);
        } else {
            transport = new DefaultTcpTransportMapping(
                    (TcpAddress) listenAddress);
        }

        //V3 SECURITY
        USM usm = new USM(
                SecurityProtocols.getInstance().addDefaultProtocols(),
                new OctetString(MPv3.createLocalEngineID()), 0);

        SecurityProtocols.getInstance().addPrivacyProtocol(new PrivAES192());
        SecurityProtocols.getInstance().addPrivacyProtocol(new PrivAES256());
        SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());

        usm.setEngineDiscoveryEnabled(true);

        SecurityModels.getInstance().addSecurityModel(usm);

        snmp = new Snmp(dispatcher, transport);
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3(usm));


        String username = "username";         // SET THIS
        String authpassphrase = "authpassphrase";   // SET THIS
        String privacypassphrase = "privacypassphrase";   // SET THIS

        snmp.getUSM().addUser(    // SET THE SECURITY PROTOCOLS HERE
                new OctetString(username),
                new UsmUser(new OctetString(username), AuthMD5.ID, new OctetString(
                        authpassphrase), PrivAES128.ID, new OctetString(privacypassphrase)));

        snmp.listen();
    }


    @Override
    public void processPdu(CommandResponderEvent crEvent) {

        PDU pdu = crEvent.getPDU();
        Address address = crEvent.getPeerAddress();
        String agentIpaddress = address.toString().split("/")[0];

        Vector<? extends VariableBinding> varBinds = pdu.getVariableBindings();

        System.out.println("===== NEW TRAP SNMP RECEIVED ====");

        System.out.println("AGENT IPADDRESS : " + agentIpaddress);
        System.out.println("Varbinds:" + varBinds);

        //snmp V.1 Processing
        //trapProccessingV1(pdu, agentIpaddress, varBinds);

        //snmp V.2 Processing
        //trapProccessingV2(pdu, agentIpaddress, varBinds);

        //int device_id = deviceNodesDao.validAgentNode(agentIpaddress).getDevice_id();
        //String device_name = deviceNodesDao.validAgentNode(agentIpaddress).getName();

        //DeviceNodesModel device_node = deviceNodesDao.validAgentNode(agentIpaddress);

        //System.out.println("DEVICE NODE:"+ device_node);
        //System.out.println("DEVICE ID:"+ device_id);
        //System.out.println("DEVICE NAME:"+ device_name);

        //VALIDATION REGISTERED AGENT AS DEVICE_NODES

        //DeviceNodesModel device_node = deviceNodesDao.validAgentNode(agentIpaddress);
        //
        //String device_name = device_node.getName();
        //int device_id = device_node.getDevice_id();

        //System.out.println("DEVICE NODE:"+ device_node);
        //System.out.println("DEVICE ID:"+ device_id);
        //System.out.println("DEVICE NAME:"+ device_name);

        try {

            //System.out.println("DEVICE NODE:"+ node);
            int device_id = deviceNodesDao.validAgentNode(agentIpaddress).getDevice_id();
            System.out.println("DEVICE ID:"+ device_id);

            /*
            1,TSM100P
            2,EX4 GKF MOS
            3,RECTIFIER ImPow
            4,SINELITH LPF48-50A
            5,SC200
            6,SMART PACK
            7,TSM100
            8,SINELITH LPF48-100A
            9,TSM100-10S
            10,RECT DUMMY

            */

            //switch logic
            switch (device_id) {
                case 1:
                    //1,TSM100P
                    tsm100P.main(pdu, agentIpaddress, varBinds);

                    break;
                case 2:
                    //2,EX4 GKF MOS
                    ex4GKF.main(pdu, agentIpaddress, varBinds);

                    break;
                case 3:
                    //3,RECTIFIER ImPow
                    imPower.main(pdu, agentIpaddress, varBinds);

                    break;
                case 4:
                    //4,SINELITH LPF48-50A
                    sinelithLPF50A.main(pdu, agentIpaddress, varBinds);

                    break;
                case 5:
                    //5,SC200
                    sc200EATON.main(pdu, agentIpaddress, varBinds);

                    break;
                case 6:
                    //6,SMART PACK / ELTEK
                    smartPack.main(pdu, agentIpaddress, varBinds);

                    break;
                case 7:
                    //7,TSM100
                    tsm100.main(pdu, agentIpaddress, varBinds);

                    break;
                case 8:
                    //8,SINELITH LPF48-100A
                    sinelithLPF100A.main(pdu, agentIpaddress, varBinds);

                    break;
                case 9:
                    //9,TSM100-10S
                    tsm100XS.main(pdu, agentIpaddress, varBinds);

                    break;
                default:
                    System.out.println("NO MODULE TRAP HANDLER FOR THIS DEVICE!");

            }


        }catch (Exception e){
            System.out.println("NOT VALID AGENT");
        }




        System.out.println("==== TRAP END ===");
        System.out.println("");
    }

    //private void handlerTSM100S(PDU pdu, String agentIpaddress, Vector<? extends VariableBinding> varBinds) {
    //    if (pdu.getType() == PDU.V1TRAP) {
    //        //logic for v1
    //
    //        PDUv1 pduV1 = (PDUv1) pdu;
    //
    //        System.out.println("");
    //        System.out.println("===== NEW TRAP SNMP VERSION 1 RECEIVED ====");
    //        //System.out.println("ENTERPRISE: " + pduV1.getEnterprise().toString());
    //        System.out.println("AGENT IPADDRESS : " + agentIpaddress);
    //        System.out.println("Varbinds:" + varBinds);
    //
    //    }
    //
    //    if (pdu.getType() == PDU.TRAP) {
    //        //logic for v2
    //        System.out.println("===== NEW TRAP SNMP VERSION 2c/3 RECEIVED ====");
    //        //System.out.println("ORI VARBIND: " + pdu.getVariableBindings());
    //        System.out.println("AGENT IPADDRESS : " + agentIpaddress);
    //        System.out.println("Varbinds:" + varBinds);
    //
    //    }
    //}


    static String trimVarBindMessage(String value, String a) {
        // Returns a substring containing all characters after a string.
        int posA = value.lastIndexOf(a);
        if (posA == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= value.length()) {
            return "";
        }
        return value.substring(adjustedPosA);
    }


}

