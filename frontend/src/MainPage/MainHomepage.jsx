import React, { useEffect } from 'react'
import PopularRecipe from './PopularRecipe'
import CustomRecipe from './CustomRecipe';
import MenuRecipe from './MenuRecipe';
import PopularClass from './PopularClass';
import Advertisement from './Advertisement';
import ErrorConfirm from '../Hook/ErrorConfirm';
import { editErrorType, closeError } from '../persistStore';
import { useDispatch, useSelector } from 'react-redux';
const MainHomepage =()=> {
  const dispatch = useDispatch();
  const errorType = useSelector(state=>state.errorType);
  console.log(errorType)


  useEffect(()=>{
    if(errorType === 'ET'){
      setTimeout(() => {
        dispatch(closeError());
        setTimeout(()=>{
          dispatch(editErrorType(''));
        }, 100)

      }, 2000);
    }
  },[])

  return (  
    <>
    <CustomRecipe/>
    <PopularRecipe/>
    <MenuRecipe/>
    <PopularClass/>
    <Advertisement/>
    <ErrorConfirm error={useSelector(state=>state.errorType)}/>
    </> 
  );
}
export default MainHomepage;
