package com.example.celog.common.s3;

import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

public class FileUtil {

    // 파일 이름이 기준 length 보다 길경우 잘라서 리턴 메서드
    public static String cutFileName (String fileName, int length) {
        if(fileName.length() <= length) {
            return fileName;
        }

        String extension = fileName.substring(fileName.lastIndexOf("."));
        return fileName.substring(0, length - extension.length()) + extension;
    }

    // 랜덤 파일 이름 리턴 메서드
    public static String getRandomFileName(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return RandomStringUtils.randomAlphabetic(5)+ "_" + RandomStringUtils.randomNumeric(5) + "_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + extension;
    }

    public static String getFileNameByBrowser(String fileName, HttpServletRequest request)
            throws UnsupportedEncodingException {
        String browser= "";
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if(headerName.equals("user-agent")) {
                browser = request.getHeader(headerName);
            }
        }

        String docName = "";
        if (browser.contains("Trident") || browser.contains("MSIE") || browser.contains("Edge")) {
            docName = FileUtil.mappingSpecialCharacter(URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        } else if (browser.contains("Firefox")) {
            docName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

        } else if (browser.contains("Opera")) {
            docName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

        } else if (browser.contains("Chrome")) {
            docName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

        } else if (browser.contains("Safari")) {
            docName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

        } else {
            //throw new RuntimeException("미지원 브라우저");
        }
        return docName;
    }

    public static String mappingSpecialCharacter(String name) {

        // 파일명에 사용되는 특수문자
        char[] sh_list = { '~', '!', '@', '#', '$', '%', '&', '(', ')', '=', ';', '[', ']', '{', '}', '^', '-' };
        for (char sh : sh_list) {
            String encodeStr = URLEncoder.encode(sh + "", StandardCharsets.UTF_8);
            name = name.replaceAll(encodeStr, "\\" + sh);
        }

        // 띄워쓰기 -> + 치환
        name = name.replaceAll("%2B", "+");
        // 콤마 -> _ 치환
        name = name.replaceAll("%2C", "_");

        return name;
    }
}