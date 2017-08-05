package com.example.rncanvas;

import android.util.Log;

import com.facebook.react.views.art.ARTSurfaceView;
import com.facebook.react.views.art.ARTSurfaceViewManager;
import com.facebook.react.views.art.ARTSurfaceViewShadowNode;

/**
 * Created by shen on 2017/8/4.
 */

public class SuperARTSurfaceViewManager extends ARTSurfaceViewManager{

    @Override
    public void updateExtraData(ARTSurfaceView root, Object extraData) {

        super.updateExtraData(root, extraData);

        ARTSurfaceViewShadowNode node = (ARTSurfaceViewShadowNode) extraData;

        NodeSave.put(node);
    }
}
