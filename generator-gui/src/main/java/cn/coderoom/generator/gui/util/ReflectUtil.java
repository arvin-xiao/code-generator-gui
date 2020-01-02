package cn.coderoom.generator.gui.util;

import cn.coderoom.generator.base.utils.StringUtils;
import com.google.common.collect.Maps;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

/**
 * 
 * @class ReflectUtil
 * @package cn.coderoom.mybatis.generator.util
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/16 10:49 
*/ 
public class ReflectUtil {

    static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    public static Object getTarget(Object dest, Map<String, Object> addProperties) {
        // get property map
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(dest);
        Map<String, Class> propertyMap = Maps.newHashMap();
        for (PropertyDescriptor d : descriptors) {
            if (!"class".equalsIgnoreCase(d.getName())) {
                propertyMap.put(d.getName(), d.getPropertyType());
            }
        }
        // add extra properties
        addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));
        // new dynamic bean
        DynamicBean dynamicBean = new DynamicBean(dest.getClass(), propertyMap);
        // add old value
        propertyMap.forEach((k, v) -> {
            try {
                // filter extra properties
                if (!addProperties.containsKey(k)) {
                    dynamicBean.setValue(k, propertyUtilsBean.getNestedProperty(dest, k));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        // add extra value
        addProperties.forEach((k, v) -> {
            try {
                dynamicBean.setValue(k, v);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        Object target = dynamicBean.getTarget();
        return target;
    }

    /** 
     * 动态属性对象转jsonString
     * @param Objects	
     * @author lim
     * @date 2019/12/16 14:02 
     * @return java.lang.String
    */ 
    public static String toJsonString(List<Object> Objects){

        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (Object o:
        Objects) {
            sb.append("{");
            Class cls = o.getClass();
            //将参数类转换为对应属性数量的Field类型数组（即该类有多少个属性字段 N 转换后的数组长度即为 N）
            java.lang.reflect.Field[] fields = cls.getDeclaredFields();
            for(int i = 0;i < fields.length; i ++){
                java.lang.reflect.Field f = fields[i];
                f.setAccessible(true);
                try {
                    //f.getName()得到对应字段的属性名，f.get(o)得到对应字段属性值,f.getGenericType()得到对应字段的类型
                    System.out.println("属性名："+f.getName().replace("$cglib_prop_","")+"；属性值："+f.get(o)+";字段类型：" + f.getGenericType());
                    sb.append("\"");
                    sb.append(StringUtils.upperFirstLatter(f.getName().replace("$cglib_prop_","")));
                    sb.append("\":\"");
                    sb.append(f.get(o));
                    sb.append("\",");
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    logger.error("动态属性对象转jsonString异常"+e.getMessage());
                    e.printStackTrace();
                }
            }
            sb.append("},");
        }
        sb.append("]");

        return sb.toString();
    }


    public static class DynamicBean {
        /**
         * 目标对象
         */
        private Object target;

        /**
         * 属性集合
         */
        private BeanMap beanMap;

        public DynamicBean(Class superclass, Map<String, Class> propertyMap) {
            this.target = generateBean(superclass, propertyMap);
            this.beanMap = BeanMap.create(this.target);
        }


        /** 
         * bean 添加属性和值
         * @param property
         * @param value	
         * @author lim
         * @date 2019/12/16 10:50 
         * @return void
        */ 
        public void setValue(String property, Object value) {
            beanMap.put(property, value);
        }

        /**
         * 获取属性值
         *
         * @param property
         * @return
         */
        public Object getValue(String property) {
            return beanMap.get(property);
        }

        /**
         * 获取对象
         * @param
         * @author lim
         * @date 2019/12/16 10:50
         * @return java.lang.Object
        */
        public Object getTarget() {
            return this.target;
        }


        /** 
         * 根据属性生成对象
         * @param superclass	
         * @param propertyMap	
         * @author lim
         * @date 2019/12/16 10:50 
         * @return java.lang.Object
        */ 
        private Object generateBean(Class superclass, Map<String, Class> propertyMap) {
            BeanGenerator generator = new BeanGenerator();
            if (null != superclass) {
                generator.setSuperclass(superclass);
            }
            BeanGenerator.addProperties(generator, propertyMap);
            return generator.create();
        }
    }
    
}
