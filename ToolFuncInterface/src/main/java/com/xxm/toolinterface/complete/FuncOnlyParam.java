package com.xxm.toolinterface.complete;

/**
 * Created by xxm on 2019/3/22 0022
 */
public abstract class FuncOnlyParam<Param> extends FunctionName{

    public FuncOnlyParam(String funcName) {
        super(funcName);
    }

    public abstract void onOnlyParam(Param param);
}
