package com.waben.stock.applayer.operation.warpper.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Created by yuyidi on 2018/3/3.
 * @desc
 */
public class MailAuthenricator extends Authenticator {

    private String username;
    private String password;

    public MailAuthenricator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username,password);
    }
}
