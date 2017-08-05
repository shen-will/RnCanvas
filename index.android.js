/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  View,ART,requireNativeComponent,Button
} from 'react-native';



import {Canvas,Paint} from "./RnCanvas/Canvas"

const { Shape ,Surface,Group,Text,Transform} = ART;


import Path from  "./RnCanvas/Path"




export default class chartest extends Component {

    constructor(props)
    {
        super(props);

        this.state={

            progress:0
        }

        this.start=this.start.bind(this);
    }


    componentWillUnmount(){

       clearTimeout(this.timer);
    }

    start(){
        this.timer=setTimeout(()=>{

            this.setState({

                progress:this.state.progress+2
            });

            if(this.state.progress<100)
            {
                this.start();
            }

        },200);

    }

    getProgressView(){
        let circle=new Canvas();

        circle.setPaintStyle(Paint.Style.FILL);



        circle.setPaintColor("#088000");

        let angle=this.state.progress/100 * 360;

        circle.drawArc(0,0,400,400,0,angle,true);


        circle.setPaintColor("#E5E5E5");


        circle.drawCircle(200,200,100);


        circle.setPaintColor("#088000");

        circle.setPaintTextSize(49);


        let text=this.state.progress+"%";


        circle.drawText(text,0,text.length,200,200);

        return circle.createView();


    }

    getPathView(){

        let path=new Path().moveTo(100,100).arcTo(100,100,100,0,1*Math.PI,true).build();


        return  <Shape d={path}  stroke="#000000" strokeWidth={1}/>
    }



  render() {


      return  <View>

          <Surface width="300" height="300">

              {
                  this.getProgressView()
              }

      </Surface>

          <Button title="开始" onPress={this.start}/>

      </View>
  }
}



AppRegistry.registerComponent('chartest', () => chartest);
