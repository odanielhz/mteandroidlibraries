package com.mte.mteframework.security;

import android.os.Parcel;
import android.os.Parcelable;

public class MTEUserCredentials implements Parcelable
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
    public int AccountType;



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
        this.AccountType = 0;
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

    protected MTEUserCredentials(Parcel in) {
        Email = in.readString();
        Username = in.readString();
        Name = in.readString();
        LastName = in.readString();
        Password = in.readString();
        LoggedIn = in.readByte() != 0;
        IsNewUser = in.readByte() != 0;
        UserId = in.readInt();
        ObjectId = in.readString();
        AccountType = in.readInt();
    }

    public static final Creator<MTEUserCredentials> CREATOR = new Creator<MTEUserCredentials>() {
        @Override
        public MTEUserCredentials createFromParcel(Parcel in) {
            return new MTEUserCredentials(in);
        }

        @Override
        public MTEUserCredentials[] newArray(int size) {
            return new MTEUserCredentials[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Email);
        dest.writeString(Username);
        dest.writeString(Name);
        dest.writeString(LastName);
        dest.writeString(Password);
        dest.writeByte((byte) (LoggedIn ? 1 : 0));
        dest.writeByte((byte) (IsNewUser ? 1 : 0));
        dest.writeInt(UserId);
        dest.writeString(ObjectId);
        dest.writeInt(AccountType);
    }
    //====================================================================
    //====================================================================
}
