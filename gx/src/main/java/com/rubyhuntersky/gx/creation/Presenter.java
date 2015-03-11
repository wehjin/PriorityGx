package com.rubyhuntersky.gx.creation;

import com.rubyhuntersky.gx.basic.Interactor;
import com.rubyhuntersky.gx.basic.Presentation;
import com.rubyhuntersky.gx.support.PartsPresenter;

/**
 * @author wehjin
 * @since 1/27/15.
 */

public interface Presenter<T> extends Interactor<T>, Presentation, PresentationGroup {

    <KeyT> PartsPresenter<KeyT> enableParts(Class<KeyT> keyClass);
}
