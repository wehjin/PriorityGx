package com.rubyhuntersky.gx.basic;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wehjin
 * @since 3/8/15.
 */

public class BaseTest extends ApplicationTestCase<Application> {
    public BaseTest() {
        super(Application.class);
    }

    public <T> void assertProducerDeliveryEqualsSupplied(T expected, Producer<T> producer) {
        final List<T> actual = new ArrayList<>();
        final List<Throwable> error = new ArrayList<>();
        producer.produce(expected, new Monitor<T>() {
            @Override
            public void onResult(T result) {
                actual.add(result);
            }

            @Override
            public void onError(Throwable throwable) {
                error.add(throwable);
            }
        });
        if (error.size() > 0) {
            //noinspection ThrowableResultOfMethodCallIgnored
            final Throwable throwable = error.get(0);
            fail(throwable.getLocalizedMessage());
            return;
        }
        if (actual.size() == 0) {
            fail("No actual");
        }
        assertEquals(expected, actual.get(0));
    }

    public static interface Producer<T> {
        void produce(T supplied, Monitor<T> delivered);
    }
}
