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
public class TSM100 {

    final RegionsDao regionsDao;
    final DevicesDao devicesDao;
    final DeviceNodesDao deviceNodesDao;
    final TrimVarBindMessage trimVarBindMessage;
    final TrapMessageReceivedDao trapMessageReceivedDao;
    final TemporaryAlarmDao temporaryAlarmDao;
    final TrapDataDevicesDao trapDataDevicesDao;
    final LogAlarmDao logAlarmDao;
    final TrapHandler trapHandler;

    public TSM100(RegionsDao regionsDao, DevicesDao devicesDao, DeviceNodesDao deviceNodesDao, TrimVarBindMessage trimVarBindMessage,
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

        //trapOID = varBinds.get(1);
        //trapValue = varBinds.get(2);
        //System.out.println("OID :" + trimVarBindMessage(String.valueOf(trapOID), "="));
        //System.out.println("VALUE :" + trimVarBindMessage(String.valueOf(trapValue), "="));
        //System.out.println("OID:"+trimVarBindMessage.trim(String.valueOf(varBinds.get(1)),"="));


        if (pdu.getType() == PDU.V1TRAP) {
            //logic for v1

            PDUv1 pduV1 = (PDUv1) pdu;

            //System.out.println("");
            System.out.println("===== NEW TRAP SNMP VERSION 1 RECEIVED ====");
            //System.out.println("ENTERPRISE: " + pduV1.getEnterprise().toString());
            System.out.println("AGENT IPADDRESS : " + agentIpaddress);
            System.out.println("Varbinds:" + varBinds);
            System.out.println("TSP100 SNMP V.1 IS UNDER DEVELOPMENT, CONTACT PT.SINERGI FOR FURTHER INFORMATION");

        }
    }

    private void trapV2(PDU pdu, String agentIpaddress, Vector<? extends VariableBinding> varBinds) {

        if (pdu.getType() == PDU.TRAP) {
            //logic for v2
            System.out.println("===== TRAP SNMP VERSION 2c/3 =====");

            //total varbinds = 3

            VariableBinding varbind1 = varBinds.get(1);
            String varOID = trimVarBindMessage.trim(String.valueOf(varbind1), "=");
            System.out.println("OID:" + varOID);

            VariableBinding varbind2 = varBinds.get(2);
            String varValue = trimVarBindMessage.trim(String.valueOf(varbind2), "=");
            int var_value = Integer.parseInt(varValue.trim());
            System.out.println("VALUE:" + var_value);

            try {
                //VALIDATION OID
                //DevicesModel snmp_trap_object = devicesDao.validTrapOID(varOID.trim(), agentIpaddress);
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

                switch (type_trap.trim()){
                    case "ACTIVE":
                    case "INACTIVE":
                        //ALARM INACTIVE
                        // ALARM ACTIVE
                        status_alarm = type_trap;
                        trapHandler.saveToDatabase(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, status_alarm);

                        break;

                    default:
                        //DUAL ALARM
                        //dualTypeTrap(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, trapHandler);
                        TSM100P.dualTypeTrap(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, trapHandler);

                }

                //switch (varOID.trim()) {
                //    //HIGH TEMPERATURE ACTIVE
                //    case "1.3.6.1.4.1.47865.3.20.20.3000":
                //        //active
                //        status_alarm = "ACTIVE";
                //        //System.out.println(status_alarm);
                //
                //        //saveToDatabase(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, status_alarm);
                //        trapHandler.saveToDatabase(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, status_alarm);
                //
                //        break;
                //    //HIGH TEMPERATURE IN ACTIVE
                //    case "1.3.6.1.4.1.47865.3.20.20.4000":
                //        status_alarm = "INACTIVE";
                //        //System.out.println("INACTIVE");
                //
                //        trapHandler.saveToDatabase(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, status_alarm);
                //
                //        break;
                //    default:
                //        //dual
                //        //System.out.println("DUAL");
                //
                //        TSM100P.dualTypeTrap(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, trapHandler);
                //}

            } catch (Exception e) {
                System.out.println("VAR OID NOT IDENTIFIED/NOT REGISTERED");
            }

        }
    }

    static void dualTypeTrap(String agentIpaddress, String varOID, int var_value, int snmp_trap_id, String var_name, int severity_id, String region_oid, String city_oid, String device_node_name, String site_oid, String site_name, Integer device_node_id, TrapHandler trapHandler) {
        String status_alarm;
        if (var_value == 1) {
            //active
            status_alarm = "ACTIVE";
            //System.out.println("ACTIVE");
            trapHandler.saveToDatabase(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, status_alarm);

        } else {
            //inactive
            status_alarm = "INACTIVE";
            //System.out.println("INACTIVE");
            trapHandler.saveToDatabase(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, status_alarm);

        }
    }

}
