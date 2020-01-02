package cn.coderoom.generator.gui.util;

import sun.reflect.ConstructorAccessor;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @class DynamicEnumUtils
 * @package cn.coderoom.mybatis.generator.util
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/16 10:15 
*/ 
public class DynamicEnumUtils {

    private static ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();

    private static void setFailsafeFieldValue(Field field, Object target, Object value) throws NoSuchFieldException,
            IllegalAccessException {

        // 反射访问私有变量
        field.setAccessible(true);

        /**
         * 将字段实例中的修饰符更改为：非final，
         * 利用反射允许我们修改静态final字段。
         */
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        int modifiers = modifiersField.getInt(field);

        // 去掉修饰符int中的最后一位
        modifiers &= ~Modifier.FINAL;
        modifiersField.setInt(field, modifiers);

        FieldAccessor fa = reflectionFactory.newFieldAccessor(field, false);
        fa.set(target, value);
    }

    private static void blankField(Class<?> enumClass, String fieldName) throws NoSuchFieldException,
            IllegalAccessException {
        for (Field field : Class.class.getDeclaredFields()) {
            if (field.getName().contains(fieldName)) {
                AccessibleObject.setAccessible(new Field[] { field }, true);
                setFailsafeFieldValue(field, enumClass, null);
                break;
            }
        }
    }

    /** 
     * 清空枚举的缓存
     * @param enumClass	
     * @author lim
     * @date 2019/12/16 10:21 
     * @return void
    */ 
    private static void cleanEnumCache(Class<?> enumClass) throws NoSuchFieldException, IllegalAccessException {
        blankField(enumClass, "enumConstantDirectory"); // Sun (Oracle?!?) JDK 1.5/6
        blankField(enumClass, "enumConstants"); // IBM JDK
    }

    private static ConstructorAccessor getConstructorAccessor(Class<?> enumClass, Class<?>[] additionalParameterTypes)
            throws NoSuchMethodException {
        Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
        parameterTypes[0] = String.class;
        parameterTypes[1] = int.class;
        System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2, additionalParameterTypes.length);
        return reflectionFactory.newConstructorAccessor(enumClass.getDeclaredConstructor(parameterTypes));
    }

    /** 
     * 创建新的枚举项
     * @param enumClass	
     * @param value	
     * @param ordinal	
     * @param additionalTypes	
     * @param additionalValues	
     * @author lim
     * @date 2019/12/16 10:21 
     * @return java.lang.Object
    */ 
    private static Object makeEnum(Class<?> enumClass, String value, int ordinal, Class<?>[] additionalTypes,
                                   Object[] additionalValues) throws Exception {
        Object[] parms = new Object[additionalValues.length + 2];
        parms[0] = value;
        parms[1] = Integer.valueOf(ordinal);
        System.arraycopy(additionalValues, 0, parms, 2, additionalValues.length);
        return enumClass.cast(getConstructorAccessor(enumClass, additionalTypes).newInstance(parms));
    }

    /** 
     * 将枚举实例添加到作为参数提供的枚举类中
     * @param enumType	
     * @param enumName	
     * @param additionalTypes	
     * @param additionalValues	
     * @author lim
     * @date 2019/12/16 10:16 
     * @return void
    */ 
    @SuppressWarnings("unchecked")
    public static <T extends Enum<?>> void addEnum(Class<T> enumType, String enumName, Class<?>[] additionalTypes, Object[] additionalValues) {

        // 0. 检查类型
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new RuntimeException("class " + enumType + " is not an instance of Enum");
        }

        // 1. 在枚举类中查找“$values”持有者并获取以前的枚举实例
        Field valuesField = null;
        Field[] fields = enumType.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("$VALUES")) {
                valuesField = field;
                break;
            }
        }
        AccessibleObject.setAccessible(new Field[] { valuesField }, true);

        try {
            // 2. 将他拷贝到数组
            T[] previousValues = (T[]) valuesField.get(enumType);
            List<T> values = new ArrayList<T>(Arrays.asList(previousValues));

            // 3. 创建新的枚举项
            T newValue = (T) makeEnum(enumType, enumName, values.size(), additionalTypes, additionalValues);

            // 4. 添加新的枚举项
            values.add(newValue);

            // 5. 设定拷贝的数组，到枚举类型
            setFailsafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumType, 0)));

            // 6. 清空枚举的缓存
            cleanEnumCache(enumType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
