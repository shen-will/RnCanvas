import React, { Component } from 'react';
import {
    View,ART,requireNativeComponent
} from 'react-native';

const Base=requireNativeComponent("ARTCanvas");


class Canvas{

    constructor(){
        this.method=[];

    }

    drawArc(left, top, right, bottom,  startAngle,  sweepAngle,  useCenter){

        this.method.push({
            type:"canvas",
            method:"drawArc",
            args:[left,top,right,bottom,startAngle,sweepAngle,useCenter,-1]
        });
    }

    drawRect( left,  top,  right,  bottom){

        this.method.push({
            type:"canvas",
            method:"drawRect",
            args:[left,top,right,bottom,-1]
        });
    }


    drawCircle(cx, cy,  radius){

        this.method.push({
            type:"canvas",
            method:"drawCircle",
            args:[cx,cy,radius,-1]
        });
    }


    drawARGB( a,  r,  g,  b){
        this.method.push({
            type:"canvas",
            method:"drawARGB",
            args:[a,r,g,b]
        });
    }

    drawColor(color){

        this.method.push({
            type:"canvas",
            method:"drawColor",
            args:[color]
        });
    }


    drawLine( startX,  startY,  stopX,  stopY){

        this.method.push({
            type:"canvas",
            method:"drawLine",
            args:[startX,startY,stopX,stopY,-1]
        });
    }

     drawLines( pts, offset,  count){

         this.method.push({
             type:"canvas",
             method:"drawLines",
             args:[pts,offset,count,-1]
         });
    }

    drawOval( left,  top,  right,  bottom){

        this.method.push({
            type:"canvas",
            method:"drawOval",

            args:[left,top,right,bottom,-1]
        });
    }

    drawText(  text,  start,  end,  x,  y){
        this.method.push({
            type:"canvas",
            method:"drawText",

            args:[text,start,end,x,y,-1]
        });
    }


    drawRoundRect( left,  top,  right,  bottom,  rx,  ry){


        this.method.push({
            type:"canvas",
            method:"drawRoundRect",

            args:[left,top,end,right,bottom,rx,ry,-1]
        });
    }

    scale( sx,  sy){
        this.method.push({
            type:"canvas",
            method:"scale",

            args:[sx,sy]
        });

    }


    setPaintTextSize(TextSize){

        this.setPaintObj("TextSize",TextSize);
    }


    setPaintColor(color){

        this.setPaintObj("Color",color);
    }

    setPaintStyle(style){
        this.setPaintObj("Style",style);

    }

    setPaintAlpha(alpha){

        this.setPaintObj("Alpha",alpha);
    }


    setPaintStrokeWidth(StrokeWidth){

        this.setPaintObj("StrokeWidth",StrokeWidth);

    }


    setPaintAntiAlias(AntiAlias){

        this.setPaintObj("AntiAlias",AntiAlias);
    }

    setPaintStrokeCap(StrokeCap){

        this.setPaintObj("StrokeCap",StrokeCap);
    }


    setPaintStrokeJoin(StrokeJoin){

        this.setPaintObj("StrokeJoin",StrokeJoin);
    }




    setPaintObj(name,value){

        let obj={};

        obj["type"]="paint";

        obj[name]=value;

        this.method.push(obj);
    }



    createView(){

        return <Base method={this.method}/>
    }

}

const Paint={

    Cap:{
        BUTT:0,
        ROUND:1,
        SQUARE:2
    },

    Join:{

        BEVEL:0,
        MITER:1,
        ROUND:2
    },
    Style:{

        STROKE:0,
        FILL:1,
        FILL_AND_STROKE:2

    }



}

module.exports={
    Canvas,Paint
}

