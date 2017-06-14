/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2_1181;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author staceylawson
 */
public class Project2_1181 extends Application {

    private Label ti;
    private Label form;
    private Label loanedName;
    private Label loanedDate;
    private Label sort;
    private TextField t1;
    private TextField t2;
    private TextField t3;
    private TextField t4;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private RadioButton rb1;
    private RadioButton rb2;

    /**
     * @param args the command line arguments
     */
    MediaCollection mc = new MediaCollection();

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane gridPane = new GridPane();
        primaryStage.setTitle("Media Collection");
        //set up list view
        ObservableList<MediaItem> media = FXCollections.observableArrayList();
        ListView<MediaItem> list = new ListView<>();
        list.setPrefSize(450, 800);
        list.setItems(media);

        gridPane.add(list, 0, 0, 2, 20);
        gridPane.setHgap(10);

        ti = new Label("Title: ");
        form = new Label("Format: ");
        loanedName = new Label("Loaned To: ");
        loanedDate = new Label("Loaned On: ");
        sort = new Label("Sort");
        t1 = new TextField("");
        t2 = new TextField("");
        t3 = new TextField("");
        t4 = new TextField("");
        b1 = new Button("Add");
        b2 = new Button("Remove");
        b3 = new Button("Return");
        b4 = new Button("Loan");

        VBox left = new VBox(ti, form, b1);
        left.setSpacing(20);
        left.setPadding(new Insets(30, 10, 20, 10));

        //set up title and format box with an add button
        VBox right = new VBox(t1, t2);
        right.setSpacing(10);
        right.setPadding(new Insets(20, 10, 10, 10));

        HBox info = new HBox(left, right);
        info.setPadding(new Insets(30, 10, 10, 10));
        info.setStyle("-fx-border-color: black");
        gridPane.add(info, 4, 2, 1, 2);

        //set function for add button
        b1.setOnAction(e -> {
            try {
                String name = t1.getText();
                String type = t2.getText();
                MediaItem item = new MediaItem(name, type);
                //make sure duplicate items are not added
                if (!media.contains(item)) {

                    media.add(item);
                    list.refresh();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });
        //end set function for add button

        //set up remove button
        HBox middle = new HBox(b2);
        middle.setSpacing(20);
        middle.setPadding(new Insets(60, 10, 20, 10));
        gridPane.add(middle, 4, 8, 1, 1);

        //set up remove item button
        b2.setOnAction(e -> {
            MediaItem item = list.getSelectionModel().getSelectedItem();

            // Display alert dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                media.remove(item);
                list.refresh();

            } else {
                // ... user chose CANCEL or closed the dialog
            }

        });

        //set up return button
        HBox middle2 = new HBox(b3);
        middle2.setSpacing(20);
        middle2.setPadding(new Insets(10, 10, 30, 10));
        gridPane.add(middle2, 4, 9, 1, 1);

        b3.setOnAction(e -> {
            MediaItem item = list.getSelectionModel().getSelectedItem();

            try {
                item.returnItem();
                list.refresh();

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });
        //end set function for add button
        //set up loaned to and loaned on box with loan button
        VBox left2 = new VBox(loanedName, loanedDate, b4);
        left2.setSpacing(20);
        left2.setPadding(new Insets(20, 10, 30, 10));

        VBox right2 = new VBox(t3, t4);
        right2.setSpacing(10);
        right2.setPadding(new Insets(10, 10, 30, 10));

        HBox loaned = new HBox(left2, right2);
        loaned.setStyle("-fx-border-color: black");
        gridPane.add(loaned, 4, 12, 1, 1);

        b4.setOnAction(e -> {
            MediaItem item = list.getSelectionModel().getSelectedItem();
            try {
                String loanedTo = t3.getText();
                String date = t4.getText();

                if (mc.isLoanable(item)) {

                    Date loanedOn = new SimpleDateFormat(
                            "MM-dd-yyyy").parse(date);

                    item.loan(loanedTo, loanedOn);
                    list.refresh();
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });

        //set up radio buttons for sorting
        ToggleGroup group = new ToggleGroup();
        rb1 = new RadioButton("By title");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        rb2 = new RadioButton("By date loaned");
        rb2.setToggleGroup(group);
        rb1.setPadding(new Insets(0, 10, 10, 10));
        rb2.setPadding(new Insets(0, 10, 10, 10));

        VBox bottom = new VBox(sort, rb1, rb2);
        sort.setPadding(new Insets(20, 20, 10, 10));
        gridPane.add(bottom, 4, 17, 1, 2);

        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
//   

    public static void main(String[] args) {
        Application.launch(args);
        

    }
}
