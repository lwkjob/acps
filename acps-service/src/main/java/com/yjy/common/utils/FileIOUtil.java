package com.yjy.common.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by yuanzc on 2015/4/22.
 */
public class FileIOUtil {
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String OS_ENCODING = Charset.defaultCharset()
            .toString();

    // System.out.println(System.getProperty("file.encoding"));
    public static InputStream getFileInputStream(String filename) {
        if (StringUtilsAcps.isEmptyString(filename))
            return null;
        InputStream input = null;
        try {
            if (filename.startsWith("classpath:")) {
                ClassLoader cl = FileIOUtil.class.getClassLoader();
                input = cl.getResourceAsStream(filename.substring(10));
            } else {
                File file = new File(filename);
                if (file.exists()) {
                    input = new FileInputStream(filename);
                }
            }
            if (input == null && PathResolver.isRelative(filename)) {
                ClassLoader cl = FileIOUtil.class.getClassLoader();
                input = cl.getResourceAsStream(filename);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return input;

    }

    public static URL getFile(String filename) {
        if (StringUtilsAcps.isEmptyString(filename))
            return null;
        URL url = null;
        try {
            if (filename.startsWith("classpath:")) {
                ClassLoader cl = FileIOUtil.class.getClassLoader();
                url = cl.getResource(filename.substring(10));
            } else {
                File file = new File(filename);
                if (file.exists()) {
                    url = file.toURI().toURL();
                }
            }
            if (url == null && PathResolver.isRelative(filename)) {
                ClassLoader cl = FileIOUtil.class.getClassLoader();
                url = cl.getResource(filename);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    public static String readFile(String fileName, String encoding) {
        return readFile(new File(fileName), encoding);
    }

    public static String readString(String fileName) {
        BufferedReader fr = null;
        try {
            fr = getBufferedReader(getFileInputStream(fileName), DEFAULT_ENCODING);
            if (fr == null)
                return null;

            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = fr.readLine()) != null) {
                buf.append(line);
            }
            return buf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static BufferedReader getBufferedReader(InputStream ins,
                                                   String encode) {
        try {
            return new BufferedReader(new InputStreamReader(ins, encode));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文件名，读取文件
     *
     * @return
     * @throws IOException
     */
    public static String readFile(File file, String encoding) {
        BufferedReader fr = null;
        try {
            fr = getBufferedReader(file, encoding);
            if (fr == null)
                return null;

            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = fr.readLine()) != null) {
                buf.append(line);
            }
            return buf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static BufferedReader getBufferedReader(File file, String encode) {
        try {
            return new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), encode));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
