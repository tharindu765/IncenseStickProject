package lk.ijse.Util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField, String text){
        String filed = "";

        switch (textField){
            case NAME:
                filed = "^[a-zA-Z ]+$";
                break;
            case TELNUMBER:
                filed = "^(\\+94|0)?[1-9][0-9]{8}$";
                break;
            case NIC:
                filed = "^\\d{12}$";
                break;
            case PASSWORD:
                filed=".*";
                break;
            case ADDRESS:
                filed ="^[a-zA-Z0-9\\s,'.-]+$\n";
            break;
            case QTY:
            case BATCHID:
                filed = "^[0-9]+$";
                break;
            case PRICE:
                filed = "^[0-9]+$";
                break;
        }


        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }


    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField) {
        if (Regex.isTextFieldValid(location, textField.getText())) {
            textField.setStyle("-fx-background-radius: 30;-fx-focus-color: green; -fx-unfocus-color: green;");
            // textField.setStyle("-fx-control-inner-background: green; -fx-focus-color: green; -fx-unfocus-color: green;");

            return true;
        } else {
            textField.setStyle("-fx-background-radius: 30;-fx-focus-color: red; -fx-unfocus-color: red;");
            //textField.setStyle("-fx-control-inner-background: red; -fx-focus-color: red; -fx-unfocus-color: red;");
            return false;
        }
    }
}
