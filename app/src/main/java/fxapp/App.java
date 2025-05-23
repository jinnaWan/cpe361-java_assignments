package fxapp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fxapp.common.QuickSort;
import fxapp.common.Student;
import fxapp.common.StudentComparators;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private QuickSort<Student> quickSort = new QuickSort<>();
    private TextArea displayArea = new TextArea();
    
    // UI Controls
    private TextField idField = new TextField();
    private TextField firstNameField = new TextField();
    private TextField lastNameField = new TextField();
    private TextField gpaField = new TextField();
    private ComboBox<String> sortFieldComboBox;
    private ComboBox<String> sortOrderComboBox;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Sorter");
        
        // Setup the layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        
        // Add Student Form
        GridPane addForm = createAddStudentForm();
        
        // Sort Controls
        HBox sortControls = createSortControls();
        
        // Display Area
        displayArea.setEditable(false);
        displayArea.setPrefHeight(300);
        
        // Add all components to layout
        mainLayout.getChildren().addAll(
            new Label("Add New Student:"),
            addForm,
            new Separator(),
            new Label("Sort Students:"),
            sortControls,
            new Separator(),
            new Label("Student List:"),
            displayArea
        );
        
        // Initialize with sample data
        addSampleData();
        updateStudentDisplay();
        
        // Show the scene
        Scene scene = new Scene(mainLayout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private GridPane createAddStudentForm() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(5));
        
        // Labels and fields
        form.add(new Label("ID:"), 0, 0);
        form.add(idField, 1, 0);
        
        form.add(new Label("First Name:"), 0, 1);
        form.add(firstNameField, 1, 1);
        
        form.add(new Label("Last Name:"), 0, 2);
        form.add(lastNameField, 1, 2);
        
        form.add(new Label("GPA:"), 0, 3);
        form.add(gpaField, 1, 3);
        
        // Add and Delete buttons
        Button addButton = new Button("Add Student");
        addButton.setOnAction(e -> addStudent());
        
        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> deleteStudent());
        
        HBox buttons = new HBox(10, addButton, deleteButton);
        form.add(buttons, 1, 4);
        
        return form;
    }
    
    private HBox createSortControls() {
        HBox controls = new HBox(10);
        
        // Sort field selection
        sortFieldComboBox = new ComboBox<>();
        sortFieldComboBox.getItems().addAll("ID", "First Name", "Last Name", "GPA");
        sortFieldComboBox.setValue("ID");
        
        // Sort order selection
        sortOrderComboBox = new ComboBox<>();
        sortOrderComboBox.getItems().addAll("Ascending", "Descending");
        sortOrderComboBox.setValue("Ascending");
        
        // Sort button
        Button sortButton = new Button("Sort");
        sortButton.setOnAction(e -> sortStudents());
        
        controls.getChildren().addAll(
            new Label("Sort by:"), 
            sortFieldComboBox,
            new Label("Order:"), 
            sortOrderComboBox,
            sortButton
        );
        
        return controls;
    }
    
    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            double gpa = Double.parseDouble(gpaField.getText());
            
            if (firstName.isEmpty() || lastName.isEmpty()) {
                showAlert("Error", "First name and last name cannot be empty.");
                return;
            }
            
            if (gpa < 0 || gpa > 4.0) {
                showAlert("Error", "GPA must be between 0.0 and 4.0.");
                return;
            }
            
            students.add(new Student(id, firstName, lastName, gpa));
            clearInputFields();
            updateStudentDisplay();
            
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid number format. ID must be an integer and GPA must be a decimal.");
        }
    }
    
    private void deleteStudent() {
        String selectedText = displayArea.getSelectedText();
        if (selectedText != null && !selectedText.isEmpty()) {
            // Try to extract student ID from selection
            try {
                String idString = selectedText.substring(selectedText.indexOf("ID: ") + 4);
                int id = Integer.parseInt(idString.substring(0, idString.indexOf(",")));
                
                // Find and remove student with this ID
                students.removeIf(student -> student.getId() == id);
                updateStudentDisplay();
                
            } catch (Exception e) {
                showAlert("Error", "Please select a complete student entry to delete.");
            }
        } else {
            showAlert("Error", "Please select a student to delete.");
        }
    }
    
    private void sortStudents() {
        String field = sortFieldComboBox.getValue();
        boolean ascending = sortOrderComboBox.getValue().equals("Ascending");
        
        Comparator<Student> comparator;
        switch (field) {
            case "First Name":
                comparator = StudentComparators.BY_FIRST_NAME;
                break;
            case "Last Name":
                comparator = StudentComparators.BY_LAST_NAME;
                break;
            case "GPA":
                comparator = StudentComparators.BY_GPA;
                break;
            default:
                comparator = StudentComparators.BY_ID;
        }
        
        // Create a copy of the list to sort
        List<Student> sortedList = new ArrayList<>(students);
        
        // Apply quick sort
        quickSort.sort(sortedList, comparator, ascending);
        
        // Update the observable list
        students.clear();
        students.addAll(sortedList);
        
        updateStudentDisplay();
    }
    
    private void updateStudentDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Student student : students) {
            sb.append(student).append("\n");
        }
        displayArea.setText(sb.toString());
    }
    
    private void clearInputFields() {
        idField.clear();
        firstNameField.clear();
        lastNameField.clear();
        gpaField.clear();
    }
    
    private void addSampleData() {
        students.addAll(
            new Student(1, "John", "Doe", 3.5),
            new Student(2, "Jane", "Smith", 3.8),
            new Student(3, "Bob", "Johnson", 3.2),
            new Student(4, "Alice", "Williams", 4.0),
            new Student(5, "Charlie", "Brown", 2.9)
        );
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
