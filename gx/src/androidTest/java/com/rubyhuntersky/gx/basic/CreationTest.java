package com.rubyhuntersky.gx.basic;

import android.graphics.RectF;

import com.rubyhuntersky.gx.console.ActionConsole;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.functions.Action1;
import com.rubyhuntersky.gx.functions.Action3;
import com.rubyhuntersky.gx.picture.ColoredRectanglePicture;
import com.rubyhuntersky.gx.picture.Picture;
import com.rubyhuntersky.gx.responder.FunctionResponder;
import com.rubyhuntersky.gx.support.Animator;
import com.rubyhuntersky.gx.units.ZeroUnits;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wehjin
 * @since 3/7/15.
 */

public class CreationTest extends BaseTest {

    public void testCreateDeliversMonitorResult() throws Exception {

        assertProducerDeliveryEqualsSupplied("Hello", new Producer<String>() {
            @Override
            public void produce(final String supplied, final Monitor<String> delivered) {
                final Interactive<String> supplier = Interactives.create(new OnPresent<String>() {
                    @Override
                    public void call(Presenter<String> presenter) {
                        presenter.getMonitor().onResult(supplied);
                    }
                });
                supplier.present(null, null, null, delivered);
            }
        });
    }

    public void testCreateDeliversMonitorError() throws Exception {

        assertProducerDeliveryEqualsSupplied(new Throwable("Goodbye"), new Producer<Throwable>() {
            @Override
            public void produce(final Throwable supplied, final Monitor<Throwable> delivered) {
                final Interactive<String> supplier = Interactives.create(new OnPresent<String>() {
                    @Override
                    public void call(Presenter<String> presenter) {
                        presenter.getMonitor().onError(supplied);
                    }
                });
                supplier.present(null, null, null, new Monitor<String>() {

                    @Override
                    public void onResult(String result) {
                        delivered.onError(new Throwable("Unexpected result"));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        delivered.onResult(supplied);
                    }
                });
            }
        });
    }

    public void testCreateDeliversConsoleAnimator() throws Exception {

        assertProducerDeliveryEqualsSupplied(new Animator() {
            @Override
            public void onAnimationFrame() {
            }
        }, new Producer<Animator>() {
            @Override
            public void produce(final Animator supplied, final Monitor<Animator> delivered) {
                final Interactive<String> supplier = Interactives.create(new OnPresent<String>() {
                    @Override
                    public void call(Presenter<String> presenter) {
                        presenter.getConsole().onAnimator(supplied);
                    }
                });
                supplier.present(null, new ActionConsole(null, null, null, new Action1<Animator>() {
                    @Override
                    public void call(Animator responder) {
                        delivered.onResult(responder);
                    }
                }), null, null);
            }
        });
    }

    public void testCreateDeliversConsoleDialog() throws Exception {

        Map<String, Object> supplied = new HashMap<>();
        supplied.put("settings", "H11x");
        supplied.put("monitor", Monitor.EMPTY);
        supplied.put("interactive", Interactives.textLine(0x11223344));
        assertProducerDeliveryEqualsSupplied(supplied, new Producer<Map<String, Object>>() {
            @Override
            public void produce(final Map<String, Object> supplied, final Monitor<Map<String, Object>> delivered) {
                final Interactive<String> supplier = Interactives.create(new OnPresent<String>() {
                    @Override
                    public void call(Presenter<String> presenter) {
                        //noinspection unchecked
                        Interactive<String> interactive = (Interactive<String>) supplied.get("interactive");
                        String settings = (String) supplied.get("settings");
                        //noinspection unchecked
                        Monitor<String> monitor = (Monitor<String>) supplied.get("monitor");
                        presenter.getConsole().onDialog(interactive, settings, monitor);
                    }
                });
                supplier.present(null, new ActionConsole(null, null, new Action3<Interactive<?>, Object, Monitor<?>>() {
                    @Override
                    public void call(Interactive<?> interactive, Object o, Monitor<?> monitor) {
                        Map<String, Object> delivery = new HashMap<>();
                        delivery.put("interactive", interactive);
                        delivery.put("settings", o);
                        delivery.put("monitor", monitor);
                        delivered.onResult(delivery);
                    }
                }, null), null, null);
            }
        });
    }

