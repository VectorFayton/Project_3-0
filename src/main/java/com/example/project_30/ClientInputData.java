package com.example.project_30;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientInputData implements Runnable {
    InputStream input_stream;
    TextArea chat_history;

    public ClientInputData(InputStream input_stream, TextArea chat_history){
        this.input_stream = input_stream;
        this.chat_history = chat_history;
    }

    @Override
    public void run() {
        try {

            BufferedReader input_from_server = new BufferedReader(new InputStreamReader(input_stream));
            while (true) {
                chat_history.appendText("Server: " + input_from_server.readLine() + "\n");
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}