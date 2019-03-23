package com.xxm.toolinterface.complete;

/**
 * Created by xxm on 2019/3/22 0022
 */
public abstract class FuncWithParamAndResult<Result, Param> extends FunctionName {

    public FuncWithParamAndResult(String funcName) {
        super(funcName);
    }

    public abstract Result onWithParamAndResult(Param param);
}
