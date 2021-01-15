package cn.coderoom.generator.base.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsEngineUtilsTest {

    @Test
    public void callJSFunction() {

        try{

            JsEngineUtils.callJSFunction();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}