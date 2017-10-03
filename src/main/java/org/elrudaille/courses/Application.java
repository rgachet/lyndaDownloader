package org.elrudaille.courses;

import org.rythmengine.Rythm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

/**
 * Created by rgachet on 27/02/2017.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("home.template", "web");
        Rythm.init(map);
        SpringApplication.run(Application.class, args);
    }
}
