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
import java.net.Socket;

public class Client extends Application {
    Socket socket;
    private TextField text_field_for_message;
    private TextArea chat_history_area;
    private ListView<String> userList;

    @Override
    public void start(Stage primary_stage) {
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 400);

        // Chat history panel
        chat_history_area = new TextArea();
        chat_history_area.setEditable(false);
        root.setCenter(chat_history_area);

        // User list
        userList = new ListView<>();
        userList.setPrefWidth(150);
        root.setRight(userList);

        // Chatbox
        text_field_for_message = new TextField();
        Button send_button = new Button("Send");
        HBox chatBox = new HBox(text_field_for_message, send_button);
        chatBox.setPadding(new Insets(10));
        root.setBottom(chatBox);

        // Create the scene
        Scene scene = new Scene(root);

        // Set the stage
        primary_stage.setTitle("Client");
        primary_stage.setScene(scene);
        primary_stage.show();
        try {
            socket = new Socket("localhost", 8000);
            chat_history_area.appendText("Request sent successfully \n");
        }
        catch (IOException ex) {
            chat_history_area.appendText(ex.toString() + '\n');
        }
            try {

                ClientInputData input_from_server = new ClientInputData(socket.getInputStream(), chat_history_area);
                ClientOutputData output_to_server = new ClientOutputData(socket.getOutputStream(), text_field_for_message, chat_history_area);

                Thread input_thread = new Thread(input_from_server);
                Thread output_thread = new Thread(output_to_server);
                input_thread.start();
                output_thread.start();

            }
            catch (IOException ex) {
                System.err.println(ex);
            }



    }
}