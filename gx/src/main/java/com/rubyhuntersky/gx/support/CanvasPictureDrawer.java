package com.rubyhuntersky.gx.support;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;

import com.rubyhuntersky.gx.picture.ArrowToCenterPicture;
import com.rubyhuntersky.gx.picture.ArrowToEdgePicture;
import com.rubyhuntersky.gx.picture.ColoredOvalPicture;
import com.rubyhuntersky.gx.picture.ColoredRectanglePicture;
import com.rubyhuntersky.gx.picture.CrossPicture;
import com.rubyhuntersky.gx.picture.Picture;
import com.rubyhuntersky.gx.picture.PictureList;
import com.rubyhuntersky.gx.picture.PicturePair;
import com.rubyhuntersky.gx.picture.PictureSet;
import com.rubyhuntersky.gx.picture.TextLinePicture;
import com.rubyhuntersky.gx.picture.TextMultiLinePicture;
import com.rubyhuntersky.gx.text.TextBreaker;
import com.rubyhuntersky.gx.text.TextLine;
import com.rubyhuntersky.gx.text.TextRuler;

import java.util.List;

/**
 * @author wehjin
 * @since 1/29/15.
 */

public class CanvasPictureDrawer {

    public static final float ARROW_POINT_EDGE_SHIFT = 1.25f;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
    private TextBreaker textBreaker = new TextBreaker();
    private final Path path = new Path();

    public void drawPicture(Picture picture, Canvas canvas) {
        if (picture == null) {
            return;
        }
        if (picture instanceof CrossPicture) {
            drawCrossPicture(canvas, (CrossPicture) picture);
            return;
        }
        if (picture instanceof ArrowToCenterPicture) {
            drawArrowToCenterPicture(canvas, (ArrowToCenterPicture) picture);
            return;
        }
        if (picture instanceof ArrowToEdgePicture) {
            drawArrowToEdgePicture(canvas, (ArrowToEdgePicture) picture);
            return;
        }
        if (picture instanceof ColoredRectanglePicture) {
            drawColoredRectanglePicture(canvas, (ColoredRectanglePicture) picture);
            return;
        }
        if (picture instanceof ColoredOvalPicture) {
            drawColoredOvalPicture(canvas, (ColoredOvalPicture) picture);
            return;
        }
        if (picture instanceof PictureSet) {
            PictureSet pictureSet = (PictureSet) picture;
            for (PictureSet.Item item : pictureSet.getItems()) {
                drawPicture(item.picture, canvas);
            }
            return;
        }
        if (picture instanceof PictureList) {
            for (Picture subPicture : ((PictureList) picture).getPictures()) {
                drawPicture(subPicture, canvas);
            }
            return;
        }
        if (picture instanceof PicturePair) {
            drawPicture(((PicturePair) picture).getBackground(), canvas);
            drawPicture(((PicturePair) picture).getForeground(), canvas);
            return;
        }
        if (picture instanceof TextLinePicture) {
            drawTextLinePicture((TextLinePicture) picture, canvas);
            return;
        }
        if (picture instanceof TextMultiLinePicture) {
            TextMultiLinePicture textMultiLinePicture = (TextMultiLinePicture) picture;
            RectF perimeter = textMultiLinePicture.getPerimeter();
            textPaint.setTypeface(Typeface.DEFAULT);
            textPaint.setTextAlign(Paint.Align.LEFT);
            textPaint.setColor(textMultiLinePicture.getColor());
            int lineSpacing = (int) (perimeter.height() / textMultiLinePicture.getMaxLineCount());
            int textSize = (int) (lineSpacing * .7f);
            textPaint.setTextSize(textSize);
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float extraInset = fontMetrics.descent / 2;
            int maxLineWidth = (int) (perimeter.width() - 2 * extraInset);
            List<TextLine> textLines = textBreaker.breakText(textMultiLinePicture.getText(), maxLineWidth, 3,
                  new TextRuler() {
                      @Override
                      public float getTextWidth(String text) {
                          return textPaint.measureText(text);
                      }
                  });
            int lineLeft = (int) ((int) perimeter.left + extraInset);
            int baseTop = (int) (perimeter.top - fontMetrics.ascent + extraInset);
            int count = 0;
            for (TextLine textLine : textLines) {
                float lineTop = baseTop + count * lineSpacing;
                canvas.drawText(textLine.text, lineLeft, lineTop, textPaint);
                count++;
            }
        }
    }

    private void drawColoredOvalPicture(Canvas canvas, ColoredOvalPicture picture) {
        paint.setColor(picture.getColor());
        canvas.drawOval(picture.getRect(), paint);
    }

