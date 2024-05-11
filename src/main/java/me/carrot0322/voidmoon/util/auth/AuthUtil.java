package me.carrot0322.voidmoon.util.auth;

import me.carrot0322.voidmoon.VoidMoon;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import static me.carrot0322.voidmoon.util.client.Util.mc;

public class AuthUtil {
    public static boolean auth() {
        String hwid = getHwid();
        try {
            URL url = new URL("https://gitlab.com/carrot0322/vm/-/raw/main/hwid.txt");
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(hwid)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void sendWebhook() throws IOException {
        WebhookUtil webhookUtil = new WebhookUtil("https://discord.com/api/webhooks/1238733763916075050/q90Il_DHxK5GDR6qmmcnaqYul2fUcZTWcCGJFSaeYCVThZS5LsQbgR_pM54X_MVvL2mw");
        WebhookUtil.EmbedObject embed = new WebhookUtil.EmbedObject();
        embed.setTitle("Name: " + mc.getSession().getUsername() + " - Version: " + VoidMoon.MOD_VERSION);
        embed.setThumbnail("https://mc-heads.net/avatar/" + mc.getSession().getUsername() + "/512.png");
        embed.setDescription("HWID: " + getHwid());

        if(auth()){
            embed.setColor(Color.GREEN);
        } else {
            embed.setColor(Color.RED);
        }

        embed.setFooter(getTime(), null);
        webhookUtil.addEmbed(embed);

        webhookUtil.execute();
    }

    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd / HH:mm:ss");
        Date date = new Date();
        return (formatter.format(date));
    }

    public static String getHwid() {
        String hwid = System.getenv("os")
                + System.getenv("os.arch")
                + System.getenv("os.name")
                + System.getenv("SystemRoot")
                + System.getenv("NUMBER_OF_PROCESSORS");

        String encodedHWID = encodeHWID(hwid);
        return encodedHWID;
    }

    public static String encodeHWID(String hwid) {
        StringBuffer sb = new StringBuffer();
        sb.append(hwid);

        return DigestUtils.sha512Hex(DigestUtils.sha512Hex(
                        mixString(sb
                                .insert(sb.length() - sb.length() / 2, "Ubuntu-")
                                .insert(sb.length() - sb.length() / 3, "Windows-")
                                .insert(sb.length() - sb.length() / 4, "MacOS")
                                .toString()
                        )
                )
        );
    }

    public static String mixString(String str){
        return str
                .replace("a", "r")
                .replace("A", "R")
                .replace("b", "e")
                .replace("B", "E")
                .replace("w", "q")
                .replace("W", "Q")
                .replace("o", "e")
                .replace("O", "E")
                .replace("s", "u")
                .replace("S", "U");
    }
}
