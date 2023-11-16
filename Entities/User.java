package Entities;

public class User{

    public String email;
    public String password;
    public String username = "";

    public User(String email,String password){
        this.email = email;
        this.password = password;

        for(int i = 0;i<email.length();i++){
            if(email.charAt(i)!='@'){
                this.username += Character.toString(email.charAt(i));
            }
            else
                break;
        }
    }

}