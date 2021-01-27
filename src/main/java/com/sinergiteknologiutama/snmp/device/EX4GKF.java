package com.sinergiteknologiutama.snmp.device;

import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.smi.VariableBinding;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
public class EX4GKF {

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
            System.out.println("EX4GKF SNMP V.1 IS UNDER DEVELOPMENT, CALL PT.SINERGI FOR FURTHER INFORMATION");


        }
    }

    private void trapV2(PDU pdu, String agentIpaddress, Vector<? extends VariableBinding> varBinds) {
        if (pdu.getType() == PDU.TRAP) {
            //logic for v2
            System.out.println("===== NEW TRAP SNMP VERSION 2c/3 RECEIVED ====");
            //System.out.println("ORI VARBIND: " + pdu.getVariableBindings());
            System.out.println("AGENT IPADDRESS : " + agentIpaddress);
            System.out.println("Varbinds:" + varBinds);
            System.out.println("EX4GKF SNMP V.2 IS UNDER DEVELOPMENT, CALL PT.SINERGI FOR FURTHER INFORMATION");


        }
    }
}
