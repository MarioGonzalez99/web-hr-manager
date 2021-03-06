/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.model;


import hr.properties.ConfigProperties;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 *
 * @author Mario
 */
public class Decrypt {
    private static BasicTextEncryptor loadKey(){
        Properties encryptProps = new Properties();
        try(InputStream propertyFile = ConfigProperties.getResourceAsInputStream("encryptKey.properties")){
            encryptProps.load(propertyFile);
        } catch (IOException ex) {
            System.out.println("File not found");
        }
        String key = encryptProps.getProperty("key");
        BasicTextEncryptor decrypter = new BasicTextEncryptor();
        decrypter.setPassword(key);
        return decrypter;
    }
    public static String decryptText(String encryptedText){
        BasicTextEncryptor decrypter = loadKey();
        return decrypter.decrypt(encryptedText);
    }
    public static String encryptText(String decryptedText){
        BasicTextEncryptor encrypter = loadKey();
        return encrypter.encrypt(decryptedText);
    }
}
