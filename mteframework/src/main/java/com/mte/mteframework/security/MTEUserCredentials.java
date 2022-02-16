package com.mte.mteframework.security;

public class MTEUserCredentials
{
    public String Email;
    public String Username;
    public String Name;
    public String LastName;
    public String Password;
    public boolean LoggedIn;
    public boolean IsNewUser;
    public int UserId;
    public String ObjectId;



    //====================================================================
    //====================================================================
    public MTEUserCredentials()
    {
        this.Username = "Undefined";
        this.Password = "null";
        this.Email="user@domain.com";
        this.LoggedIn = false;
        this.IsNewUser =false;
        this.UserId = -1;
        this.ObjectId="";
        this.Name = "Undefined";
        this.LastName = "Undefined";
    }
    //====================================================================
    //====================================================================
    public MTEUserCredentials(String uname, String password, String email)
    {this.Username = uname;
        this.Password = password;
        this.Email = email;
        this.LoggedIn = false;
        this.IsNewUser =false;
        this.UserId = -1;
        this.ObjectId="";
        this.Name = "Undefined";

    }
    //====================================================================
    //====================================================================
    public MTEUserCredentials(String name,String uname, String password, String email)
    {this.Username = uname;
        this.Password = password;
        this.Email = email;
        this.LoggedIn = false;
        this.IsNewUser =false;
        this.UserId = -1;
        this.ObjectId="";
        this.Name = name;

    }
    //====================================================================
    //====================================================================
    public void setLoggedIn()
    {this.LoggedIn=true;}
    //====================================================================
    //====================================================================
    public void setLoggedOut()
    {
        this.LoggedIn=false;
    }
    //====================================================================
    //====================================================================
}
