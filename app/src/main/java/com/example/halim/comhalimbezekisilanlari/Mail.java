package com.example.halim.comhalimbezekisilanlari;

import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

class Mail extends javax.mail.Authenticator {
    private String _user, _pass, _from, _sport, _host,_port ,_subject, _body;
    private String[] _to;
    private boolean _auth;
    private boolean _debuggable;
    private Multipart _multipart;

    public Mail() {
        _host = "smtp.gmail.com"; // varsayılan smtp sunucusu
        _port = "465"; // varsayılan smtp port numarası
        _sport = "465"; // varsayılan socketfactory port numarası
        _user = ""; // kullanıcı adı
        _pass = ""; // parola
        _from = "halimbezek@gmail.com"; // e-posta gönderen kişinin adresi
        _subject = ""; // email konusu
        _body = ""; // email gövde kısmı
        _debuggable = false; // debug modunu açmak için -varsayılan off
        _auth = true; // smtp kimlik doğrulama - varsayılan on
        _multipart = new MimeMultipart();

    }

    public Mail(String user, String pass) {
        this();
        _user = user;
        _pass = pass;

    }

    public void setTo(String[] too) {
        _to = too;
    }

    // get ve set fonksiyonları
    public String getBody() {
        return _body;
    }

    public void setBody(String _body) {
        this._body = _body;
    }

    public String getSubject() {
        return _subject;
    }

    public void setSubject(String _subject) {
        this._subject = _subject;
    }

    public boolean send() throws Exception {
        Properties props = _setProperties();

        if (!_user.equals("") && !_pass.equals("") && _to.length > 0
                && !_from.equals("") && !_subject.equals("")
                && !_body.equals("")) {

            Session session = Session.getInstance(props, this);

            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(_from));

            InternetAddress[] addressTo = new InternetAddress[_to.length];
            for (int i = 0; i < _to.length; i++) {
                addressTo[i] = new InternetAddress(_to[i]);
            }
            msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

            msg.setSubject(_subject);
            msg.setSentDate(new Date());

            // mailin gövde kısmı
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(_body);
            _multipart.addBodyPart(messageBodyPart);

            // parçaları mesaja koyma
            msg.setContent(_multipart);

            // mail gönderme
            Transport.send(msg,addressTo);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(_user, _pass);
    }

    private Properties _setProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", _host);

        if (_debuggable) {
            props.put("mail.debug", "true");
        }
        if (_auth) {
            props.put("mail.smtp.auth", "true");
        }
        props.put("mail.smtp.port", _port);
        props.put("mail.smtp.socketFactory.port", _sport);
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;


    }

}