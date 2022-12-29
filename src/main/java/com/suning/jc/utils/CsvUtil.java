package com.suning.jc.utils;

import org.apache.commons.collections4.MapUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 13120094
 */
public class CsvUtil {
    public static void genCsv(String fileName,String titles, String[] mapKeys, List<HashMap<String,Object>> content, HttpServletResponse response){
        PrintWriter out=null;
        try {
           String utf = "UTF-8";
           response.setContentType("application/csv");
           response.setCharacterEncoding(utf);
           response.setHeader("Pragma", "public");
           response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, utf));
           response.setHeader("Cache-Control", "max-age=30");
           out = response.getWriter();
           out.println(titles);
           for (Map<String, Object> row : content) {
               String line = "";
               for (String col : mapKeys) {
                   line += CSVFormat(MapUtils.getString(row, col, "")) + ",";
               }
               out.println(line);
           }
           out.flush();
       }catch (IOException e){
           e.printStackTrace();
       }finally {
            out.close();
       }
    }

    public static String CSVFormat(String input) {
        boolean bFound = false;
        //如果值中含有逗号、换行符、制表符（Tab）、单引号，双引号，则需要用双引号括起来；
        //如果值中包含双引号，则需要用两个双引号来替换。
        //正则匹配：",'\"\r\n\t"
        bFound = input.matches("(.*)(,|'|\"|\r|\n|\t)(.*)");
        if (bFound) {
            //如果存在匹配字符
            //先将双引号替换为两个双引号
            String sTemp = input.replaceAll("\"", "\"\"");
            //然后，两端使用"字符
            sTemp ="\"" + sTemp + "\"";
            return sTemp;
        }
        return input;
    }
}
