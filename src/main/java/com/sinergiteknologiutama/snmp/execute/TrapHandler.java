package com.sinergiteknologiutama.snmp.execute;

import com.sinergiteknologiutama.snmp.dao.*;
import com.sinergiteknologiutama.snmp.model.DeviceNodesModel;
import com.sinergiteknologiutama.snmp.model.DevicesModel;
import com.sinergiteknologiutama.snmp.model.RegionsModel;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

@Component
public class TrapHandler {

    final DeviceNodesDao deviceNodesDao;
    final DevicesDao devicesDao;
    final RegionsDao regionsDao;
    final TemporaryAlarmDao temporaryAlarmDao;
    final TrapDataDevicesDao trapDataDevicesDao;
    final LogAlarmDao logAlarmDao;
    final TrapMessageReceivedDao trapMessageReceivedDao;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
    private DevicesModel validTrapOID;
    private DevicesModel validAgent;
    private DeviceNodesModel validAgentNode;
    private RegionsModel validAgentRegion;


    @Value("${email.notification}")
    private String urlEmailNotification;

    public TrapHandler(DeviceNodesDao deviceNodesDao, DevicesDao devicesDao, RegionsDao regionsDao, TemporaryAlarmDao temporaryAlarmDao, TrapDataDevicesDao trapDataDevicesDao, LogAlarmDao logAlarmDao, TrapMessageReceivedDao trapMessageReceivedDao) {
        this.deviceNodesDao = deviceNodesDao;
        this.devicesDao = devicesDao;
        this.regionsDao = regionsDao;
        this.temporaryAlarmDao = temporaryAlarmDao;
        this.trapDataDevicesDao = trapDataDevicesDao;
        this.logAlarmDao = logAlarmDao;
        this.trapMessageReceivedDao = trapMessageReceivedDao;
    }