    private void drawColoredRectanglePicture(Canvas canvas, ColoredRectanglePicture picture) {
        paint.setColor(picture.getColor());
        canvas.drawRect(picture.getRect(), paint);
    }

    private void drawCrossPicture(Canvas canvas, CrossPicture picture) {
        float previousStrokeWidth = paint.getStrokeWidth();
        paint.setColor(picture.getColor());
        paint.setStrokeWidth(picture.getPenWidth());
        RectF perimeter = picture.getPerimeter();
        canvas.drawLine(perimeter.left, perimeter.top, perimeter.right, perimeter.bottom, paint);
        canvas.drawLine(perimeter.left, perimeter.bottom, perimeter.right, perimeter.top, paint);
        paint.setStrokeWidth(previousStrokeWidth);
    }

    private void drawArrowToEdgePicture(Canvas canvas, ArrowToEdgePicture picture) {
        float previousStrokeWidth = paint.getStrokeWidth();

        paint.setColor(picture.getColor());
        float penWidth = picture.getPenWidth();
        paint.setStrokeWidth(penWidth);
        RectF perimeter = picture.getPerimeter();

        float pointX;
        float pointY;
        float tailX;
        float tailY;
        final int azimuth = picture.getAzimuth();
        switch (azimuth) {
            case 0:
                tailX = perimeter.centerX();
                tailY = perimeter.bottom;
                pointX = tailX;
                pointY = Math.round(perimeter.top + penWidth * ARROW_POINT_EDGE_SHIFT);
                break;
            case 4:
                tailX = perimeter.centerX();
                tailY = perimeter.top;
                pointX = tailX;
                pointY = Math.round(perimeter.bottom - penWidth * ARROW_POINT_EDGE_SHIFT);
                break;
            default:
                tailX = perimeter.right;
                tailY = perimeter.centerY();
                pointX = Math.round(perimeter.left + penWidth * ARROW_POINT_EDGE_SHIFT);
                pointY = tailY;
                break;
        }

        canvas.drawLine(pointX, pointY, tailX, tailY, paint);

        float wingReach;
        switch (azimuth) {
            case 0:
            case 4:
                wingReach = perimeter.width() / 2;
                break;
            default:
                wingReach = perimeter.height() / 2;
                break;
        }
        float wingSweep = wingReach * picture.getSweep();

        float wingAX;
        float wingAY;
        float wingBX;
        float wingBY;
        switch (azimuth) {
            case 0: {
                wingAX = pointX - wingReach;
                wingBX = pointX + wingReach;
                wingAY = wingBY = pointY + wingSweep;
                break;
            }
            case 4: {
                wingAX = pointX - wingReach;
                wingBX = pointX + wingReach;
                wingAY = wingBY = pointY - wingSweep;
                break;
            }
            default: {
                wingAX = pointX + wingSweep;
                wingAY = pointY - wingReach;
                wingBX = wingAX;
                wingBY = pointY + wingReach;
                break;
            }
        }
        path.reset();
        path.moveTo(wingAX, wingAY);
        path.lineTo(pointX, pointY);
        path.lineTo(wingBX, wingBY);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(previousStrokeWidth);
    }

    private void drawArrowToCenterPicture(Canvas canvas, ArrowToCenterPicture picture) {
        float previousStrokeWidth = paint.getStrokeWidth();

        paint.setColor(picture.getColor());
        final float penWidth = picture.getPenWidth();
        paint.setStrokeWidth(penWidth);
        RectF perimeter = picture.getPerimeter();

        float pointX = perimeter.centerX();
        float pointY = Math.round(perimeter.centerY() - penWidth / 2);
        canvas.drawLine(pointX, perimeter.top, pointX, pointY, paint);

        float wingHeight = perimeter.height() / 4;
        float wingWidth = perimeter.width() / 4;
        path.reset();
        path.moveTo(pointX - wingWidth, pointY - wingHeight);
        path.rLineTo(wingWidth, wingHeight);
        path.rLineTo(wingHeight, -wingHeight);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(previousStrokeWidth);
    }

    private void drawTextLinePicture(TextLinePicture picture, Canvas canvas) {
        RectF perimeter = picture.getPerimeter();
        textPaint.setColor(picture.getColor());
        textPaint.setTextSize(Math.round(perimeter.height() * .5f));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        float available = perimeter.width();
        String fitted = TextUtils.ellipsize(picture.getText(), textPaint, available, TextUtils.TruncateAt.END)
                                 .toString();
        float textY = perimeter.centerY() - textPaint.getFontMetrics().ascent * .38f;
        canvas.drawText(fitted, perimeter.centerX(), textY, textPaint);
    }
}
