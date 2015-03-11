package com.rubyhuntersky.gx.basic;

/**
 * @author wehjin
 * @since 1/27/15.
 */

public interface Monitor<ResultType> {
    void onResult(ResultType result);
    void onError(Throwable throwable);

    public static Monitor<?> EMPTY = new Monitor<Object>() {
        @Override
        public void onResult(Object result) {
            // Do nothing.
        }

        @Override
        public void onError(Throwable throwable) {
            // Do nothing.
        }
    };
}
