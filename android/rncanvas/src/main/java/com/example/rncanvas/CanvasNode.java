package com.example.rncanvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Surface;
import android.view.View;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.art.ARTSurfaceViewShadowNode;
import com.facebook.react.views.art.ARTVirtualNode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shen on 2017/8/3.
 */

public class CanvasNode extends ARTVirtualNode {

    private ReadableArray method;

    private final String TYPE_CANVAS="canvas";

    private final String TYPE_PAINT="paint";


    private Paint paint;


    private static Paint.Cap[] caps=new Paint.Cap[]{Paint.Cap.BUTT, Paint.Cap.ROUND, Paint.Cap.SQUARE};

    private static Paint.Join[] jons=new Paint.Join[]{Paint.Join.BEVEL, Paint.Join.MITER, Paint.Join.ROUND};

    private static Paint.Style[] styles=new Paint.Style[]{Paint.Style.STROKE, Paint.Style.FILL, Paint.Style.FILL_AND_STROKE};



    private static Map<String,Class[]> methodList=new HashMap<>();



    static {

        //开放给js层的方法

        methodList.put("drawArc",new Class[]{float.class,float.class,
                float.class,float.class,float.class,float.class,boolean.class,Paint.class
        });

        methodList.put("drawCircle",new Class[]{float.class,float.class,float.class,Paint.class});

        //drawRect(float left, float top, float right, float bottom, @NonNull Paint pa

        methodList.put("drawRect",new Class[]{float.class,
                float.class,float.class,float.class,Paint.class});

        // drawARGB(int a, int r, int g, int b)

        methodList.put("drawARGB",new Class[]{int.class,
                int.class,int.class,int.class});

        methodList.put("drawColor",new Class[]{int.class});

        //drawLine(float startX, float startY, float stopX, float stopY,
       // @NonNull Paint paint) {

        methodList.put("drawLine",new Class[]{float.class,float.class,
                float.class,float.class,Paint.class});

        // void drawLines(@Size(min=4,multiple=2) float[] pts, int offset, int count, Paint paint) {
        methodList.put("drawLines",new Class[]{float[].class,int.class,
                int.class,Paint.class});

        //drawOval(float left, float top, float right, float bottom, @NonNull Paint paint) {

        methodList.put("drawOval",new Class[]{float.class,float.class,
                float.class,float.class,Paint.class});

        //drawText(@NonNull String text, int start, int end, float x, float y,
      //  @NonNull Paint paint) {


        methodList.put("drawText",new Class[]{String.class,int.class,
                int.class,float.class,float.class,Paint.class});

        // drawRoundRect(float left, float top, float right, float bottom, float rx, float ry,
        //@NonNull Paint paint)

        methodList.put("drawRoundRect",new Class[]{float.class,float.class,
                float.class,float.class,float.class,float.class,Paint.class});

        // scale(float sx, float sy)
        methodList.put("scale",new Class[]{float.class,float.class});


        //translate(float dx, float dy)

        methodList.put("translate",new Class[]{float.class,float.class});

        //rotate(float degrees)

        methodList.put("rotate",new Class[]{float.class});

    }


    @ReactProp(name = "method")
    public void setDrawMethod(ReadableArray array){
        //接收到要执行的方法

        this.method=array;


        //手动刷新界面
        invalidate();
    }

    private void invalidate(){

        ARTSurfaceViewShadowNode node = NodeSave.get(getRootNode());

        Surface surface = getSurface(node);
        if(surface!=null && paint!=null)
        {
            Canvas item =surface.lockCanvas(null);
            draw(item,paint,1.0f);

            surface.unlockCanvasAndPost(item);
        }

    }


