package com.mte.mteframework.security;

import android.os.Build;

import com.mte.mteframework.Debug.MTEDebugLogger;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESEncrypter
{

    //#####################################################################################################
    //#####################################################################################################
    public AESEncrypter()
    {

    }



    //#####################################################################################################
    //#####################################################################################################
    public static String Encrypt(String inputData, String passPhrase, String applicationCypherKey)
    {
        try
        {
            String encryptedPassPhrase ="";
            String encryptionKey = "";
            String encryptedData ="";
            //============================================================================================
            //============================================================================================
            if(applicationCypherKey.length()>=32)
            {
                //============================================================================================
                //============================================================================================
                //Check Encrypted PassPhrase
                encryptedPassPhrase = AESEncrypt(passPhrase, applicationCypherKey.substring(0,32));

                if(encryptedPassPhrase.length()>=32)
                {
                    //Now lets get the encryption Key
                    encryptionKey = encryptedPassPhrase.substring(0,32);

                    //===================================================================================
                    //Lets encrypt the data
                    encryptedData = AESEncrypt(inputData, encryptionKey);

                    return encryptedData;

                }
                else
                {
                    //passphrase not long enough
                    return "Error. Passphrase must be 16 bytes or longer";

                }

                //============================================================================================
                //============================================================================================
            }
            else
            {
                return "Error. Cypher must be 32 bytes long.";
            }
            //============================================================================================
            //============================================================================================

        }
        catch (Exception ex)
        {
            return "None. EXC." + ex.toString();
        }
    }
    //#####################################################################################################
    //#####################################################################################################
    public static String Decrypt(String encryptedData, String passPhrase, String applicationCypherKey)
    {
        try
        {
            String encryptedPassPhrase ="";
            String encryptionKey = "";
            String decryptedData ="";
            //============================================================================================
            //============================================================================================
            if(applicationCypherKey.length()>=32)
            {
                //============================================================================================
                //============================================================================================
                //Check Encrypted PassPhrase
                encryptedPassPhrase = AESEncrypt(passPhrase, applicationCypherKey.substring(0,32));

                if(encryptedPassPhrase.length()>=32)
                {
                    //Now lets get the encryption Key
                    encryptionKey = encryptedPassPhrase.substring(0,32);

                    //===================================================================================
                    //Lets decrypt the data
                    decryptedData = AESDecrypt(encryptedData, encryptionKey);

                    return decryptedData;

                }
                else
                {
                    //passphrase not long enough
                    return "Error. Passphrase must be 16 bytes or longer";

                }

                //============================================================================================
                //============================================================================================
            }
            else
            {
                return "Error. Cypher must be 32 bytes long.";
            }
            //============================================================================================
            //============================================================================================

        }
        catch (Exception ex)
        {
            return "None. EXC." + ex.toString();
        }
    }
    //#####################################################################################################
    //#####################################################################################################
    public static String AESEncrypt(String inputdata,String encryptionKey)
    {
        try
        {
            SecretKey secretKey = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // Using ECB mode for simplicity (not recommended for most use cases)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(inputdata.getBytes("UTF-8"));
            //===========================================================
            //Version check
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                return Base64.getEncoder().encodeToString(encryptedBytes);
            }
            else
            {
                //MTEDebugLogger.Log(APPTAG, "This Version of Android is not supported. API Level 26 required.");
                return "Error: (" + inputdata +") This Version of Android is not supported. API Level 26 required.";
            }
        }
        catch (Exception ex)
        {
            return "None. EXC." + ex.toString();
        }
    }

    //#########################################################################################################
    //#########################################################################################################

    public static String AESDecrypt(String encryptedText, String key)
    {
        try
        {
            SecretKey secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // Using ECB mode for simplicity (not recommended for most use cases)
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = new byte[0];
            //===========================================================
            //Version check
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                encryptedBytes = Base64.getDecoder().decode(encryptedText);
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
                return new String(decryptedBytes, "UTF-8");
            }
            else
            {
                //MTEDebugLogger.Log(APPTAG, "This Version of Android is not supported. API Level 26 required.");
                return "This Version of Android is not supported. API Level 26 required.";
            }

        }
        catch (Exception ex)
        {
            return "Exception. "+ ex.toString();
        }
    }

    //#####################################################################################################
    //#####################################################################################################


}
