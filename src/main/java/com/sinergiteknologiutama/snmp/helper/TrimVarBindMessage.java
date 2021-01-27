package com.sinergiteknologiutama.snmp.helper;

import org.springframework.stereotype.Component;

@Component
public class TrimVarBindMessage {

    public String trim(String value, String a){
        // Returns a substring containing all characters after a string.
        int posA = value.lastIndexOf(a);
        if (posA == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= value.length()) {
            return "";
        }
        return value.substring(adjustedPosA);
    }
}
