import React,{useState} from "react";

export const validation=(value,type)=>{
        switch(type){
            case 'id':{ if(typeof value === 'string')return value.length<8 || value.length>15;    }
            case 'pw': 
            case 'newPw':
                return !/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[a-zA-Z\d@$!%*?&]{8,15}$/.test(value);
            case 'email': return !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
            case 'tel': return !/^[0-9]{11}$/.test(value);
            case 'name' : return !/^[a-zA-Z0-9가-힣]{2,10}$/.test(value);
            
            default: return false;
        }

    }
export const validationPassConfirm=(value_1,value_2)=>{
    console.log({value1:value_1, value2:value_2})
    return !(value_1===value_2);
}


    





