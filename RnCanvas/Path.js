
//Path增强类
import {
    ART
} from 'react-native';


const Base = ART.Path;

const Method={
     MOVE_TO :0,
 CLOSE : 1,
 LINE_TO :2,
 CURVE_TO : 3,
 ARC :4

}

class Path{

    constructor(){

        this.base=new Base();
    }

    moveTo(x,y){
        this.base.moveTo(x,y);

        return this;
    }

    close(){
        this.base.close();

        return this;
    }

    lineTo(x,y){

        this.base.lineTo(x,y);

        return this;
    }


    arcTo( x,  y,  r,  start,  end,
           ccw){
        this.base.path.push(Method.ARC, x, y, r, start, end, ccw ? 0 : 1);

        return this;
    }

    build()
    {
        return this.base;
    }

}

module.exports=Path