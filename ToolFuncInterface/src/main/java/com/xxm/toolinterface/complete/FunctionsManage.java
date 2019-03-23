package com.xxm.toolinterface.complete;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xxm on 2019/3/22 0022
 * 四种方法公共接口
 */
public class FunctionsManage {

    private final String TAG = FunctionsManage.class.getSimpleName();

    private static FunctionsManage functionsManage;

    private Map<String, FuncNoParamNoResult> noParamNoResultMap;
    private Map<String, FuncOnlyParam> onlyParamMap;
    private Map<String, FuncOnlyResult> onlyResultMap;
    private Map<String, FuncWithParamAndResult> paramAndResultMap;

    private FunctionsManage() {
        noParamNoResultMap = new HashMap<>();
        onlyParamMap = new HashMap<>();
        onlyResultMap = new HashMap<>();
        paramAndResultMap = new HashMap<>();
    }


    public static FunctionsManage getInstance() {
        if (functionsManage == null) {
            functionsManage = new FunctionsManage();
        }
        return functionsManage;
    }

    public void addFunctionName(FuncNoParamNoResult noParamNoResult) {
        if (noParamNoResult == null) return;
        noParamNoResultMap.put(noParamNoResult.funcName, noParamNoResult);
    }

    public void addFunctionName(FuncOnlyParam onlyParam) {
        if (onlyParam == null) return;
        onlyParamMap.put(onlyParam.funcName, onlyParam);
    }

    public void addFunctionName(FuncOnlyResult onlyResult) {
        if (onlyResult == null) return;
        onlyResultMap.put(onlyResult.funcName, onlyResult);
    }

    public void addFunctionName(FuncWithParamAndResult paramAndResult) {
        if (paramAndResult == null) return;
        paramAndResultMap.put(paramAndResult.funcName, paramAndResult);
    }

    /**
     * 没有参数和返回值
     *
     * @param funcName 唯一的方法名
     */
    public void invokeFunc(String funcName) {
        if (TextUtils.isEmpty(funcName)) return;
        FuncNoParamNoResult funcNoParamNoResult = noParamNoResultMap.get(funcName);
        if (funcNoParamNoResult != null) {
            funcNoParamNoResult.onWithParamNoResult();
        } else {
            Log.e(TAG, "no find funcName:" + funcName);
        }
    }

    /**
     * 有参数但是没有返回值
     *
     * @param funcName 唯一的方法名
     * @param param    参数，泛型
     */
    public <Param> void invokeFunc(String funcName, Param param) {
        if (TextUtils.isEmpty(funcName) || param == null) return;
        FuncOnlyParam funcOnlyParam = onlyParamMap.get(funcName);
        if (funcOnlyParam != null) {
            funcOnlyParam.onOnlyParam(param);
        } else {
            Log.e(TAG, "no find funcName:" + funcName);
        }
    }

    /**
     * 没有参数但是有返回值
     *
     * @param funcName    唯一的方法名
     * @param resultClass 返回值，泛型
     */
    public <Result> Result invokeFunc(String funcName, Class<Result> resultClass) {
        if (TextUtils.isEmpty(funcName)) return null;
        FuncOnlyResult funcOnlyResult = onlyResultMap.get(funcName);
        if (funcOnlyResult != null) {
            if (resultClass != null) {
                return resultClass.cast(funcOnlyResult.onOnlyResult());
            } else {
                return (Result) funcOnlyResult.onOnlyResult();
            }
        } else {
            Log.e(TAG, "no find funcName:" + funcName);
        }
        return null;
    }

    /**
     * 有参数和返回值
     *
     * @param funcName    唯一的方法名
     * @param param       参数，泛型
     * @param resultClass 返回值，泛型
     */
    public <Result, Param> Result invokeFunc(String funcName, Param param, Class<Result> resultClass) {
        if (TextUtils.isEmpty(funcName) || param == null) return null;
        FuncWithParamAndResult funcWithParamAndResult = paramAndResultMap.get(funcName);
        if (funcWithParamAndResult != null) {
            if (resultClass != null) {
                return resultClass.cast(funcWithParamAndResult.onWithParamAndResult(param));
            } else {
                return (Result) funcWithParamAndResult.onWithParamAndResult(param);
            }
        } else {
            Log.e(TAG, "no find funcName:" + funcName);
        }
        return null;
    }

}
