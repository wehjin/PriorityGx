package com.rubyhuntersky.gx.basic;

import com.rubyhuntersky.gx.support.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wehjin
 * @since 3/8/15.
 */

public class AdaptTest extends BaseTest {

    public void testAdaptModifiesOriginalSettingsAndResult() throws Exception {
        assertProducerDeliveryEqualsSupplied("Hello", new Producer<String>() {
            @Override
            public void produce(final String supplied, final Monitor<String> delivered) {
                Interactives.<List<String>>echo().adapt(new Adapter<List<String>, String>() {
                    @Override
                    public List<String> getSettings(String outerSettings) {
                        final ArrayList<String> innerSettings = new ArrayList<>(2);
                        innerSettings.add(outerSettings);
                        innerSettings.add("Goodbye");
                        return innerSettings;
                    }

                    @Override
                    public String getResult(List<String> innerResult) {
                        return innerResult.get(0);
                    }
                }).present(null, null, supplied, delivered);
            }
        });
    }

}
