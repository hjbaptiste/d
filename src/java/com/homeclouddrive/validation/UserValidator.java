package com.homeclouddrive.validation;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.sql.Time;
import java.math.BigDecimal;
import com.jigy.api.Helpful;


public class UserValidator {


public static String validate(HttpServletRequest request) {

String idUser = Helpful.getRequestParamStrSafe("idUser", request);
String emailAddress = Helpful.getRequestParamStrSafe("emailAddress", request);
String password = Helpful.getRequestParamStrSafe("password", request);
String firstName = Helpful.getRequestParamStrSafe("firstName", request);
String lastName = Helpful.getRequestParamStrSafe("lastName", request);
String dtSignUp = Helpful.getRequestParamStrSafe("dtSignUp", request);
String dtServiceExpires = Helpful.getRequestParamStrSafe("dtServiceExpires", request);
String tempPassword = Helpful.getRequestParamStrSafe("tempPassword", request);
String isSubscribed = Helpful.getRequestParamStrSafe("isSubscribed", request);
String role = Helpful.getRequestParamStrSafe("role", request);
String status = Helpful.getRequestParamStrSafe("status", request);
String dtLastUpdate = Helpful.getRequestParamStrSafe("dtLastUpdate", request);


if(Helpful.isEmpty(emailAddress)){
return "EMAIL ADDRESS is a Required Field";
}
if(emailAddress.length() > 45){
return "EMAIL ADDRESS Cannot Exceed 45 Characters";
}
if(!Helpful.isValidEmailAddress(emailAddress)){
return "Please Enter a Valid Email Address";
}
if(Helpful.isEmpty(password)){
return "PASSWORD is a Required Field";
}
if(password.length() > 50){
return "PASSWORD Cannot Exceed 50 Characters";
}
if(firstName.length() > 45){
return "FIRST NAME Cannot Exceed 45 Characters";
}
if(lastName.length() > 45){
return "LAST NAME Cannot Exceed 45 Characters";
}
if(Helpful.isEmpty(dtSignUp)){
return "DT SIGN UP is a Required Field";
}
try {
Date.parse(dtSignUp);
} catch(IllegalArgumentException e) { 
return "DT SIGN UP is Not a Valid Date";
}
try {
Date.parse(dtServiceExpires);
} catch(IllegalArgumentException e) { 
return "DT SERVICE EXPIRES is Not a Valid Date";
}
if(tempPassword.length() > 20){
return "TEMP PASSWORD Cannot Exceed 20 Characters";
}
if(isSubscribed.length() > 1){
return "IS SUBSCRIBED Cannot Exceed 1 Characters";
}
if(Helpful.isEmpty(role)){
return "ROLE is a Required Field";
}
if(role.length() > 1){
return "ROLE Cannot Exceed 1 Characters";
}
if(Helpful.isEmpty(status)){
return "STATUS is a Required Field";
}
if(status.length() > 1){
return "STATUS Cannot Exceed 1 Characters";
}


return null;
}
}
