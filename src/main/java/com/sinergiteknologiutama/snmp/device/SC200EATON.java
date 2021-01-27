package com.sinergiteknologiutama.snmp.device;

import com.sinergiteknologiutama.snmp.dao.*;
import com.sinergiteknologiutama.snmp.execute.TrapHandler;
import com.sinergiteknologiutama.snmp.helper.TrimVarBindMessage;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.smi.VariableBinding;
import org.springframework.stereotype.Component;

import java.util.Vector;


@Component
public class SC200EATON {

    final RegionsDao regionsDao;
    final DevicesDao devicesDao;
    final DeviceNodesDao deviceNodesDao;
    final TrimVarBindMessage trimVarBindMessage;
    final TrapMessageReceivedDao trapMessageReceivedDao;
    final TemporaryAlarmDao temporaryAlarmDao;
    final TrapDataDevicesDao trapDataDevicesDao;
    final LogAlarmDao logAlarmDao;
    final TrapHandler trapHandler;

    public SC200EATON(RegionsDao regionsDao, DevicesDao devicesDao, DeviceNodesDao deviceNodesDao, TrimVarBindMessage trimVarBindMessage,
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
            System.out.println("SC200 SNMP V.1 IS UNDER DEVELOPMENT CONTACT PT.SINERGI FOR FURTHER INFORMATION");


        }
    }

    private void trapV2(PDU pdu, String agentIpaddress, Vector<? extends VariableBinding> varBinds) {

        if (pdu.getType() == PDU.TRAP) {
            //logic for v2
            System.out.println("===== NEW TRAP SNMP VERSION 2c/3 RECEIVED ====");
            //System.out.println("ORI VARBIND: " + pdu.getVariableBindings());
            System.out.println("AGENT IPADDRESS : " + agentIpaddress);
            System.out.println("Varbinds:" + varBinds);




        }
    }
}
