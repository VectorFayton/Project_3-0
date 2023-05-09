package com.example.project_30;

public class Packet {
    private  int serial_number;
    private  String data;

    public Packet (int serial_number, String data){
        this.serial_number = serial_number;
        this.data = data;
    }

    public String getData(){
        return data;
    }

    public int getSerialNo(){
        return serial_number;
    }

    public String toString(){
        return serial_number + "," + data;
    }
}
