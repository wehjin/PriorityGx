package com.rubyhuntersky.gx.basic;

import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;

/**
 * @author wehjin
 * @since 3/8/15.
 */

public class BindTest extends BaseTest {

    public void testBindSuppliesSettingsToOriginal() throws Exception {
        assertProducerDeliveryEqualsSupplied("Hello", new Producer<String>() {
            @Override
            public void produce(final String supplied, final Monitor<String> delivered) {
                final Interactive<String> extractSettings = Interactives.create(new OnPresent<String>() {
                    @Override
                    public void call(Presenter<String> presenter) {
                        delivered.onResult(presenter.getSettings());
                    }
                });
                extractSettings.bind(supplied).present(null, null, null, null);
            }
        });
    }

}
