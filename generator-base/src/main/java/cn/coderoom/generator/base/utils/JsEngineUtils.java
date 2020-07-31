package cn.coderoom.generator.base.utils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsEngineUtils {

    //调用js函数
    public static void callJSFunction() throws Exception{
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        Map<String,Object> data = new HashMap<>();
        data.put("a",1);
        data.put("b",1);
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(data);
        String str = "function func (data) {return data[0].a+data[0].b; }";
        //执行js脚本定义函数
        engine.eval(str);
        Invocable invocable = (Invocable) engine;
        Double res = (Double)invocable.invokeFunction("func",list);
        System.out.println(res.intValue());

    }

}
