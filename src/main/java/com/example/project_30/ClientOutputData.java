package com.example.project_30;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class ClientOutputData implements  Runnable {
    OutputStream output_stream;
    TextField message_text_field;
    TextArea chat_history_area;
    Button send_button;

    public ClientOutputData(OutputStream output_stream, TextField message_text_field, TextArea chat_history_area, Button send_button){
        this.output_stream = output_stream;
        this.message_text_field = message_text_field;
        this.chat_history_area = chat_history_area;
        this.send_button = send_button;
    }

    @Override
    public void run() {
        PrintStream output_to_server = new PrintStream(output_stream);
        message_text_field.setOnAction(e -> {
            String message = message_text_field.getText();
            output_to_server.println(message);
            sendMessage();
        });

        send_button.setOnAction( e -> {
            String message = message_text_field.getText();
            output_to_server.println(message);
            sendMessage();
        });

    }
    private void sendMessage() {
        String message = message_text_field.getText();
        if (!message.isEmpty()) {
            chat_history_area.appendText(String.format("Me (%s): %s \n", new Date(), message));
            message_text_field.clear();
        }
    }
}
