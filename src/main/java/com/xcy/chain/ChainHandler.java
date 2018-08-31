package com.xcy.chain;

/**
 * Created by cat on 2017-02-28.
 */
public abstract class ChainHandler {

    public void execute(Chain chain){
        work();
        chain.proceed();
    }

    protected abstract void work();
}
