package cn.coderoom.generator.base.utils;

/** 
 * 
 * @class StringUtils
 * @package cn.coderoom.mybatis.generator.util
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/16 14:44 
*/ 
public class StringUtils {

    /**
     * convert string from slash style to camel style, such as my_course will convert to MyCourse
     * @param str
     * @author lim
     * @date 2019/12/18 14:20
     * @return java.lang.String
    */
    public static String dbStringToCamelStyle(String str) {
        if (str != null) {
            if (str.contains("_")) {
                str = str.toLowerCase();
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(str.charAt(0)).toUpperCase());
                for (int i = 1; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c != '_') {
                        sb.append(c);
                    } else {
                        if (i + 1 < str.length()) {
                            sb.append(String.valueOf(str.charAt(i + 1)).toUpperCase());
                            i++;
                        }
                    }
                }
                return sb.toString();
            } else {
                String firstChar = String.valueOf(str.charAt(0)).toUpperCase();
                String otherChars = str.substring(1);
                return firstChar + otherChars;
            }
        }
        return null;
    }

    public static String toCamelStyle(String str) {
        if (str != null) {
            if (str.contains("_")) {
                str = str.toLowerCase();
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(str.charAt(0)).toLowerCase());
                for (int i = 1; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c != '_') {
                        sb.append(c);
                    } else {
                        if (i + 1 < str.length()) {
                            sb.append(String.valueOf(str.charAt(i + 1)).toUpperCase());
                            i++;
                        }
                    }
                }
                return sb.toString();
            } else {
                String firstChar = String.valueOf(str.charAt(0)).toUpperCase();
                String otherChars = str.substring(1);
                return firstChar + otherChars;
            }
        }
        return null;
    }

    /** 
     * 首字母大写
     * @param letter
     * @author lim
     * @date 2019/12/16 14:45 
     * @return java.lang.String
    */ 
    public static String upperFirstLatter(String letter){
        return letter.substring(0, 1).toUpperCase()+letter.substring(1);
    }

    /** 
     *  首字母小写
     * @param letter	
     * @author lim
     * @date 2019/12/16 17:48 
     * @return java.lang.String
    */ 
    public static String lowerFirstLatter(String letter){
        return letter.substring(0, 1).toLowerCase()+letter.substring(1);
    }
}
