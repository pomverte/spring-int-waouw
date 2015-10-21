package com.fr.cgi.atp.message;

import lombok.Getter;

@Getter
public class WaouwMessage {
    private Integer number;
    private String userName;

    public WaouwMessage(Integer number, String userName) {
        this.number = number;
        this.userName = userName;
    }
}
