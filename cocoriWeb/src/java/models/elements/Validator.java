package models.elements;

import java.awt.Color;
import java.util.Date;

public abstract class Validator {

    private static String pattern_Only_Letters = "[a-zA-Z- 'áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœ]+";
    private static String message_Only_Letters_Last_Name = "<h4>Erreur</h4><p>Le nom ne doit comporter que des lettres</p>";
    private static String message_Only_Letters_First_Name = "<h4>Erreur</h4><p>Le prénom ne doit comporter que des lettres</p>";
    private static String message_Only_Letters_City = "<h4>Erreur</h4><p>La ville ne doit comporter que des lettres</p>";
    private static String pattern_Zip_Code = "^([0-9]{5})$";
    private static String message_Zip_Code = "<h4>Erreur</h4><p>Le code postal doit comporter 5 caractères numériques</p>";
    private static String pattern_Phone_Number = "^0[1-9][0-9]{8}$";
    private static String message_Phone_Number = "<h4>Erreur</h4><p>Le numéro de téléphone doit comporter 10 caractères numériques et commencer par un zéro</p>";
    private static String pattern_Email_Adress = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
    private static String message_Email_Adress = "<h4>Erreur</h4><p>L'adresse mail est invalide</p>";

    private static String message_Birthday = "<h4>Erreur</h4><p>La date de naissance est invalide</p>";

    public static boolean controlOnlyLettersLastName(String variable) throws Exception {
        return control(variable, pattern_Only_Letters, message_Only_Letters_Last_Name);
    }

    public static boolean controlOnlyLettersFirstName(String variable) throws Exception {
        return control(variable, pattern_Only_Letters, message_Only_Letters_First_Name);
    }

    public static boolean controlOnlyLettersCity(String variable) throws Exception {
        return control(variable, pattern_Only_Letters, message_Only_Letters_City);
    }

    public static boolean controlZipCode(String variable) throws Exception {
        return control(variable, pattern_Zip_Code, message_Zip_Code);
    }

    public static boolean controlPhoneNumber(String variable) throws Exception {
        return control(variable, pattern_Phone_Number, message_Phone_Number);
    }

    public static boolean controlEmailAdress(String variable) throws Exception {
        return control(variable, pattern_Email_Adress, message_Email_Adress);
    }

    public static boolean controlBirthDate(Date birthday) throws Exception {

        Date currentDate = new Date();

        if (birthday.compareTo(currentDate) < 0) {
            return true;
        } else {
            throw new Exception(message_Birthday);
        }
    }

    private static boolean control(String variable, String pattern, String message) throws Exception {
        if (variable.trim().equals("")) {
            return true;
        } else if (variable.matches(pattern)) {
            return true;
        } else {
            throw new Exception(message);
        }
    }

}
