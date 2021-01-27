package com.sinergiteknologiutama.snmp.execute;

import com.sinergiteknologiutama.snmp.dao.*;
//import com.sinergiteknologiutama.snmp.model.SitesModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Receiver {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

    //final SitesDao sitesDao;
    final TrapReceiver trapReceiver;

    @Value("${trap.server}")
    private String trapServer;

    @Value("${trap.port}")
    private String trapPort;

    @Value("${server.poller}")
    private String serverPoller;



    public Receiver(TrapReceiver trapReceiver) {
        //this.sitesDao = sitesDao;
        this.trapReceiver = trapReceiver;
    }


    public void run() {

        String updated_at = dateFormat.format(new Date());

        System.out.println("START AT:" + updated_at);

        System.out.println("trap.server:"+ trapServer);
        System.out.println("trap.port:"+ trapPort);
        //pingAllNodeOnSite();
        trapReceiver.run();


        //init();

        //List<SitesModel> sitesModels = sitesDao.siteList();
        //for(SitesModel record : sitesModels){
        //    System.out.println("Site:"+record.getName());
        //}

    }



}
