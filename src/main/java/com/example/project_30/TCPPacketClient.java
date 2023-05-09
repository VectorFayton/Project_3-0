package com.example.project_30;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class TCPPacketClient extends Application {

    BufferedWriter output_stream_to_server = null;
    BufferedReader input_stream_from_server = null;
    BufferedReader input_from_console = null;
    @Override
    public void start(Stage primaryStage) {
        // Panel p to hold the label and text field
        BorderPane pane_for_text_field = new BorderPane();
        pane_for_text_field.setPadding(new Insets(5, 5, 5, 5));
        pane_for_text_field.setStyle("-fx-border-color: green");
        pane_for_text_field.setLeft(new Label("Enter a message: "));

        TextField message_text_field = new TextField();
        message_text_field.setAlignment(Pos.BOTTOM_RIGHT);
        pane_for_text_field.setCenter(message_text_field);

        BorderPane mainPane = new BorderPane();
        // Text area to display contents
        TextArea chat_history_area = new TextArea();
        mainPane.setCenter(new ScrollPane(chat_history_area));
        mainPane.setTop(pane_for_text_field);

        // Create a scene and place it in the stage
        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        AtomicInteger number_of_packet = new AtomicInteger(1);

        message_text_field.setOnAction(e -> {
            try {
//                while (true) {
                // Data
                chat_history_area.appendText("Enter the data packet: \n");
                String data = message_text_field.getText();

                Packet packet = new Packet(number_of_packet.get(), data);

                output_stream_to_server.write(packet.getSerialNo() + "\n");
                output_stream_to_server.flush();

                output_stream_to_server.write(packet.getData() + "\n");
                output_stream_to_server.flush();

                String data_from_server = input_stream_from_server.readLine();
                chat_history_area.appendText("FROM SERVER: " + data_from_server + "\n");
                message_text_field.clear();

                if (data.equalsIgnoreCase("close")) {
                    primaryStage.close();
                }

                number_of_packet.getAndIncrement();
//                }
            } catch (IOException exception){
                System.err.println(exception);
            }
        });

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);
            // Socket socket = new Socket("130.254.204.36", 8000);
            // Socket socket = new Socket("drake.Armstrong.edu", 8000);

            // Create an input stream to receive data from the server
            input_from_console = new BufferedReader(new InputStreamReader(System.in));
            input_stream_from_server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output_stream_to_server = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException ex) {
            chat_history_area.appendText(ex.toString() + '\n');
        }
    }
}