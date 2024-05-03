import { configureStore, current } from "@reduxjs/toolkit";
import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";

export const OPENERROR= "OPEN/ERROR"
export const openError=()=>({type:OPENERROR,payload:true});
export const CLOSEERROR= "CLOSE/ERROR"
export const closeError=()=>({type:CLOSEERROR,payload:false});
export const ERRORTYPE= "TYPE/ERRORTYPE"
export const editErrorType=(data)=>({type:ERRORTYPE,payload:data});
export const SHOWDUPLICATIONBOOLEAN="SHOW/DUPLICATIONBOOLEAN"
export const ShowDuplication=(name)=>({type:SHOWDUPLICATIONBOOLEAN,payload:name});
export const HIDEDUPLICATIONBOOLEAN="HIDE/DUPLICATIONBOOLEAN"
export const HideDuplication=(name)=>({type:HIDEDUPLICATIONBOOLEAN,payload:name});

export const OPENEMAILCHECK='OPEN/EMAILCHECK'
export const openEmailCheck=()=>({type:OPENEMAILCHECK,payload:true});
export const CLOSEEMAILCHECK='CLOSE/EMAILCHECK'
export const closeEmailCheck=()=>({type:CLOSEEMAILCHECK,payload:false});

export const SETMEMBERID="SET/MEMBERID"
export const setMemberid=(id)=>({type:SETMEMBERID,payload:id});
export const SETMEMBEREMAIL='SET/MEMBEREMAIL'
export const setMermberEmail=(email)=>({type:SETMEMBEREMAIL,payload:email});
export const SETMEMBERTEL='SET/MEMBERTEL'
export const setMemberTel=(tel)=>({type:SETMEMBERTEL,payload:tel});
export const SETMEMBERPW='SET/MEMBERPW'
export const setMemberPw=(pw)=>({type:SETMEMBERPW,payload:pw});
export const SETMEMBERNAME='SET/MEMBERNAME'
export const setMemberName=(name)=>({type:SETMEMBERNAME,payload:name});
export const SETMEMBERNEWPW='SET/MEMBERNEWPW'
export const setMemberNewPw=(newPw)=>({type:SETMEMBERNEWPW,payload:newPw});

export const initialState = {
  member: { memberId:"",memberEmail:"",memberTel:'',memberPw:'',memberName:'',memberNewPw:''},
  accessToken: "",
  error:false,
  errorType:"",
  duplicationText:{id:'중복된 아이디입니다.',name:'중복된 닉네임입니다.' },
  duplicationBoolean:{id:false,name:false},
  emailCheck:false,
  nickname:""
};

const reducer = (currentState, action) => {
  if (currentState === undefined) {
    return initialState;
  }
  const newState = { ...currentState };
  switch (action.type) {
    case "member":
      newState.member = action.payload;
      break;
    case "accessToken":
      newState.accessToken = action.payload;
      break;
    case OPENERROR:
    case CLOSEERROR:
      newState.error = action.payload;
      break;
    case ERRORTYPE:
      newState.errorType=action.payload;
      break;
    case SHOWDUPLICATIONBOOLEAN:
      newState.duplicationBoolean={
        ...newState.duplicationBoolean,
        [action.payload]:true
      }
      break;
    case HIDEDUPLICATIONBOOLEAN:
      newState.duplicationBoolean={
        ...newState.duplicationBoolean,     
        [action.payload]:false
      }
      break;
    case SETMEMBERID:
      newState.member={
        ...newState.member,
        memberId:action.payload
      }
      break;
    case OPENEMAILCHECK:
    case CLOSEEMAILCHECK:
      newState.emailCheck=action.payload;
      break;
    case SETMEMBEREMAIL:
      newState.member={
        ...newState.member,
        memberEmail:action.payload
      }
      break;
    case SETMEMBERTEL:
      newState.member={
        ...newState.member,
        memberTel:action.payload
      }
      break;
    case SETMEMBERPW:
      newState.member={
        ...newState.member,
        memberPw:action.payload
      }
      break;
    case SETMEMBERNAME:
      newState.member={
        ...newState.member,
        memberName:action.payload
      }
      break;
    case SETMEMBERNEWPW:
      newState.member={
        ...newState.member,
        memberNewPw:action.payload
      }
    case "nickname":
      newState.nickname = action.payload;
      break;
    default:
  }
  return newState;
};
const persistConfig = {
  key: "root",
  storage,
  blacklist: ['error','errorType','duplicationText','duplicationBoolean','emailCheck','member']
};
const persistedReducer = persistReducer(persistConfig, reducer);
const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});

export default store;
