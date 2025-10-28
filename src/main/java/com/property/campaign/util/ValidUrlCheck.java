package com.property.campaign.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ValidUrlCheck {

    public static boolean isValidUrl(String imageUrl) {
        try {
            // Check if URL is valid
            URL url = new URL(imageUrl);

            // Check if URL is an image
            BufferedImage image = ImageIO.read(url);

            if (image == null) {
                return false;
            }

            return true;
        } catch (MalformedURLException e) {
            // URL is not valid
            return false;
        } catch (IOException e) {
            // Error while reading image
            return false;
        }
    }
}
