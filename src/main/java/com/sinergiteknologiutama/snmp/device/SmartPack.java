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
public class SmartPack {

    final RegionsDao regionsDao;
    final DevicesDao devicesDao;
    final DeviceNodesDao deviceNodesDao;
    final TrimVarBindMessage trimVarBindMessage;
    final TrapMessageReceivedDao trapMessageReceivedDao;
    final TemporaryAlarmDao temporaryAlarmDao;
    final TrapDataDevicesDao trapDataDevicesDao;
    final LogAlarmDao logAlarmDao;
    final TrapHandler trapHandler;

    public SmartPack(RegionsDao regionsDao, DevicesDao devicesDao, DeviceNodesDao deviceNodesDao, TrimVarBindMessage trimVarBindMessage,
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
            System.out.println("ELTEK SNMP V.1 IS UNDER DEVELOPMENT CONTACT PT.SINERGI FOR FURTHER INFORMATION");


        }
    }

    private void trapV2(PDU pdu, String agentIpaddress, Vector<? extends VariableBinding> varBinds) {
        if (pdu.getType() == PDU.TRAP) {
            //logic for v2
            System.out.println("===== NEW TRAP SNMP VERSION 2c/3 RECEIVED ====");
            //System.out.println("ORI VARBIND: " + pdu.getVariableBindings());
            System.out.println("AGENT IPADDRESS : " + agentIpaddress);
            System.out.println("Varbinds:" + varBinds);

            /*
            * ALARM SUBSYSSTATUS
            * error (0),
            * normal (1),
            * minorAlarm (2),
            * majorAlarm (3),
            * disabled (4),
            * disconnected (5),
            * notPresent (6),
            * minorAndMajor (7),
            * majorLow (8),
            * minorLow (9),
            * majorHigh (10),
            * minorHigh (11),
            * event (12),
            * valueVolt (13),
            * valueAmp (14),
            * valueTemp (15),
            * valueUnit (16),
            * valuePerCent (17),
            * critical (18),
            * warning (19)
            *
            * todo:26/01/2021 , use severity setting from nms , so severity from device will be ignored
            * use only : 0,1,2,3,18,19
            * */

            /*
            * STATUS ALARM
            * 1 => ACTIVE
            * 0 => INACTIVE
            *
            * */

            VariableBinding varbind1 = varBinds.get(1);
            String varOID = trimVarBindMessage.trim(String.valueOf(varbind1), "=");
            System.out.println("OID:" + varOID);


            //todo: this severity will be ignored , use from nms severity setting
            //VariableBinding varbind2 = varBinds.get(4);
            //String varSeverity = trimVarBindMessage.trim(String.valueOf(varbind2), "=");
            //int severity = Integer.parseInt(varSeverity.trim());
            //System.out.println("SEVERITY:" + varSeverity);


            VariableBinding varbind3 = varBinds.get(5);
            String varValue = trimVarBindMessage.trim(String.valueOf(varbind3), "=");
            int var_value = Integer.parseInt(varValue);
            System.out.println("VALUE:" + varValue.trim());


            try{
                //VALIDATION OIO
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

                TSM100P.dualTypeTrap(agentIpaddress, varOID, var_value, snmp_trap_id, var_name, severity_id, region_oid, city_oid, device_node_name, site_oid, site_name, device_node_id, trapHandler);

            }catch (Exception e){
                System.out.println("VAR OID NOT IDENTIFIED/NOT REGISTERED");

            }




        }
    }
}