    private Surface getSurface(ARTSurfaceViewShadowNode node ){

        try {
            Field mSurface = ARTSurfaceViewShadowNode.class.getDeclaredField("mSurface");

            mSurface.setAccessible(true);

            Surface value = (Surface) mSurface.get(node);

            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, float opacity) {

        //node具体的绘制操作

        this.paint=paint;

        ReadableMap map;

        if(this.method!=null){

            for(int i=0;i<this.method.size();i++){

                map=this.method.getMap(i);

                if(map!=null){
                    drawMap(map,canvas,paint);
                }
            }
        }

    }

    public void drawMap(ReadableMap map,Canvas canvas,Paint paint){

        //判断是Canvas 或者是Paint的操作

        String type=map.getString("type");

        if(TYPE_CANVAS.equals(type)){
            drawMapForCanvas(map,canvas,paint);
        }
        else if(TYPE_PAINT.equals(type)){

            drawMapForPaint(map,paint);
        }

    }

    public void drawMapForCanvas(ReadableMap map,Canvas canvas,Paint paint){
        //对js层开放的方法
        ReadableArray args = map.getArray("args");

        String methodName=map.getString("method");

        Method tarMethod= null;
        try {
            tarMethod =Canvas.class.getMethod(methodName,methodList.get(methodName));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if(tarMethod!=null){
            try {

                Object[] ivs=mapToArgs(args,methodList.get(methodName),paint);

                onMethodWillDo(tarMethod,ivs);

                tarMethod.invoke(canvas,ivs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void onMethodWillDo(Method method,Object[] args){

        if("drawText".equals(method.getName())){

            //drawText(@NonNull String text, int start, int end, float x, float y,
            //  @NonNull Paint paint) {

            String text= (String) args[0];

            float width=paint.measureText(text);

            float height=width/text.length();


            Log.d("shen","字体:"+height+"--"+width);

            args[3]=((float)args[3])-width/2;

            args[4]=((float)args[4])+height/2;

        }


    }

    public Object[] mapToArgs(ReadableArray array,Class[] clas,Paint paint){

        if(array.size()!=clas.length){
            throw new IllegalArgumentException("参数长度不匹配");
        }
        Object[] result=new Object[clas.length];

        for(int i=0;i<clas.length;i++){

            switch (clas[i].getSimpleName()){

                case "int":
                    result[i]=array.getInt(i);
                    break;
                case "float":
                    result[i]=(float)array.getDouble(i);
                    break;
                case "double":
                    result[i]=array.getDouble(i);

                    break;
                case "String":
                    result[i]=array.getString(i);
                    break;
                case "Paint":
                    result[i]=paint;
                    break;
                case "boolean":
                    result[i]=array.getBoolean(i);
                    break;
                case "float[]":

                    ReadableArray tmp = array.getArray(i);

                    float[] fs=new float[tmp.size()];

                    for(int f=0;f<tmp.size();f++){

                        fs[f]= (float) tmp.getDouble(f);
                    }

                    result[i]=fs;
                    break;

            }
        }

        return result;
    }


    public void drawMapForPaint(ReadableMap map,Paint paint){

        //开放给js层的Paint属性

        if(map.hasKey("Alpha")){
            paint.setAlpha(map.getInt("Alpha"));
        }

        if(map.hasKey("Color")){
            paint.setColor(Color.parseColor(map.getString("Color")));
        }

        if(map.hasKey("AntiAlias")){
            paint.setAntiAlias(map.getBoolean("AntiAlias"));
        }

        if(map.hasKey("StrokeWidth")){
            paint.setStrokeWidth((float) map.getDouble("StrokeWidth"));
        }

        if(map.hasKey("StrokeCap")){
            paint.setStrokeCap(caps[map.getInt("StrokeCap")]);
        }

        if(map.hasKey("StrokeJoin")){
            paint.setStrokeJoin(jons[map.getInt("StrokeJoin")]);
        }

        if(map.hasKey("Style")){
            paint.setStyle(styles[map.getInt("Style")]);
        }

        if(map.hasKey("TextSize")){
            paint.setTextSize((float) map.getDouble("TextSize"));
        }
    }


}
