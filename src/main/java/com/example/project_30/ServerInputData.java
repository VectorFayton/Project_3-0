package com.example.project_30;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class ServerInputData implements Runnable {
    InputStream input_stream;
    TextArea chat_history;
    public ServerInputData(InputStream input_stream, TextArea chat_history){
        this.input_stream = input_stream;
        this.chat_history = chat_history;
    }

    @Override
    public void run(){
        try {

            BufferedReader input_from_client = new BufferedReader(new InputStreamReader(input_stream));
            while (true) {
                chat_history.appendText("Client " + new Date() + ": " + input_from_client.readLine() + "\n");
            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
