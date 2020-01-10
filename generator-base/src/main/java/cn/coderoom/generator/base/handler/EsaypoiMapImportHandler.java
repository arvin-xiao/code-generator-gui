package cn.coderoom.generator.base.handler;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import cn.afterturn.easypoi.util.PoiPublicUtil;

import java.util.Map;

/** 
 * 
 * @class EsaypoiMapImportHandler
 * @package cn.coderoom.generator.base.handler
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2020/1/10 11:23 
*/ 
public class EsaypoiMapImportHandler extends ExcelDataHandlerDefaultImpl<Map<String, Object>> {

    @Override
    public void setMapValue(Map<String, Object> map, String originKey, Object value) {
        if (value instanceof Double) {
            map.put(getRealKey(originKey), PoiPublicUtil.doubleToString((Double) value));
        } else {
            map.put(getRealKey(originKey), value != null ? value.toString() : null);
        }
    }

    private String getRealKey(String originKey) {
        if (originKey.equals("姓名")) {
            return "name";
        }
        return originKey;
    }


}
