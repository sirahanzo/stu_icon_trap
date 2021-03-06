package com.sinergiteknologiutama.snmp.device;

import com.sinergiteknologiutama.snmp.dao.*;
import com.sinergiteknologiutama.snmp.execute.TrapHandler;
import com.sinergiteknologiutama.snmp.helper.TrimVarBindMessage;
import com.sinergiteknologiutama.snmp.model.DeviceSNMPTrapModel;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.smi.VariableBinding;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
public class TSM100XS {

    final RegionsDao regionsDao;
    final DevicesDao devicesDao;
    final DeviceNodesDao deviceNodesDao;
    final TrimVarBindMessage trimVarBindMessage;
    final TrapMessageReceivedDao trapMessageReceivedDao;
    final TemporaryAlarmDao temporaryAlarmDao;
    final TrapDataDevicesDao trapDataDevicesDao;
    final LogAlarmDao logAlarmDao;
    final TrapHandler trapHandler;

    public TSM100XS(RegionsDao regionsDao, DevicesDao devicesDao, DeviceNodesDao deviceNodesDao, TrimVarBindMessage trimVarBindMessage,
                    TrapMessageReceivedDao trapMessageReceivedDao, TemporaryAlarmDao temporaryAlarmDao, TrapDataDevicesDao trapDataDevicesDao,
                    LogAlarmDao logAlarmDao, TrapHandler trapHandler) {

        this.regionsDao = regionsDao;
        this.devicesDao = devicesDao;
        this.deviceNodesDao = deviceNodesDao;
        this.trimVarBindMessage = trimVarBindMessage;
        this.trapMessageReceivedDao = trapMessageReceivedDao;
        this.temporaryAlarmDao = temporaryAlarmDao;
        this.trapDataDevicesDao = trapDataDevicesDao;
        this.logAlarmDao = logAlarmDao;
        this.trapHandler = trapHandler;
    }

    public void main(PDU pdu, String agentIpaddress, Vector<? extends VariableBinding> varBinds) {

        trapV1(pdu, agentIpaddress, varBinds);
        trapV2(pdu, agentIpaddress, varBinds);

    }

    private void trapV1(PDU pdu, String agentIpaddress, Vector<? extends VariableBinding> varBinds) {
        if (pdu.getType() == PDU.V1TRAP) {
            //logic for v1

            PDUv1 pduV1 = (PDUv1) pdu;

            System.out.println("");
            System.out.println("===== NEW TRAP SNMP VERSION 1 RECEIVED ====");
            //System.out.println("ENTERPRISE: " + pduV1.getEnterprise().toString());
            System.out.println("AGENT IPADDRESS : " + agentIpaddress);
            System.out.println("Varbinds:" + varBinds);

        }
    }

    private void trapV2(PDU pdu, String agentIpaddress, Vector<? extends VariableBinding> varBinds) {
        if (pdu.getType() == PDU.TRAP) {
            //logic for v2
            System.out.println("===== NEW TRAP SNMP VERSION 2c/3 RECEIVED ====");
            //System.out.println("ORI VARBIND: " + pdu.getVariableBindings());
            System.out.println("AGENT IPADDRESS : " + agentIpaddress);
            System.out.println("Varbinds:" + varBinds);

            //count varbind here
            int varbind_size = varBinds.size();

            VariableBinding varbind1;
            VariableBinding varbind2;
            VariableBinding varbind3;
            VariableBinding varbind4;

            String varOID;
            String varValue;
            int var_monitoring;
            int var_treshold;
            int var_value;

            System.out.println("VARBIND SIZE :" + varbind_size);

            switch (varbind_size) {
                case 3:
                    //varbind 3

                    parsingVarbind3(varBinds,agentIpaddress);

                    break;
                case 4:
                    //varbind 4
                    System.out.println("NO METHOD TO HANDLE THIS VARBIND TRAP OBJECT");

                    break;
                case 5:
                    //varbind 5
                    parsingVarbind5(varBinds,agentIpaddress);

                    break;
                default:
                    //minimun varbind 2 else...
                    System.out.println("NO TRAP OBJECT WITH THIS VARBIND");

            }



        }
    }

