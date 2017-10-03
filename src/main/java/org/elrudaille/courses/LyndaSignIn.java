package org.elrudaille.courses;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.elrudaille.courses.util.ConfigUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;


/**
 * Created by rgachet on 24/02/2017.
 */
public class LyndaSignIn {

    private static Logger logger = Logger.getLogger(LyndaSignIn.class.getName());

    private final String lyndaRoot = "https://www.lynda.com/";

    public void signIn() throws UnirestException, IOException {
        BasicCookieStore cookieStore = new org.apache.http.impl.client.BasicCookieStore();
        CloseableHttpClient client =
                org.apache.http.impl.client.HttpClients.custom().setDefaultCookieStore(cookieStore).build(); //.setProxy(new HttpHost("localhost", 8888))
        Unirest.setHttpClient(client);

        String user= ConfigUtil.getValueFromLyndaProperty("user");
        String password = ConfigUtil.getValueFromLyndaProperty("password");
        // connexion
        logger.info("Connexion à Lynda");

        Document signin = Jsoup.connect(lyndaRoot + "signin").get();
        String hidden = signin.getElementsByAttributeValue("name", "-_-").iterator().next().attr("value");

        HttpResponse<String> reponse =
                Unirest.post(lyndaRoot + "signin/password")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Referer", lyndaRoot + "signin")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .field("-_-", hidden).field("email", user).asString();
        reponse =
                Unirest.post(lyndaRoot + "signin/user")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Referer", lyndaRoot + "signin")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .field("-_-", hidden).field("email", user).field("password", password).asString();
        logger.info("connected");
    }

    public void signOut() {
        try {
            logger.info("Déconnexion à Lynda");
            Unirest.get(lyndaRoot + "/logout").asString();
        } catch (UnirestException e) {
            try {
                Unirest.shutdown();
            } catch (IOException e1) {
                logger.error(e1.getMessage());
            }
            logger.error(e.getMessage());
        }
        logger.info("Déconnecté");
    }
}
