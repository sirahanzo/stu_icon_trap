package com.sinergiteknologiutama.snmp.execute;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TrapConfig {

    public static String TRAP_SERVER;
    public static String TRAP_PORT;

    @Value("${trap.server}")
    public void setTrapServer(String ipaddress) {
        TRAP_SERVER = ipaddress;
    }

    @Value("${trap.port}")
    public void setTrapPort(String port) {
        TRAP_SERVER = port;
    }



}