    public void testCreateDeliversConsoleResponder() throws Exception {

        assertProducerDeliveryEqualsSupplied(new FunctionResponder(null, null), new Producer<Responder>() {
            @Override
            public void produce(final Responder supplied, final Monitor<Responder> delivered) {
                final Interactive<String> supplier = Interactives.create(new OnPresent<String>() {
                    @Override
                    public void call(Presenter<String> presenter) {
                        presenter.getConsole().onResponder(supplied);
                    }
                });
                supplier.present(null, new ActionConsole(null, new Action1<Responder>() {
                    @Override
                    public void call(Responder responder) {
                        delivered.onResult(responder);
                    }
                }, null, null), null, null);
            }
        });
    }

    public void testCreateDeliversConsolePicture() throws Exception {

        assertProducerDeliveryEqualsSupplied(new ColoredRectanglePicture(0x11223344, Units.ZERO_RECT),
              new Producer<Picture>() {
                  @Override
                  public void produce(final Picture supplied, final Monitor<Picture> delivered) {
                      final Interactive<String> supplier = Interactives.create(new OnPresent<String>() {
                          @Override
                          public void call(Presenter<String> presenter) {
                              presenter.getConsole().onPicture(supplied);
                          }
                      });
                      supplier.present(null, new ActionConsole(new Action1<Picture>() {
                          @Override
                          public void call(Picture picture) {
                              delivered.onResult(picture);
                          }
                      }, null, null, null), null, null);
                  }
              });
    }

    public void testCreateSuppliesPresenterSettings() throws Exception {

        assertProducerDeliveryEqualsSupplied("Greetings", new Producer<String>() {
            @Override
            public void produce(String supplied, final Monitor<String> delivered) {
                final Interactive<String> extract = Interactives.create(new OnPresent<String>() {
                    @Override
                    public void call(Presenter<String> presenter) {
                        delivered.onResult(presenter.getSettings());
                    }
                });
                extract.present(null, null, supplied, null);
            }
        });
    }

    public void testCreateSuppliesPresenterTapWidth() throws Exception {

        assertProducerDeliveryEqualsSupplied(17f, new Producer<Float>() {
            @Override
            public void produce(Float supplied, final Monitor<Float> delivered) {
                final Interactive<Object> extract = Interactives.create(new OnPresent<Object>() {
                    @Override
                    public void call(Presenter<Object> presenter) {
                        delivered.onResult(presenter.getUnits().getTapWidth());
                    }
                });
                extract.present(new ZeroUnits().withTapWidth(supplied), null, null, null);
            }
        });
    }

    public void testCreateSuppliesPresenterStrokeWidth() throws Exception {

        assertProducerDeliveryEqualsSupplied(17f, new Producer<Float>() {
            @Override
            public void produce(Float supplied, final Monitor<Float> delivered) {
                final Interactive<Object> extract = Interactives.create(new OnPresent<Object>() {
                    @Override
                    public void call(Presenter<Object> presenter) {
                        delivered.onResult(presenter.getUnits().getStrokeWidth());
                    }
                });
                extract.present(new ZeroUnits().withStrokeWidth(supplied), null, null, null);
            }
        });
    }

    public void testCreateSuppliesPresenterTextHeight() throws Exception {

        assertProducerDeliveryEqualsSupplied(17f, new Producer<Float>() {
            @Override
            public void produce(Float supplied, final Monitor<Float> delivered) {
                final Interactive<Object> extract = Interactives.create(new OnPresent<Object>() {
                    @Override
                    public void call(Presenter<Object> presenter) {
                        delivered.onResult(presenter.getUnits().getTextHeight());
                    }
                });
                extract.present(new ZeroUnits().withTextHeight(supplied), null, null, null);
            }
        });
    }

    public void testCreateSuppliesPresenterPerimeter() throws Exception {

        assertProducerDeliveryEqualsSupplied(new RectF(1, 2, 3, 4), new Producer<RectF>() {
            @Override
            public void produce(RectF supplied, final Monitor<RectF> delivered) {
                final Interactive<Object> extractPerimeter = Interactives.create(new OnPresent<Object>() {
                    @Override
                    public void call(Presenter<Object> presenter) {
                        delivered.onResult(presenter.getUnits().getPerimeter());
                    }
                });
                extractPerimeter.present(new ZeroUnits().withPerimeter(supplied), null, null, null);
            }
        });
    }
}
