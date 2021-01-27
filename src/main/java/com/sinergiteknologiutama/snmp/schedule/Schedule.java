//package com.sinergiteknologiutama.snmp.schedule;
//
//
//import com.sinergiteknologiutama.snmp.execute.Execute;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Date;
//
//@Slf4j
//@Component
//public class Schedule {
//
//
//    final Execute execute;
//
//    public Schedule(Execute execute) {
//        this.execute = execute;
//    }
//
//
//    //@Scheduled(cron = "${cron.scheduler}")
//    @Scheduled(cron = "* 0/${cron.scheduler.minute} * * * *")
//    public void scheduledTask() {
//        //log.info("START POLLING AT:{}", new Date());
//        execute.initPolling();
//        execute.customProgram();
//
//    }
//
//}
