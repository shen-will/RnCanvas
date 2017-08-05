package com.example.rncanvas;

import android.graphics.Path;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.views.art.ARTSurfaceViewShadowNode;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shen on 2017/8/4.
 */

public class NodeSave {

    private static  Map<ReactShadowNode,ARTSurfaceViewShadowNode> save=new HashMap<>();

    public static void put(ARTSurfaceViewShadowNode node){

        save.put(node.getRootNode(),node);
    }

    public static ARTSurfaceViewShadowNode get(ReactShadowNode node){

        if(save.containsKey(node))
            return save.get(node);

        return null;
    }

}
