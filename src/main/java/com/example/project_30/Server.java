package com.example.project_30;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {
    private TextField text_field_for_message;
    private TextArea chat_history_area;
    private ListView<String> user_list;
    @Override
    public void start(Stage primary_stage) {
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 400);

        // Chat history panel
        chat_history_area = new TextArea();
        chat_history_area.setEditable(false);
        root.setCenter(chat_history_area);

        // User list
        user_list = new ListView<>();
        user_list.setPrefWidth(150);
        root.setRight(user_list);

        // Chatbox
        text_field_for_message = new TextField();
        Button send_button = new Button("Send");
        HBox chatBox = new HBox(text_field_for_message, send_button);
        chatBox.setPadding(new Insets(10));
        root.setBottom(chatBox);

        // Create the scene
        Scene scene = new Scene(root);

        // Set the stage
        primary_stage.setTitle("Server");
        primary_stage.setScene(scene);
        primary_stage.show();

        new Thread(() -> {
            try {
                ServerSocket server_socket = new ServerSocket(8000);
                chat_history_area.appendText("Waiting for client request \n");
                Socket socket = server_socket.accept();
                chat_history_area.appendText("New client is pop up \n");

                ServerInputData input_from_client = new ServerInputData(socket.getInputStream(), chat_history_area);
                ServerOutputData output_to_client = new ServerOutputData(socket.getOutputStream(), text_field_for_message, chat_history_area, send_button);

                Thread input_thread = new Thread(input_from_client);
                Thread output_thread = new Thread(output_to_client);
                input_thread.start();
                output_thread.start();

            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
