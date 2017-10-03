package org.elrudaille.courses.util;

import org.rythmengine.Rythm;

/**
 * Created by rgachet on 27/02/2017.
 */
public class TemplateUtil {
    public static String generateWithTemplate(String templatePath, Object... args) {
        return Rythm.render(templatePath, args);
    }

    public static String generateWithTemplate(String templatePath) {
        return Rythm.render(templatePath);
    }
}
