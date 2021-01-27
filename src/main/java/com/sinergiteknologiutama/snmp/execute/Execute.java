//package com.sinergiteknologiutama.snmp.execute;
//
//import com.adventnet.snmp.snmp2.*;
//import com.sinergiteknologiutama.snmp.dao.*;
//import com.sinergiteknologiutama.snmp.model.DevicesModel;
//import com.sinergiteknologiutama.snmp.model.SitesModel;
//import lombok.extern.slf4j.Slf4j;
//import org.joda.time.DateTime;
//import org.joda.time.DateTimeZone;
//import org.joda.time.Instant;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.InetAddress;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Slf4j
//@Component
//public class Execute {
//
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
//
//    final SitesDao sitesDao;
//    final DevicesDao devicesDao;
//    final PollingDataDevicesDao pollingDataDevicesDao;
//    final LogPollingDataDeviceDao logPollingDataDeviceDao;
//    final CommnunicationsDataDao commnunicationsDataDao;
//    final TemporaryAlarmDao temporaryAlarmDao;
//    final String updated_at = dateFormat.format(new Date());
//
//    //private Timestamp updated_at = Timestamp.valueOf(dateFormat.format(new Date()));
//
//
//    public Execute(SitesDao sitesDao, DevicesDao devicesDao, PollingDataDevicesDao pollingDataDevicesDao, LogPollingDataDeviceDao logPollingDataDeviceDao, CommnunicationsDataDao commnunicationsDataDao, TemporaryAlarmDao temporaryAlarmDao) {
//        this.sitesDao = sitesDao;
//        this.devicesDao = devicesDao;
//        this.pollingDataDevicesDao = pollingDataDevicesDao;
//        this.logPollingDataDeviceDao = logPollingDataDeviceDao;
//        this.commnunicationsDataDao = commnunicationsDataDao;
//        this.temporaryAlarmDao = temporaryAlarmDao;
//    }
//
//    @Value("${server.poller}")
//    private String serverPoller;
//
//
//    public void initPolling() {
//
//        //String updated_at = dateFormat.format(new Date());
//
//        System.out.println("START AT:" + updated_at);
//
//        pingAllNodeOnSite();
//
//        // GET SNMP OBJECT
//        //pollingDeviceNode();
//
//    }
//
//    @Async
//    void pollingDeviceNode() {
//        List<DevicesModel> devicesModels = devicesDao.deviceList(serverPoller);
//        for (DevicesModel node : devicesModels) {
//            String site_oid = node.getDeviceNodesModel().getSite_oid();
//            Integer node_id = node.getDeviceNodesModel().getDevice_node_id();
//            String node_ipaddress = node.getDeviceNodesModel().getIpaddress();
//            Integer object_id = node.getDeviceSNMPObjectModel().getSnmp_object_id();
//            String object_oid = node.getDeviceSNMPObjectModel().getVar_oid();
//            Double object_devider = (double) node.getDeviceSNMPObjectModel().getVar_devider();
//            Integer snmp_version = Integer.parseInt(node.getSnmp_version());
//            Integer snmp_timeout = node.getSnmp_timeout();
//            Integer snmp_retries = node.getSnmp_retries();
//
//
//            //log.info("Polling IP :{}",node_ipaddress);
//            System.out.println("SITE OID : " + site_oid);
//            System.out.println("NODE OID : " + node_id);
//            System.out.println("NODE IPADDRESS : " + node_ipaddress);
//            //System.out.println("OBJECT ID : " + object_id);
//            System.out.println("OBJECT OID : " + object_oid);
//            //System.out.println("OBJECT DEVIDER : " + object_devider);
//            System.out.println("SNMP VERSION:"+ snmp_version);
//            System.out.println("RETRIES GET :"+ snmp_retries);
//
//            System.out.println("TIMOUT GET:"+ snmp_timeout);
//
//            System.out.println("------------------------------------------");
//            System.out.println("GET SNMP RESULT >>");
//
//            getObjectSNMP(site_oid, node_id, node_ipaddress, object_id, object_oid, object_devider,snmp_version,snmp_timeout,snmp_retries);
//
//            System.out.println("------------------------------------------------------------------------------------");
//        }
//    }
//
//    @Async
//    void getObjectSNMP(String site_oid, Integer node_id, String node_ipaddress, Integer object_id, String object_oid, Double object_devider, Integer snmp_version, Integer snmp_timeout, Integer snmp_retries) {
//        SnmpAPI api = new SnmpAPI();
//
//        // Open session
//        SnmpSession session = new SnmpSession(api);
//        try {
//            session.open();
//        } catch (SnmpException e) {
//            System.err.println("Error opening socket: " + e);
//        }
//
//        // Build Get request PDU
//        SnmpPDU pdu = new SnmpPDU();
//
//        if(snmp_version == 1)pdu.setVersion(SnmpAPI.SNMP_VERSION_1);
//        else if(snmp_version == 2)pdu.setVersion(SnmpAPI.SNMP_VERSION_2C);
//        else if(snmp_version == 3)pdu.setVersion(SnmpAPI.SNMP_VERSION_3);
//
//        UDPProtocolOptions option = new UDPProtocolOptions(node_ipaddress);
//        pdu.setProtocolOptions(option);
//        pdu.setCommand(SnmpAPI.GET_REQ_MSG);
//
//        pdu.setCommunity("public");
//        pdu.setRetries(snmp_retries);
//        pdu.setTimeout(snmp_timeout);
//
//        // Provide the OID .1.3.6.1.2.1.1.0
//        SnmpOID oid = new SnmpOID(object_oid);
//        pdu.addNull(oid);
//
//        // Send PDU and receive response PDU
//        SnmpPDU result = null;
//        try {
//            result = session.syncSend(pdu);
//        } catch (SnmpException e) {
//            e.printStackTrace();
//        }
//        if (result == null) {
//            //this message show due to  comm. lost  to devices_node
//            System.out.println("Request timed out!");
//            //logPollingDataDeviceDao.insertNewLog(site_oid,node_id,object_id,255.255,object_devider, Timestamp.valueOf(dateFormat.format(new Date())));
//
//        } else {
//            //System.out.println("result = "+result.getVariableBindings().toString());
//            if (result.getErrstat() == 0) {
//
//                // print the response pdu varbinds
//                System.out.println(result.printVarBinds());
//                //System.out.println("OID : " + result.getObjectID(0));
//
//                //int value = Integer.parseInt(String.valueOf(result.getVariable(0))) ;
//                Double value = Double.parseDouble(String.valueOf(result.getVariable(0)));
//                //System.out.println("VALUE : " + value);
//
//                //log.info("Update Database");
//
//                // TODO: 2/19/2020 : update polling_data_device(DONE)
//                //System.out.println("UDPATE DATASE AFTER GET SNMP SUCCESS");
//
//
//                //pollingDataDevicesDao.updateTable(value, object_devider, site_oid, node_id, object_id,updated_at);
//                pollingDataDevicesDao.updateTable(value, object_devider, site_oid, node_id, object_id);
//
//                // TODO: 2/19/2020 :insert into log_polling_data_devices(DONE)
//                //logPollingDataDeviceDao.insertNewLog(site_oid,node_id,object_id,value,object_devider, Timestamp.valueOf(dateFormat.format(new Date())));
//
//
//
//            } else {
//                //System.out.print("ERROR:");
//                System.out.println(result.getError());
//            }
//        }
//
//        // close session
//        session.close();
//
//        // close api thread
//        api.close();
//    }
//
//    @Async
//    void pingAllNodeOnSite() {
//        ArrayList<String> alive_node_ipaddress = new ArrayList<>();
//
//        ArrayList<String> commlost_node_ipaddress = new ArrayList<>();
//
//        List<SitesModel> siteNode = sitesDao.siteNodeList(serverPoller);
//
//        //PollingDataDevicesDao polling = new PollingDataDevicesDao();
//
//        //PING ALL NODE IN SITE
//        for (SitesModel record : siteNode) {
//            try {
//                //InetAddress inet = InetAddress.getByName(record.getDeviceNode().getIpaddress());
//                //System.out.println("=========================================================");
//                String ip = record.getDeviceNode().getIpaddress();
//                Integer node_id = record.getDeviceNode().getDevice_node_id();
//                String region_oid = record.getRegion_oid();
//                String site_oid = record.getDeviceNode().getSite_oid();
//                String device = record.getName();
//                System.out.println("IPaddress: "+ip);
//
//
//                //System.out.println("Sending Ping Request to " + ip);
//                //log.info("Sending ping Request {}:",ip);
//
//                //if (inet.isReachable(5000)) {
//                //    //System.out.println(ip + " is reachable.");
//                //    alive_node_ipaddress.add(ip);
//                //
//                //    //UPDATE `communications_data` with 'alive' status
//                //    commnunicationsDataDao.updateCommunications(1,1,node_id);
//                //
//                //
//                //} else {
//                //    //System.out.println(ip + " NOT reachable.");
//                //    commlost_node_ipaddress.add(ip);
//                //
//                //    // TODO: 2/19/2020 : update communication_data (DONE)
//                //    //UPDATE `communications_data` with 'commlost' status
//                //    commnunicationsDataDao.updateCommunications(0,0,node_id);
//                //
//                //    // TODO: 2/19/2020 : update polling_data_device value 255.255 (DONE)
//                //    //UPDATE polling_data_device
//                //    pollingDataDevicesDao.updateCommLostSite(site_oid,node_id,Timestamp.valueOf(dateFormat.format(new Date())));
//                //
//                //    // TODO: 2/19/2020 : update alarm_temp(DONE)
//                //    temporaryAlarmDao.updateSeverityCommLost(ip);
//
//
//                //}
//            } catch (Exception e) {
//                System.out.println("Exception:" + e.getMessage());
//            }
//
//
//            //All devices+devices_node+object
//
//
//            //Enumeration<String> e = Collections.enumeration(alive_node_ipaddress);
//            //while (e.hasMoreElements());
//            //System.out.println(e.nextElement());
//
//            //Execute.pingIpAddress(record.getDeviceNode().getIpaddress());
//            //System.out.println("CALL FUNCTION GET SNMP OID WITH SNMP_OBJECT LIST");
//        }
//
//        //All alive devices_node
//        System.out.println("Alive IP :"+ alive_node_ipaddress);
//        //for (String n : alive_node_ipaddress) {
//        for (String ignore : alive_node_ipaddress) {
//            //System.out.println("UPDATE COMMUNICATION STATUS PING OK :" + n);
//            // TODO: 2/19/2020 : update communication_data (DONE)
//
//            System.out.print("");
//        }
//
//        //All commlost devices_node
//        System.out.println("Comm Lost IP:"+commlost_node_ipaddress);
//        //for (String n : commlost_node_ipaddress) {
//        for (String ignore : commlost_node_ipaddress) {
//            //System.out.println("UPDATE COMMUNICATION STATUS COMMLOST :" + n);
//            // TODO: 2/19/2020 : insert into log_polling_data_devices with value 255.255 (*comm.lost code:DONE)
//
//            System.out.print("");
//
//        }
//    }
//
//    @Value("${python.execute}")
//    private String executePython;
//
//    public void customProgram(){
//
//        try {
//            String s;
//            //Process p = Runtime.getRuntime().exec("python C:\\Users\\UNIX3\\PycharmProjects\\nms2020\\nms.py");
//            Process p = Runtime.getRuntime().exec(executePython);
//            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            while ((s = in.readLine()) != null){
//                System.out.println(s);
//            }
//        }catch (IOException ie){
//            ie.printStackTrace();
//            System.out.println("Error occur try to run exclude program");
//        }
//    }
//
//}
