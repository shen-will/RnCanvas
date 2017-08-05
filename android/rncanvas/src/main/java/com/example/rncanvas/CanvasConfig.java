package com.example.rncanvas;

import android.util.Log;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.uimanager.ViewManager;

import java.util.List;

/**
 * Created by shen on 2017/8/4.
 */

public class CanvasConfig {

    public static ReactPackage getMainPackage(){

        return new MainReactPackage(){


            @Override
            public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
                List<ViewManager> viewManagers = super.createViewManagers(reactContext);

                //替换掉ARTSurfaceView便于保存surfaceNode
                for(int i=0;i<viewManagers.size();i++){

                    if(viewManagers.get(i).getName().equals("ARTSurfaceView")){
                        viewManagers.remove(i);

                        viewManagers.add(new SuperARTSurfaceViewManager());
                    }
                }
                //自己的node
                viewManagers.add(new CanvasViewManager());

                return viewManagers;
            }
        };

    }
}
