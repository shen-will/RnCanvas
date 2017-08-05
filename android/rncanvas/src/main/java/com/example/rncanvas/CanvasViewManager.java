package com.example.rncanvas;

import android.view.View;

import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;

/**
 * Created by shen on 2017/8/3.
 */

public class CanvasViewManager extends ViewManager<View, ReactShadowNode> {

    private final  String name="ARTCanvas";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ReactShadowNode createShadowNodeInstance() {
        return new CanvasNode();
    }

    @Override
    public Class<? extends ReactShadowNode> getShadowNodeClass() {
        return CanvasNode.class;
    }

    @Override
    protected View createViewInstance(ThemedReactContext reactContext) {

        throw new IllegalStateException("ARTCanvas does not map into a native view");

    }

    @Override
    public void updateExtraData(View root, Object extraData) {

        throw new IllegalStateException("ARTCanvas does not map into a native view");
    }
}
