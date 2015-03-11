package com.rubyhuntersky.gx.support;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Monitor;
import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.interaction.TouchInteraction;
import com.rubyhuntersky.gx.picture.Picture;

abstract public class ConsoleView extends View implements Console {

    public static final String TAG = ConsoleView.class.getSimpleName();

    private RectF perimeter = new RectF();
    private Picture picture;
    private Responder responder;
    private CanvasPictureDrawer pictureDrawer = new CanvasPictureDrawer();
    private Console outerConsole;
    private OnPerimeterChangeListener perimeterChangeListener;
    private Animator animator;

    public ConsoleView(Context context) {
        super(context);
        init(null, 0);
    }

    public ConsoleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ConsoleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void setOuterConsole(Console console) {
        this.outerConsole = console;
    }

    public void setOnPerimeterChangeListener(OnPerimeterChangeListener perimeterChangeListener) {
        this.perimeterChangeListener = perimeterChangeListener;
    }

    protected void onPerimeter(RectF perimeter) {
        if (perimeterChangeListener == null) {
            return;
        }
        perimeterChangeListener.onPerimeterChange(perimeter);
    }

    private void init(AttributeSet attrs, int defStyle) {
        setOnTouchListener(getTouchListener());
    }

    @Override
    public void onPicture(Picture picture) {
        this.picture = picture;
        if (picture == null) {
            Log.w(TAG, "Picture is null");
        }
        invalidate();
    }

    @Override
    public void onResponder(Responder responder) {
        this.responder = responder;
    }

    @Override
    public <DialogT> void onDialog(Interactive<? extends DialogT> interactive, DialogT settings,
          final Monitor<? super DialogT> monitor) {
        if (outerConsole == null) {
            return;
        }

        outerConsole.onDialog(interactive, settings, monitor);
    }

    @Override
    public void onAnimator(final Animator animator) {
        this.animator = animator;
        if (animator == null) {
            return;
        }
        postOnAnimation(new Runnable() {
            final Animator myAnimator = animator;

            @Override
            public void run() {
                myAnimator.onAnimationFrame();
                Animator consoleAnimator = ConsoleView.this.animator;
                if (consoleAnimator == null || consoleAnimator != myAnimator) {
                    return;
                }
                postOnAnimation(this);
            }
        });
    }

    private OnTouchListener getTouchListener() {
        return new OnTouchListener() {

            private TouchInteraction touchInteraction;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (responder == null) {
                            return false;
                        }
                        int[] location = {0, 0};
                        v.getLocationOnScreen(location);
                        float x = event.getRawX() - location[0];
                        float y = event.getRawY() - location[1];
                        touchInteraction = responder.getTouchInteraction(x, y);
                        return touchInteraction != null;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        if (touchInteraction != null) {
                            int[] location = {0, 0};
                            v.getLocationOnScreen(location);
                            float x = event.getRawX() - location[0];
                            float y = event.getRawY() - location[1];
                            touchInteraction.move(x, y);
                            return true;
                        }
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        if (touchInteraction != null) {
                            touchInteraction.cancel();
                            touchInteraction = null;
                        }
                    }
                    case MotionEvent.ACTION_UP: {
                        if (touchInteraction != null) {
                            touchInteraction.up();
                            touchInteraction = null;
                        }
                    }
                }
                return false;
            }
        };
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        Log.d(TAG, "onLayout " + contentHeight);
        if (perimeter.height() == contentHeight && perimeter.width() == contentWidth) {
            return;
        }
        perimeter.set(paddingLeft, paddingTop, paddingLeft + contentWidth, paddingTop + contentHeight);
        onPerimeter(perimeter);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (picture == null) {
            return;
        }
        pictureDrawer.drawPicture(picture, canvas);
    }

    public interface OnPerimeterChangeListener {
        void onPerimeterChange(RectF perimeter);
    }
}
