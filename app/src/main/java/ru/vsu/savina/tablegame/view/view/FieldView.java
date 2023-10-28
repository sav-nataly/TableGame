package ru.vsu.savina.tablegame.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Map;
import java.util.Queue;

import ru.vsu.savina.tablegame.R;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Edge;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.diagram.Point;
import ru.vsu.savina.tablegame.fieldgeneration.voronoigeneration.polygon.Polygon;
import ru.vsu.savina.tablegame.game.engine.field.Place;
import ru.vsu.savina.tablegame.game.impl.field.BluePlace;
import ru.vsu.savina.tablegame.game.impl.field.GreenPlace;
import ru.vsu.savina.tablegame.game.impl.field.GreyPlace;
import ru.vsu.savina.tablegame.game.impl.field.RedEnemyPlace;
import ru.vsu.savina.tablegame.game.impl.field.RedMonsterPlace;
import ru.vsu.savina.tablegame.game.impl.field.RedNativesPlace;
import ru.vsu.savina.tablegame.game.impl.field.YellowPlace;
import ru.vsu.savina.tablegame.game.impl.player.TableGamePlayer;
import ru.vsu.savina.tablegame.view.wrapper.FieldWrapper;
import ru.vsu.savina.tablegame.view.wrapper.PlaceWrapper;
import ru.vsu.savina.tablegame.view.wrapper.TransitionWrapper;

public class FieldView extends View {
    private Paint paint = new Paint();
    private FieldWrapper wrapper;
    private Map<TableGamePlayer, Place> playerPlaceMap;

    private float logicalDensity;

    public FieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        logicalDensity = metrics.density;