    public void saveTrapRectifierImpow(String agentIpaddress, String oid, String value) {

        System.out.println("=== VALIDATION RECT IMPOW ===");

        try {
            validTrapOID = devicesDao.validTrapOID(oid.trim(), agentIpaddress);
            validAgentNode = deviceNodesDao.validAgentNode(agentIpaddress);
            validAgentRegion = regionsDao.validAgentRegion(agentIpaddress);


            if (validTrapOID.getDeviceSNMPTrapModel().getVar_oid().isEmpty() & validAgentNode.getIpaddress().isEmpty()) {
                System.out.println("Validation Has Failed");
            } else {
                System.out.println("Save To database");

                String region_oid = validAgentRegion.getOid();
                System.out.println("REGION OID:" + region_oid);

                String region_name = validAgentRegion.getName();
                System.out.println("REGION NAME:" + region_name);

                String city_oid = validAgentRegion.getSitesModel().getCity_oid();
                //System.out.println("CITY OID:" + city_oid);

                String city_name = validAgentRegion.getCitiesModel().getCity_name();
                System.out.println("CITY NAME:" + city_name);


                String site_oid = validTrapOID.getDeviceNodesModel().getSite_oid();
                //System.out.println("SITE OID:"+ validTrapOID.getDeviceNodesModel().getSite_oid());
                System.out.println("SITE OID:" + site_oid);

                String site_name = validAgentRegion.getSitesModel().getSite_name();
                System.out.println("SITE NAME:" + site_name);

                //String from_ipaddress = agentIpaddress;
                System.out.println("NODE IPADDRESS:" + agentIpaddress);

                String node_name = deviceNodesDao.validAgentNode(agentIpaddress).getName();
                //System.out.println("NODE NAME:"+deviceNodesDao.validAgentNode(agentIpaddress).getName());
                System.out.println("NODE NAME:" + node_name);

                int device_node_id = deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id();
                //System.out.println("NODE ID:"+deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id());
                System.out.println("NODE ID:" + device_node_id);

                //int node_oid = deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id();
                //System.out.println("NODE ID:"+deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id());
                //System.out.println("NODE ID:" + device_node_id);

                int snmp_trap_id = validTrapOID.getDeviceSNMPTrapModel().getSnmp_trap_id();
                //System.out.println("TRAP OID:"+validTrapOID.getDeviceSNMPTrapModel().getVar_oid());
                System.out.println("TRAP ID:" + snmp_trap_id);


                String var_oid = validTrapOID.getDeviceSNMPTrapModel().getVar_oid();
                //System.out.println("TRAP OID:"+validTrapOID.getDeviceSNMPTrapModel().getVar_oid());
                System.out.println("TRAP OID:" + var_oid);

                String var_name = validTrapOID.getDeviceSNMPTrapModel().getVar_name();
                //System.out.println("TRAP NAME:"+validTrapOID.getDeviceSNMPTrapModel().getVar_name());
                System.out.println("TRAP NAME:" + var_name);

                String type_trap = validTrapOID.getDeviceSNMPTrapModel().getType_trap();
                //System.out.println("TRAP OID:"+validTrapOID.getDeviceSNMPTrapModel().getVar_oid());
                System.out.println("TRAP TYPE:" + type_trap);


                int severity_id = validTrapOID.getDeviceSNMPTrapModel().getSeverity_id();
                //System.out.println("TRAP NAME:"+validTrapOID.getDeviceSNMPTrapModel().getVar_name());
                System.out.println("SEVERITY ID:" + severity_id);

                int var_value = Integer.parseInt(value.trim());
                //System.out.println("TRAP VALUE:"+value);
                System.out.println("TRAP VALUE:" + var_value);

                // TODO: 3/8/2020 : insert new trap data into database(trap_data_devices)
                //var_value = 1 (clear) , 2(active)
                if (var_value == 1) {
                    System.out.println("TRAP STATUS: INACTIVE");

                    //save incoming trap
                    trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "INACTIVE", var_name, var_value);
                    //trapDataDevicesDao.newTrapData(agentIpaddress, var_oid, "Active", var_name, var_value);

                    //update temporary alarm (only 2/active alarm in to be count)
                    countingAlarmInactive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);


                    //update trap_data_device
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "INACTIVE");


                    //save into log_alarm
                    logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, node_name, agentIpaddress, snmp_trap_id, var_oid, var_name, severity_id, var_value, "INACTIVE");


                } else {

                    System.out.println("TRAP STATUS: ACTIVE");

                    //save incoming trap
                    trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "ACTIVE", var_name, var_value);

                    //update temporary alarm (only 2/active alarm in to be count)
                    countingAlarmActive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                    //update trap_data_device
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "ACTIVE");

                    //save into log_alarm
                    logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, node_name, agentIpaddress, snmp_trap_id, var_oid, var_name, severity_id, var_value, "ACTIVE");

                    emailNotificationHandler(agentIpaddress, region_oid, site_oid, device_node_id, var_name, severity_id,snmp_trap_id);

                }


                // TODO: 3/8/2020 : get list of logic for all trap


            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No Agent or Trap OID found in Database");
        }


    }

    private void countingAlarmActive(String agentIpaddress, String region_oid, String site_oid, String node_name, String var_oid, int var_value, int severity_id, int snmp_trap_id) {

        /**
         * severity:
         * 1. Major
         * 2. Minor
         * 3. Critical
         * 4. Warning
         * 5. Commlost
         */

        String status_alarm = "ACTIVE";
        switch (severity_id) {
            case 1:
                System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY: MAJOR ");
                temporaryAlarmDao.updateSeverityMajor(agentIpaddress, snmp_trap_id, status_alarm,1);
                break;
            case 2:
                System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY MINOR");
                temporaryAlarmDao.updateSeverityMinor(agentIpaddress, snmp_trap_id, status_alarm,1);
                break;
            case 3:
                System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY CRITICAL");
                temporaryAlarmDao.updateSeverityCritical(agentIpaddress, snmp_trap_id, status_alarm,1);
                break;
            case 4:
                System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY WARNING");
                temporaryAlarmDao.updateSeverityWarning(agentIpaddress, snmp_trap_id, status_alarm,1);
                break;
            default:
                System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY COMMLOST");
                //temporaryAlarmDao.updateSeverityCommLost(agentIpaddress);//not use due to snmp-poller function

        }

    }

    private void countingAlarmInactive(String agentIpaddress, String region_oid, String site_oid, String node_name, String var_oid, int var_value, int severity_id, int snmp_trap_id) {

        /**
         * severity:
         * 1. Major
         * 2. Minor
         * 3. Critical
         * 4. Warning
         * 5. Commlost
         */
        String status_alarm = "INACTIVE";

        switch (severity_id) {
            case 1:
                System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY: MAJOR ");
                temporaryAlarmDao.updateSeverityMajor(agentIpaddress, snmp_trap_id, status_alarm,0);
                break;
            case 2:
                System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY MINOR");
                temporaryAlarmDao.updateSeverityMinor(agentIpaddress, snmp_trap_id, status_alarm,0);
                break;
            case 3:
                System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY CRITICAL");
                temporaryAlarmDao.updateSeverityCritical(agentIpaddress, snmp_trap_id, status_alarm,0);
                break;
            case 4:
                System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY WARNING");
                temporaryAlarmDao.updateSeverityWarning(agentIpaddress, snmp_trap_id, status_alarm,0);
                break;
            default:
                System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY COMMLOST");
                //temporaryAlarmDao.updateSeverityCommLost(agentIpaddress);//not use due to snmp-poller function

        }

    }

    public void saveTrapEA(String agentIpaddress, String oid, String value) {
        //System.out.println("save to database OID:" + oid);
        //System.out.println("save Value:" + value);
        //String valid_agent = deviceNodeDao.findByIpaddress(agentIpaddress).getIpaddress();

        System.out.println("=== VALIDATION STU EA DEVICES ===");

        // TODO: 3/5/2020 : get information site_oid,node_id from database (join devices+device_nodes) where ipaddress = agentIpaddress
        // TODO: 3/8/2020 : check agentIpaddress has registered
        // TODO: 3/5/2020 : get oid validation from database (join device_snmp_trap+severity) where var_oid = OID

        try {
            validTrapOID = devicesDao.validTrapOID(oid.trim(), agentIpaddress);
            validAgentNode = deviceNodesDao.validAgentNode(agentIpaddress);
            validAgentRegion = regionsDao.validAgentRegion(agentIpaddress);


            if (validTrapOID.getDeviceSNMPTrapModel().getVar_oid().isEmpty() & validAgentNode.getIpaddress().isEmpty()) {
                System.out.println("Validation Has Failed");
            } else {
                System.out.println("Save To database");

                String region_oid = validAgentRegion.getOid();
                System.out.println("REGION OID:" + region_oid);

                String region_name = validAgentRegion.getName();
                System.out.println("REGION NAME:" + region_name);

                String city_oid = validAgentRegion.getSitesModel().getCity_oid();
                //System.out.println("CITY OID:" + city_oid);

                String city_name = validAgentRegion.getCitiesModel().getCity_name();
                System.out.println("CITY NAME:" + city_name);


                String site_oid = validTrapOID.getDeviceNodesModel().getSite_oid();
                //System.out.println("SITE OID:"+ validTrapOID.getDeviceNodesModel().getSite_oid());
                System.out.println("SITE OID:" + site_oid);

                String site_name = validAgentRegion.getSitesModel().getSite_name();
                System.out.println("SITE NAME:" + site_name);

                int device_id = deviceNodesDao.validAgentNode(agentIpaddress).getDevice_id();
                System.out.println("DEVICE ID:" + device_id);
                //System.out.println("DEVICE NAME:"+devicesDao.validAgent(agentIpaddress).getName());

                //String from_ipaddress = agentIpaddress;
                System.out.println("NODE IPADDRESS:" + agentIpaddress);

                String node_name = deviceNodesDao.validAgentNode(agentIpaddress).getName();
                //System.out.println("NODE NAME:"+deviceNodesDao.validAgentNode(agentIpaddress).getName());
                System.out.println("NODE NAME:" + node_name);

                int device_node_id = deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id();
                //System.out.println("NODE ID:"+deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id());
                System.out.println("NODE ID:" + device_node_id);

                int snmp_trap_id = validTrapOID.getDeviceSNMPTrapModel().getSnmp_trap_id();
                //System.out.println("TRAP OID:"+validTrapOID.getDeviceSNMPTrapModel().getVar_oid());
                System.out.println("TRAP ID:" + snmp_trap_id);

                String var_oid = validTrapOID.getDeviceSNMPTrapModel().getVar_oid();
                //System.out.println("TRAP OID:"+validTrapOID.getDeviceSNMPTrapModel().getVar_oid());
                System.out.println("TRAP OID:" + var_oid);

                String var_name = validTrapOID.getDeviceSNMPTrapModel().getVar_name();
                //System.out.println("TRAP NAME:"+validTrapOID.getDeviceSNMPTrapModel().getVar_name());
                System.out.println("TRAP NAME:" + var_name);

                String type_trap = validTrapOID.getDeviceSNMPTrapModel().getType_trap();
                //System.out.println("TRAP OID:"+validTrapOID.getDeviceSNMPTrapModel().getVar_oid());
                System.out.println("TRAP TYPE:" + type_trap);

                int severity_id = validTrapOID.getDeviceSNMPTrapModel().getSeverity_id();
                //System.out.println("TRAP NAME:"+validTrapOID.getDeviceSNMPTrapModel().getVar_name());
                System.out.println("SEVERITY ID:" + severity_id);

                int var_value = Integer.parseInt(value.trim());
                //System.out.println("TRAP VALUE:"+value);
                System.out.println("TRAP VALUE:" + var_value);

                /**
                 * Device id :
                 * 1. EA/TSM
                 * 4. BMS
                 */

                //EA
                if (device_id == 1 & var_value == 0) {
                    System.out.println("creating logic for EA alarm");
                    /**
                     * Alarm list of EA/TSM
                     * 1. DOOR SENSE (1.3.6.1.4.1.47865.3.20.20.1000) = 0 (clear),1 (active)
                     * 2. MAIN FAIL (1.3.6.1.4.1.47865.3.20.20.2000) = 0 (clear), 1 (active)
                     * 3. HIGH TEMPERATURE  AND OTHER ALARM HAVE OWN OID TO DEFINE ACTIVE/INACTIVE
                     */
                    // TODO: 3/13/2020 : create logic for initiate active/clear base on oid with value ??

                    System.out.println("TRAP STATUS: INACTIVE");
                    System.out.println("CREATE LOGIC FOR OID :"+var_oid);

                    //save incoming trap
                    trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "INACTIVE", var_name, var_value);
                    //trapDataDevicesDao.newTrapData(agentIpaddress, var_oid, "Active", var_name, var_value);

                    //update temporary alarm (only 2/active alarm in to be count)
                    countingAlarmInactive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                    //update trap_data_device
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "INACTIVE");


                    //save into log_alarm
                    logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, node_name, agentIpaddress, snmp_trap_id, var_oid, var_name, severity_id, var_value, "INACTIVE");

                    emailNotificationHandler(agentIpaddress, region_oid, site_oid, device_node_id, var_name, severity_id,snmp_trap_id);


                    //EA
                } else if (device_id == 1 & var_value == 1) {

                    System.out.println("TRAP STATUS: ACTIVE");
                    System.out.println("CREATE LOGIC FOR OID :"+var_oid);

                    //save incoming trap
                    trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "ACTIVE", var_name, var_value);

                    //update temporary alarm (only 2/active alarm in to be count)
                    countingAlarmActive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                    //update trap_data_device
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "ACTIVE");

                    //save into log_alarm
                    logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, node_name, agentIpaddress, snmp_trap_id, var_oid, var_name, severity_id, var_value, "ACTIVE");

                    //System.out.println(" SENDING POST DATA TO URL NMS !!!!");

                    emailNotificationHandler(agentIpaddress, region_oid, site_oid, device_node_id, var_name, severity_id,snmp_trap_id);


                    //BMS
                } else if (device_id == 4 & type_trap.equals("INACTIVE")) {

                    System.out.println("TRAP STATUS: INACTIVE");


                    //save incoming trap
                    trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "INACTIVE", var_name, var_value);
                    //trapDataDevicesDao.newTrapData(agentIpaddress, var_oid, "Active", var_name, var_value);

                    countingAlarmInactive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                    //update trap_data_device
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "INACTIVE");


                    //save into log_alarm
                    logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, node_name, agentIpaddress, snmp_trap_id, var_oid, var_name, severity_id, var_value, "INACTIVE");

                    emailNotificationHandler(agentIpaddress, region_oid, site_oid, device_node_id, var_name, severity_id,snmp_trap_id);


                    //BMS
                } else if (type_trap.equals("ACTIVE") & device_id == 4) {

                    System.out.println("TRAP STATUS: ACTIVE");


                    //save incoming trap
                    trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "ACTIVE", var_name, var_value);

                    //update temporary alarm (only 2/active alarm in to be count)
                    countingAlarmActive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                    //update trap_data_device
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "ACTIVE");

                    //save into log_alarm
                    logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, node_name, agentIpaddress, snmp_trap_id, var_oid, var_name, severity_id, var_value, "ACTIVE");

                    //System.out.println(" SENDING POST DATA TO URL NMS !!!!");
                    emailNotificationHandler(agentIpaddress, region_oid, site_oid, device_node_id, var_name, severity_id,snmp_trap_id);


                }


                // TODO: 3/8/2020 : get list of logic for all trap

            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No Agent or Trap OID found in Database");
        }


    }



    public void saveTrapBMS(String agentIpaddress, String oid, String oid2, String value) {

        System.out.println("=== VALIDATION STU  BMS / IMPOW_V2 DEVICES ===");

        //System.out.println("agent ipaddress:"+ agentIpaddress);
        //System.out.println("OID 1 :"+oid);
        //System.out.println("OID 2 :"+oid2);
        //System.out.println("VALUE :"+ value);

        try {
            System.out.println("TRY WITH STU DEVICE ");
            //oid = bms
            firstValidation(agentIpaddress, oid, value);

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No Agent or Trap OID found in Database, NEXT validation");
        }

        try {
            System.out.println("TRY WITH IMPOW DEVICE ");
            //oid2+value = impow
            firstValidation(agentIpaddress, oid2, value);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No Agent or Trap OID found in Database, from BMS or IMPOW");
        }


    }


    private void firstValidation(String agentIpaddress, String oid, String value) throws IOException, JSONException {
        validTrapOID = devicesDao.validTrapOID(oid.trim(), agentIpaddress);
        validAgentNode = deviceNodesDao.validAgentNode(agentIpaddress);
        validAgentRegion = regionsDao.validAgentRegion(agentIpaddress);


        if (validTrapOID.getDeviceSNMPTrapModel().getVar_oid().isEmpty() & validAgentNode.getIpaddress().isEmpty()) {
            System.out.println("Validation Has Failed");
        } else {
            //System.out.println("Save To database");

            String region_oid = validAgentRegion.getOid();
            System.out.println("REGION OID:" + region_oid);

            String region_name = validAgentRegion.getName();
            System.out.println("REGION NAME:" + region_name);

            String city_oid = validAgentRegion.getSitesModel().getCity_oid();
            //System.out.println("CITY OID:" + city_oid);

            String city_name = validAgentRegion.getCitiesModel().getCity_name();
            System.out.println("CITY NAME:" + city_name);


            String site_oid = validTrapOID.getDeviceNodesModel().getSite_oid();
            //System.out.println("SITE OID:"+ validTrapOID.getDeviceNodesModel().getSite_oid());
            System.out.println("SITE OID:" + site_oid);

            String site_name = validAgentRegion.getSitesModel().getSite_name();
            System.out.println("SITE NAME:" + site_name);

            int device_id = deviceNodesDao.validAgentNode(agentIpaddress).getDevice_id();
            System.out.println("DEVICE ID:" + device_id);
            //System.out.println("DEVICE NAME:"+devicesDao.validAgent(agentIpaddress).getName());

            //String from_ipaddress = agentIpaddress;
            System.out.println("NODE IPADDRESS:" + agentIpaddress);

            String node_name = deviceNodesDao.validAgentNode(agentIpaddress).getName();
            //System.out.println("NODE NAME:"+deviceNodesDao.validAgentNode(agentIpaddress).getName());
            System.out.println("NODE NAME:" + node_name);

            int device_node_id = deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id();
            //System.out.println("NODE ID:"+deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id());
            System.out.println("NODE ID:" + device_node_id);

            //int node_oid = deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id();
            //System.out.println("NODE ID:"+deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id());
            //System.out.println("NODE ID:" + device_node_id);

            int snmp_trap_id = validTrapOID.getDeviceSNMPTrapModel().getSnmp_trap_id();
            //System.out.println("TRAP OID:"+validTrapOID.getDeviceSNMPTrapModel().getVar_oid());
            System.out.println("TRAP ID:" + snmp_trap_id);


            String var_oid = validTrapOID.getDeviceSNMPTrapModel().getVar_oid();
            //System.out.println("TRAP OID:"+validTrapOID.getDeviceSNMPTrapModel().getVar_oid());
            System.out.println("TRAP OID:" + var_oid);

            String var_name = validTrapOID.getDeviceSNMPTrapModel().getVar_name();
            //System.out.println("TRAP NAME:"+validTrapOID.getDeviceSNMPTrapModel().getVar_name());
            System.out.println("TRAP NAME:" + var_name);

            String type_trap = validTrapOID.getDeviceSNMPTrapModel().getType_trap();
            //System.out.println("TRAP OID:"+validTrapOID.getDeviceSNMPTrapModel().getVar_oid());
            System.out.println("TRAP TYPE:" + type_trap);


            int severity_id = validTrapOID.getDeviceSNMPTrapModel().getSeverity_id();
            //System.out.println("TRAP NAME:"+validTrapOID.getDeviceSNMPTrapModel().getVar_name());
            System.out.println("SEVERITY ID:" + severity_id);

            int var_value = Integer.parseInt(value.trim());
            //System.out.println("TRAP VALUE:"+value);
            System.out.println("TRAP VALUE:" + var_value);

            // TODO: 3/8/2020 : insert new trap data into database(trap_data_devices)
            //var_value = 1 (clear) , 2(active)



            /**
             * Device id :
             * 1. EA/TSM
             * 4. BMS
             * 3. other
             */
            if (type_trap.equals("ACTIVE") & device_id == 1 | device_id == 4) {
                //System.out.println("save active");
                System.out.println("TRAP STATUS: ACTIVE");

                //save incoming trap
                trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "ACTIVE", var_name, var_value);

                //update temporary alarm (only 2/active alarm in to be count)
                countingAlarmActive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                //update trap_data_device
                if (var_oid.equals("1.3.6.1.4.1.47865.3.20.20.3000")){
                    //System.out.println("RUN LOGIC TEMPERATURE ACTIVE");
                    //update oid 3000 with status active ,and 4000 status in inactive
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, 4, "1.3.6.1.4.1.47865.3.20.20.4000", severity_id, 0, "INACTIVE");
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, 3, "1.3.6.1.4.1.47865.3.20.20.3000", severity_id, 1, "ACTIVE");

                }else{
                    //System.out.println("RUN OTHER OID ACTIVE");
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "ACTIVE");
                }

                //save incoming trap
                //trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "ACTIVE", var_name, var_value);

                //update temporary alarm (only 2/active alarm in to be count)
                //countingAlarmActive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                //update trap_data_device
                //trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "ACTIVE");

                //save into log_alarm
                logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, node_name, agentIpaddress, snmp_trap_id, var_oid, var_name, severity_id, var_value, "ACTIVE");

                //submit email notification
                emailNotificationHandler(agentIpaddress, region_oid, site_oid, device_node_id, var_name, severity_id,snmp_trap_id);


            }

            else if (type_trap.equals("INACTIVE") & device_id == 1 | device_id == 4) {
                //System.out.println("save inactive");
                System.out.println("TRAP STATUS: INACTIVE");



                //save incoming trap
                trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "INACTIVE", var_name, var_value);
                //trapDataDevicesDao.newTrapData(agentIpaddress, var_oid, "Active", var_name, var_value);

                //countingAlarmInactive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                //update trap_data_device
                //trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "INACTIVE");
                //ONLY FOR TEMPERATURE
                if (var_oid.equals("1.3.6.1.4.1.47865.3.20.20.4000")){
                    //System.out.println("RUN LOGIC TEMPERATURE INACTIVE");
                    //update oid 4000 inactive 3000 inactive
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, 3, "1.3.6.1.4.1.47865.3.20.20.3000", severity_id, var_value, "INACTIVE");
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, 4, "1.3.6.1.4.1.47865.3.20.20.4000", severity_id, var_value, "INACTIVE");

                    countingAlarmInactive(agentIpaddress, region_oid, site_oid, node_name, "1.3.6.1.4.1.47865.3.20.20.3000", 0, severity_id, 3);
                    //countingAlarmInactive(agentIpaddress, region_oid, site_oid, node_name, "1.3.6.1.4.1.47865.3.20.20.4000", 0, severity_id, 4);


                }else{
                    //System.out.println("RUN OTHER OID INACTIVE");
                    trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "INACTIVE");
                    countingAlarmInactive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                }

                //save into log_alarm
                logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, node_name, agentIpaddress, snmp_trap_id, var_oid, var_name, severity_id, var_value, "INACTIVE");

                emailNotificationHandler(agentIpaddress, region_oid, site_oid, device_node_id, var_name, severity_id,snmp_trap_id);

            }

            //for impow
            else if (type_trap.equals("DUAL") & device_id == 3 & var_value == 1) {
                //System.out.println("save inactive");
                System.out.println("TRAP STATUS: INACTIVE");

                //save incoming trap
                trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "INACTIVE", var_name, var_value);
                //trapDataDevicesDao.newTrapData(agentIpaddress, var_oid, "Active", var_name, var_value);

                countingAlarmInactive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                //update trap_data_device
                trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "INACTIVE");


                //save into log_alarm
                logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, node_name, agentIpaddress, snmp_trap_id, var_oid, var_name, severity_id, var_value, "INACTIVE");

                emailNotificationHandler(agentIpaddress, region_oid, site_oid, device_node_id, var_name, severity_id,snmp_trap_id);

            }

            else if (type_trap.equals("DUAL") & device_id == 3 & var_value == 2) {
                //System.out.println("save active");
                System.out.println("TRAP STATUS: ACTIVE");

                //save incoming trap
                trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, var_oid, "ACTIVE", var_name, var_value);

                //update temporary alarm (only 2/active alarm in to be count)
                countingAlarmActive(agentIpaddress, region_oid, site_oid, node_name, var_oid, var_value, severity_id, snmp_trap_id);

                //update trap_data_device
                trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, var_oid, severity_id, var_value, "ACTIVE");

                //save into log_alarm
                logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, node_name, agentIpaddress, snmp_trap_id, var_oid, var_name, severity_id, var_value, "ACTIVE");

                emailNotificationHandler(agentIpaddress, region_oid, site_oid, device_node_id, var_name, severity_id,snmp_trap_id);

            }


        }
    }


    private void emailNotificationHandler(String agentIpaddress, String region_oid, String site_oid, int device_node_id, String var_name, int severity_id,int snmp_trap_id) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("region_oid",region_oid);
        jsonObject.put("site_oid",site_oid);
        jsonObject.put("device_node_id",device_node_id);
        jsonObject.put("ipaddress",agentIpaddress);
        jsonObject.put("var_name",var_name);
        jsonObject.put("severity",severity_id);
        jsonObject.put("trap_id",snmp_trap_id);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response;

        try {
            //todo:get from setting
            //System.out.println("sending email to :"+urlEmailNotification);
            HttpPost request = new HttpPost(urlEmailNotification);
            StringEntity params = new StringEntity(jsonObject.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            //httpClient.execute(request);
            HttpResponse responsePOST = httpClient.execute(request);

            // handle response here...
            /*Checking response */
            //if (responsePOST != null) {
            //    InputStream in = responsePOST.getEntity().getContent(); //Get the data in the entity
            //    System.out.println(in);
            //}

            BufferedReader reader = new BufferedReader(new InputStreamReader(responsePOST.getEntity().getContent()), 2048);

            if (responsePOST != null) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    //System.out.println(" line : " + line);
                    System.out.println("Notification :"+line);

                    sb.append(line);
                }
                String getResponseString = "";
                getResponseString = sb.toString();
                //use server output getResponseString as string value.

                //System.out.println("Response :"+getResponseString);
            }

        } catch (Exception ex) {
            // handle exception here
            ex.printStackTrace();
            System.out.println("Error Cannot Estabilish Connection");
        } finally {
            httpClient.close();
        }
    }

    public void saveToDatabase(String agentIpaddress, String varOID, int var_value, int snmp_trap_id, String var_name, int severity_id, String region_oid, String city_oid, String device_node_name, String site_oid, String site_name, Integer device_node_id, String status_alarm) {
        //System.out.println("SAVE TO DATABASE");

        //save incoming trap
        trapMessageReceivedDao.insertNewTrapMassage(agentIpaddress, varOID, status_alarm, var_name, var_value);

        //update temporary alarm (only 2/active alarm in to be count)
        countingAlarm(agentIpaddress, device_node_id, severity_id, snmp_trap_id, status_alarm);

        //update trap_data_device
        trapDataDevicesDao.updateTrapData(site_oid, device_node_id, snmp_trap_id, varOID, severity_id, var_value, status_alarm);

        //save into log_alarm
        logAlarmDao.insertNewLog(region_oid, city_oid, site_oid, site_name, device_node_id, device_node_name, agentIpaddress, snmp_trap_id, varOID, var_name, severity_id, var_value, status_alarm);
    }

    private void countingAlarm(String agentIpaddress, int device_node_id, int severity_id, int snmp_trap_id, String status_alarm) {

        /**
         * severity:
         * 1. Major
         * 2. Minor
         * 3. Critical
         * 4. Warning
         * 5. Commlost
         */

        if (status_alarm.equals("ACTIVE")) {
            //String status_alarm = "ACTIVE";
            //System.out.println("counting alarm active");
            switch (severity_id) {
                case 1:
                    //System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY: MAJOR ");
                    temporaryAlarmDao.countSeverityMajor(device_node_id, snmp_trap_id, status_alarm, 1);
                    break;
                case 2:
                    //System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY MINOR");
                    temporaryAlarmDao.countSeverityMinor(device_node_id, snmp_trap_id, status_alarm, 1);
                    break;
                case 3:
                    //System.out.println("UPDATE TEMPORARY countCOUNTING+1 SEVERITY CRITICAL");
                    temporaryAlarmDao.countSeverityCritical(device_node_id, snmp_trap_id, status_alarm, 1);
                    break;
                case 4:
                    //System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY WARNING");
                    temporaryAlarmDao.countSeverityWarning(device_node_id, snmp_trap_id, status_alarm, 1);
                    break;
                default:
                    //System.out.println("UPDATE TEMPORARY ALARM COUNTING+1 SEVERITY COMMLOST");
                    //temporaryAlarmDao.updateSeverityCommLost(agentIpaddress);//not use due to snmp-poller function

            }

        } else {

            //System.out.println("counting alarm in active");
            switch (severity_id) {
                case 1:
                    //System.out.println("UPDATE TEMPORARY ALARM COUNTING+0 SEVERITY: MAJOR ");
                    temporaryAlarmDao.countSeverityMajor(device_node_id, snmp_trap_id, status_alarm, 0);
                    break;
                case 2:
                    //System.out.println("UPDATE TEMPORARY ALARM COUNTING+0 SEVERITY MINOR");
                    temporaryAlarmDao.countSeverityMinor(device_node_id, snmp_trap_id, status_alarm, 0);
                    break;
                case 3:
                    //System.out.println("UPDATE TEMPORARY ALARM COUNTING+0 SEVERITY CRITICAL");
                    temporaryAlarmDao.countSeverityCritical(device_node_id, snmp_trap_id, status_alarm, 0);
                    break;
                case 4:
                    //System.out.println("UPDATE TEMPORARY ALARM COUNTING+0 SEVERITY WARNING");
                    temporaryAlarmDao.countSeverityWarning(device_node_id, snmp_trap_id, status_alarm, 0);
                    break;
                default:
                    System.out.println("UPDATE TEMPORARY ALARM COUNTING+0 SEVERITY COMMLOST");
                    //temporaryAlarmDao.updateSeverityCommLost(agentIpaddress);//not use due to snmp-poller function

            }

        }


    }



}

