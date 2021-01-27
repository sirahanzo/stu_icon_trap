package com.sinergiteknologiutama.snmp;

import com.sinergiteknologiutama.snmp.execute.TrapReceiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SnmpTrapApplication {

    final TrapReceiver trapReceiver;

    public SnmpTrapApplication(TrapReceiver trapReceiver) {
        this.trapReceiver = trapReceiver;
    }


    public static void main(String[] args) {
        SpringApplication.run(SnmpTrapApplication.class, args);
    }


    @PostConstruct
    public void init() {
        trapReceiver.run();

    }


}
