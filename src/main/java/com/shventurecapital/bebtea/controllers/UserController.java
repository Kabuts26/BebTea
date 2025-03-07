package com.shventurecapital.bebtea.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.shventurecapital.bebtea.config.StageManager;
import com.shventurecapital.bebtea.models.User;
import com.shventurecapital.bebtea.services.impl.UserService;

//import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Controller
public class UserController implements Initializable {
    
    @FXML
    private Label userId;
    
    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private ChoiceBox<String> flavor;

    @FXML
    private TextField quantity;

    @FXML
    private RadioButton rbSmall;

    @FXML
    private ToggleGroup rbSize;

    @FXML
    private RadioButton rbMedium;

    @FXML
    private RadioButton rbLarge;

    @FXML
    private Button save;
    
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TableColumn<User, String> colAddress;

    @FXML
    private TableColumn<User, String> colFlavor;

    @FXML
    private TableColumn<User, Integer> colQuantity;

    @FXML
    private TableColumn<User, String> colSize;
    
    @FXML
    private TableColumn<User, Double> colAmount;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private UserService userService;
    
    @FXML
    void Select(ActionEvent event) {
    }
    
        private ObservableList<User> userList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }


    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void saveUser(ActionEvent event) {

            if (userId.getText() == null || "".equals(userId.getText())) {
                if (true) {

                    User user = new User();
                    user.setName(getName());
                    user.setAddress(getAddress());
                    user.setFlavor(getFlavor());
                    user.setSize(getSize());
                    user.setQuantity(getQuantity());

                    User newUser = userService.save(user);

                    saveAlert(newUser);
                } 

            } else {
                User user = userService.find(Long.parseLong(userId.getText()));
                user.setName(getName());
                user.setAddress(getAddress());
                user.setFlavor(getFlavor());
                user.setSize(getSize());
                user.setQuantity(getQuantity());
                User updatedUser = userService.update(user);
                updateAlert(updatedUser);
            }

            clearFields();
//            loadUserDetails();

    }

    @FXML
//    private void deleteUsers(ActionEvent event) {
//        List<User> users = userTable.getSelectionModel().getSelectedItems();
//
//        Alert alert = new Alert(AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation Dialog");
//        alert.setHeaderText(null);
//        alert.setContentText("Are you sure you want to delete selected?");
//        Optional<ButtonType> action = alert.showAndWait();
//
//        if (action.get() == ButtonType.OK) {
//            userService.deleteInBatch(users);
//        }
//
//        loadUserDetails();
//    }

    private void clearFields() {
        userId.setText(null);
        name.clear();
        address.clear();
        flavor.getValue();
        rbLarge.setSelected(true);
        rbMedium.setSelected(false);
        rbSmall.setSelected(false);
        quantity.clear();
    }

    private void saveAlert(User user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getName() + " " + user.getAddress() + " has been created and \n"+user.getFlavor()+"" + getSizeTitle(user.getSize()) +""+user.getQuantity()+ " id is " + user.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(User user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getName() + " " + user.getAddress() + " has been updated.");
        alert.showAndWait();
    }

    private String getSizeTitle(String size) {
        return (size.equals("Medium")) ? "Large" : "Small";
    }

    public String getName() {
        return name.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public String getFlavor() {
        return flavor.getSelectionModel().getSelectedItem();
    }
    
    public String getQuantity(){
        return quantity.getText();
    }
    
    
    public String getSize() {
        return rbMedium.isSelected() ? "Large" : "Small";
    }
    
    

    /*
	 *  Set All userTable column properties
     */
    private void setColumnProperties() {
        /* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/

        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colFlavor.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory
            = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
        @Override
        public TableCell<User, Boolean> call(final TableColumn<User, Boolean> param) {
            final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/logo.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            User user = getTableView().getItems().get(getIndex());
                            updateUser(user);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void updateUser(User user) {
                    userId.setText(Long.toString(user.getId()));
                    name.setText(user.getName());
                    address.setText(user.getAddress());
                    flavor.setValue(user.getFlavor());
                    if (user.getSize().equals("Large")) {
                        rbLarge.setSelected(true);
                    }else if(user.getSize().equals("Medium")){
                        rbMedium.setSelected(true);
                    }else {
                        rbSmall.setSelected(true);
                    }
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All users to observable list and update table
     */
    private void loadUserDetails() {
        userList.clear();
        userList.addAll(userService.findAll());

        userTable.setItems(userList);
    }

    /*
	 * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();
        // Add all users into table
        loadUserDetails();
        
        flavor.getItems().addAll("Hokaido","WinterMelon","Matcha");
        flavor.setValue("Hokaido");

    }

    
}

