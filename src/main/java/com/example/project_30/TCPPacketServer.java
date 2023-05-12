package com.example.project_30;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
public class TCPPacketServer extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Text area for displaying contents
        TextArea chat_history_area = new TextArea();
        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(chat_history_area), 450, 200);
        primaryStage.setTitle("Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(() ->
                        chat_history_area.appendText("Server started at " + new Date() + '\n'));
                // Listen for a connection request
                Socket socket = serverSocket.accept();
                chat_history_area.appendText("Connection is established! \n");
                // Create data input and output streams
                // Output and Input
                BufferedReader input_stream_from_client = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter output_stream_to_client = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                // Data
                while (true) {
                    String packet_serial_number = input_stream_from_client.readLine();
                    chat_history_area.appendText("Received from \"Clients\" Packet's serial number is: " + packet_serial_number + "\n");
                    String data_of_packet_from_client = input_stream_from_client.readLine();
                    data_of_packet_from_client.toUpperCase();
                    chat_history_area.appendText("and packet's data is: " + data_of_packet_from_client.toUpperCase() + "\n");

                    output_stream_to_client.write("Packet's serial number " + packet_serial_number + " is received" + "\n");
                    output_stream_to_client.flush();
                    if (data_of_packet_from_client.equalsIgnoreCase("close")) {
                        chat_history_area.setText("Closed");
                        System.exit(0);
                        primaryStage.close();
                    }
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}