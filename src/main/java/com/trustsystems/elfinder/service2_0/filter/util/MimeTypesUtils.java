package com.trustsystems.elfinder.service2_0.filter.util;

import org.springframework.core.io.ClassPathResource;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MimeTypesUtils {
    private  static Map<String,String> map;

    public  static final  String UNKNOWN_MIME_TYPE = "application/oct-stream";

    static {
        map = new HashMap<String, String>();
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static  String getMimeType(String ext){
        return map.get(ext.toLowerCase());
    }

    public static  boolean isUnknownType(String mime){
        return mime == null || UNKNOWN_MIME_TYPE.equals(mime);
    }
    private static void load() throws IOException {
        InputStream is = new ClassPathResource("/mime.types").getInputStream();
        BufferedReader fr = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line=fr.readLine())!=null){
            line = line.trim();
            if (line.startsWith("#") || line.isEmpty()){
                continue;
            }

            String[] tokens = line.split("\\s+");

            if (tokens.length<2){
                continue;
            }

            for (int i = 0; i < tokens.length; i++) {
                putMimeType(tokens[i],tokens[0]);
            }
        }
        is.close();
    }

    private static void putMimeType(String ext, String type) {

        if (ext==null || type == null){
            return;
        }
        map.put(ext.toLowerCase(),type);
    }

}