        if (wrapper != null) {
            drawPolygons(canvas);
            drawField(canvas);
            drawPlayers(canvas);
        }
    }

    private void drawPolygons(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        for (Polygon polygon : wrapper.getPolygons()) {
            switch (polygon.getType()) {
                case RIVER:
                    paint.setColor(ContextCompat.getColor(getContext(), R.color.river_blue));
                    break;
                case PLAIN:
                    paint.setColor(ContextCompat.getColor(getContext(), R.color.plains_yellow));
                    break;
                case OCEAN:
                    paint.setColor(ContextCompat.getColor(getContext(), R.color.ocean_blue));
                    break;
                case SWAMP:
                    paint.setColor(ContextCompat.getColor(getContext(), R.color.swamp_green));
                    break;
                case JANGA:
                    paint.setColor(ContextCompat.getColor(getContext(), R.color.janga_red));
                    break;
            }
            Path path = new Path();

            float[][] coord = getPolygonCoordinates(polygon);

            for (int i = 0; i < polygon.getPoints().size(); i++) {
                if (i == 0)
                    path.moveTo(coord[0][i], coord[1][i]);
                else
                    path.lineTo(coord[0][i], coord[1][i]);
            }
            canvas.drawPath(path, paint);

        }
    }

    private void drawField(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.place_grey));
        paint.setStrokeWidth(20f);

        for (TransitionWrapper wr : wrapper.getTransitionList()) {
            canvas.drawLine(
                    pixelsToDp(wr.getSource().getX()),
                    pixelsToDp(wr.getSource().getY()),
                    pixelsToDp(wr.getTarget().getX()),
                    pixelsToDp(wr.getTarget().getY()),
                    paint
            );
        }


        for (PlaceWrapper wr : wrapper.getPlaceList()) {
            int fillColor, strokeColor;

            if (wr.getPlace().getClass().equals(BluePlace.class)) {
                fillColor = ContextCompat.getColor(getContext(), R.color.place_blue_transparent);
                strokeColor = ContextCompat.getColor(getContext(), R.color.place_blue);
                drawCirclePlace(canvas, wr, fillColor, strokeColor);
            } else if (wr.getPlace().getClass().equals(GreenPlace.class)) {
                fillColor = ContextCompat.getColor(getContext(), R.color.place_green_transparent);
                strokeColor = ContextCompat.getColor(getContext(), R.color.place_green);
                drawCirclePlace(canvas, wr, fillColor, strokeColor);
            } else if (wr.getPlace().getClass().equals(RedMonsterPlace.class)) {
                fillColor = ContextCompat.getColor(getContext(), R.color.place_red_transparent);
                strokeColor = ContextCompat.getColor(getContext(), R.color.place_red);
                drawStarPlace(canvas, wr, fillColor, strokeColor);
            } else if (wr.getPlace().getClass().equals(RedEnemyPlace.class)) {
                fillColor = ContextCompat.getColor(getContext(), R.color.place_red_transparent);
                strokeColor = ContextCompat.getColor(getContext(), R.color.place_red);
                drawRectanglePlace(canvas, wr, fillColor, strokeColor);
            } else if (wr.getPlace().getClass().equals(RedNativesPlace.class)) {
                fillColor = ContextCompat.getColor(getContext(), R.color.place_red_transparent);
                strokeColor = ContextCompat.getColor(getContext(), R.color.place_red);
                drawCirclePlace(canvas, wr, fillColor, strokeColor);
            } else if (wr.getPlace().getClass().equals(YellowPlace.class)) {
                fillColor = ContextCompat.getColor(getContext(), R.color.place_yellow_transparent);
                strokeColor = ContextCompat.getColor(getContext(), R.color.place_yellow);
                drawCirclePlace(canvas, wr, fillColor, strokeColor);
            } else if (wr.getPlace().getClass().equals(GreyPlace.class)) {
                fillColor = ContextCompat.getColor(getContext(), R.color.place_grey_transparent);
                strokeColor = ContextCompat.getColor(getContext(), R.color.place_grey);
                drawCirclePlace(canvas, wr, fillColor, strokeColor);
            } else {
                fillColor = ContextCompat.getColor(getContext(), R.color.place_green_transparent);
                strokeColor = ContextCompat.getColor(getContext(), R.color.place_green);
                drawStarPlace(canvas, wr, fillColor, strokeColor);
            }
        }
    }

    private void drawCirclePlace(Canvas canvas, PlaceWrapper wr, int fillColor, int strokeColor) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fillColor);
        canvas.drawCircle(pixelsToDp(wr.getX()), pixelsToDp(wr.getY()), 40, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(strokeColor);
        canvas.drawCircle(pixelsToDp(wr.getX()), pixelsToDp(wr.getY()), 40, paint);
    }

    private void drawStarPlace(Canvas canvas, PlaceWrapper wr, int fillColor, int strokeColor) {
        Path path = new Path();

        float centerX = pixelsToDp(wr.getX());
        float centerY = pixelsToDp(wr.getY());

        path.moveTo(centerX + 50, centerY);
        path.lineTo(centerX + 25 * (float) Math.cos(2 * Math.PI / 10), centerY + 25 * (float) Math.sin(2 * Math.PI / 10));

        for (int i = 1; i < 5; i++) {
            double angle = 2 * Math.PI / 5 * i;
            path.lineTo(centerX + 50 * (float) Math.cos(angle), centerY + 50 * (float) Math.sin(angle));
            double angle2 = 2 * Math.PI / 5 * i + 2 * Math.PI / 10;
            path.lineTo(centerX + 25 * (float) Math.cos(angle2), centerY + 25 * (float) Math.sin(angle2));
        }

        path.close();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fillColor);
        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(strokeColor);
        canvas.drawPath(path, paint);
    }

    private void drawRectanglePlace(Canvas canvas, PlaceWrapper wr, int fillColor, int strokeColor) {
        Path path = new Path();

        float centerX = pixelsToDp(wr.getX());
        float centerY = pixelsToDp(wr.getY());

        path.moveTo(centerX - 40, centerY);
        path.lineTo(centerX, centerY - 40);
        path.lineTo(centerX + 40, centerY);
        path.lineTo(centerX, centerY + 40);
        path.close();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fillColor);
        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(strokeColor);
        canvas.drawPath(path, paint);
    }

    private void drawPlayers(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.black));
        paint.setTextSize(50);

        for (TableGamePlayer player : playerPlaceMap.keySet()) {
            PlaceWrapper wr = wrapper.getWrapperByPlace(playerPlaceMap.get(player));

            Path path = new Path();

            float centerX = pixelsToDp(wr.getX());
            float centerY = pixelsToDp(wr.getY());

            path.moveTo(centerX + 20, centerY - 70);
            path.lineTo(centerX - 20, centerY - 70);
            path.lineTo(centerX - 20, centerY - 30);
            path.lineTo(centerX - 40, centerY - 30);
            path.lineTo(centerX, centerY + 10);
            path.lineTo(centerX + 40, centerY - 30);
            path.lineTo(centerX + 20, centerY- 30);
            path.close();
            canvas.drawPath(path, paint);

            canvas.drawText(player.getName(), centerX - 50, centerY - 80, paint);
        }
    }

    public float pixelsToDp(double px) {
        return (float) px / logicalDensity * 20;
    }

    private float[][] getPolygonCoordinates(Polygon polygon) {
        Point left = polygon.getLeftPoint();

        Queue<Point> points = new ArrayDeque<>();
        float[][] coord = new float[2][polygon.getPoints().size()];
        boolean[] visited = new boolean[polygon.getEdges().size()];

        points.add(left);
        int counter = 0;
        while (!points.isEmpty()) {
            Point next = points.poll();
            if (counter < polygon.getPoints().size()) {
                coord[0][counter] = pixelsToDp(next.getX());
                coord[1][counter] = pixelsToDp(next.getY());
            }
            for (Edge e : polygon.getEdges()) {
                if (!visited[polygon.getEdges().indexOf(e)]) {
                    if (e.getStart().equals(next)) {
                        points.add(e.getEnd());
                        counter++;
                        visited[polygon.getEdges().indexOf(e)] = true;
                        break;
                    }
                    if (e.getEnd().equals(next)) {
                        points.add(e.getStart());
                        visited[polygon.getEdges().indexOf(e)] = true;
                        counter++;
                        break;
                    }
                }
            }
        }
        return coord;
    }


    public FieldWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(FieldWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Map<TableGamePlayer, Place> getPlayerPlaceMap() {
        return playerPlaceMap;
    }

    public void setPlayerPlaceMap(Map<TableGamePlayer, Place> playerPlaceMap) {
        this.playerPlaceMap = playerPlaceMap;
    }
}
