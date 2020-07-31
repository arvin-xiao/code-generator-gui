package cn.coderoom.generator.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class ObjectUtil {

    public static final char UNDERLINE_CHAR = '_';

    /**
     * 下划线转驼峰
     * @param underlineStr
     * @return
     */
    public static String underline2Camel(String underlineStr) {

        if (StringUtils.isEmpty(underlineStr)) {
            return StringUtils.EMPTY;
        }

        int len = underlineStr.length();
        StringBuilder strb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {

            char c = underlineStr.charAt(i);
            if (c == UNDERLINE_CHAR && (++i) < len) {

                c = underlineStr.charAt(i);
                strb.append(Character.toUpperCase(c));
            } else {

                strb.append(c);
            }
        }
        return strb.toString();
    }

    /**
     * 驼峰转下划线
     * @param camelStr
     * @return
     */
    public static String camel2Underline(String camelStr) {

        if (StringUtils.isEmpty(camelStr)) {

            return StringUtils.EMPTY;
        }

        int len = camelStr.length();
        StringBuilder strb = new StringBuilder(len + len >> 1);
        for (int i = 0; i < len; i++) {

            char c = camelStr.charAt(i);
            if (Character.isUpperCase(c)) {

                strb.append(UNDERLINE_CHAR);
                strb.append(Character.toLowerCase(c));
            } else {

                strb.append(c);
            }
        }
        return strb.toString();
    }

    /**
     * 带下划线的key的Map转成驼峰形式的实体对象
     * @param map map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(camel2Underline(field.getName()))); // 获取带_下划线的名称的value值
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
