package com.xxm.toolinterface.complete;

/**
 * Created by xxm on 2019/3/22 0022
 */
public abstract class FuncOnlyResult<Result> extends FunctionName {

    public FuncOnlyResult(String funcName) {
        super(funcName);
    }

    public abstract Result onOnlyResult();
}