    private void parsingVarbind5(Vector<? extends VariableBinding> varBinds,String agentIpaddress) {

        //varbind = 5  (ALARM: temperature,low voltage,low fuel)

        /*
        * TEMPERATURE:
        * 0 => InActive
        * 1 => Active low alarm
        * 2 => Active high alarm
        *
        * */

        /*
        * LOW VOLTAGE & LOW FUEL
        * 0 => InActive
        * 1 => Active
        * */

        VariableBinding varbind1;
        int var_monitoring;
        String varOID;
        VariableBinding varbind3;
        int var_treshold;
        VariableBinding varbind4;
        VariableBinding varbind2;
        int var_value;
        String varValue;
        varbind1 = varBinds.get(1);
        varOID = trimVarBindMessage.trim(String.valueOf(varbind1), "=");
        System.out.println("OID:" + varOID);

        varbind2 = varBinds.get(2);
        varValue = trimVarBindMessage.trim(String.valueOf(varbind2), "=");
        var_value = Integer.parseInt(varValue.trim());
        System.out.println("VALUE:" + var_value);

        varbind3 = varBinds.get(3);
        varbind4 = varBinds.get(4);
        var_monitoring = Integer.parseInt(String.valueOf(varbind3));
        var_treshold = Integer.parseInt(String.valueOf(varbind4));

        validationTrap(agentIpaddress, varOID, var_value);


    }

    private void parsingVarbind3(Vector<? extends VariableBinding> varBinds,String agentIpaddress) {

        VariableBinding varbind1;
        String varOID;
        VariableBinding varbind2;
        String varValue;
        int var_value;
        varbind1 = varBinds.get(1);
        varOID = trimVarBindMessage.trim(String.valueOf(varbind1), "=");
        System.out.println("OID:" + varOID);

        varbind2 = varBinds.get(2);
        varValue = trimVarBindMessage.trim(String.valueOf(varbind2), "=");
        var_value = Integer.parseInt(varValue.trim());
        System.out.println("VALUE:" + var_value);


        validationTrap(agentIpaddress, varOID, var_value);


    }

    private void validationTrap(String agentIpaddress, String varOID, int var_value) {

        try {
            //VALIDATION OID
            DeviceSNMPTrapModel deviceSNMPTrap = devicesDao.validTrapOID(varOID.trim(), agentIpaddress).getDeviceSNMPTrapModel();

            int snmp_trap_id = deviceSNMPTrap.getSnmp_trap_id();
            //System.out.println("TRAP ID:" + snmp_trap_id);

            String var_name = deviceSNMPTrap.getVar_name();
            System.out.println("TRAP NAME:" + var_name);

            int severity_id = deviceSNMPTrap.getSeverity_id();
            System.out.println("TRAP SEVERITY:" + severity_id);

            String type_trap = deviceSNMPTrap.getType_trap();
            //System.out.println("TRAP TYPE:" + type_trap);

            String region_oid = regionsDao.validAgentRegion(agentIpaddress).getOid();
            //System.out.println("REGION OID :" + region_oid);

            String city_oid = regionsDao.validAgentRegion(agentIpaddress).getSitesModel().getCity_oid();
            //System.out.println("CITY OID :" + city_oid);

            String device_node_name = deviceNodesDao.validAgentNode(agentIpaddress).getName();
            System.out.println("NODE NAME: " + device_node_name);

            String site_oid = deviceNodesDao.validAgentNode(agentIpaddress).getSite_oid();
            System.out.println("SITE OID: " + site_oid);

            String site_name = regionsDao.validAgentRegion(agentIpaddress).getSitesModel().getSite_name();
            System.out.println("SITE NAME:" + site_name);

            Integer device_node_id = deviceNodesDao.validAgentNode(agentIpaddress).getDevice_node_id();
            System.out.println("DEVICE NODE ID:" + device_node_id);

            String status_alarm;


            if (var_value == 0) {
                System.out.println("ALARM ACTIVE");
                status_alarm = "ACTIVE";

                trapHandler.saveToDatabase(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, status_alarm);

            } else {
                System.out.println("ALARM INACTIVE");
                status_alarm = "INACTIVE";

                trapHandler.saveToDatabase(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, status_alarm);

            }


        } catch (Exception e) {
            System.out.println("VAR OID NOT IDENTIFIED/NOT REGISTERED");

        }
    }


    //public void handlerV1(String agentIpaddress, Vector<? extends VariableBinding> varBinds) {
    //    System.out.println("create logic tsm100-10s"+ agentIpaddress);
    //    String device_name = deviceNodesDao.validAgentNode(agentIpaddress).getName();
    //    System.out.println("DEVICE NAME:"+ device_name);
    //    System.out.println("trap oid:"+ varBinds);
    //
    //}


}
