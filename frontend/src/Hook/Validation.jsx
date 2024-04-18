import React,{useState} from "react";

export const validation=(value,type)=>{
        switch(type){
            case 'id':{ return value.length<8 || value.length>15;    }
            case 'pw': return !/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[a-zA-Z\d@$!%*?&]{8,15}$/.test(value);
            case 'email': return !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value);
            case 'tel': return !/^\d{3}-\d{3,4}-\d{4}$/.test(value);
            
            default: return true;
        }

    }
export const validationPassConfirm=(value_1,value_2)=>{
    console.log({value1:value_1, value2:value_2})
    return !(value_1===value_2);
}


    





