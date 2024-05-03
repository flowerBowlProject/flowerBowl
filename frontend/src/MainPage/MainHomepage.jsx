import React from 'react'
import PopularRecipe from './PopularRecipe'
import CustomRecipe from './CustomRecipe';
import MenuRecipe from './MenuRecipe';
import PopularClass from './PopularClass';
import Advertisement from './Advertisement';
import ErrorConfirm from '../Hook/ErrorConfirm';
import { useSelector } from 'react-redux';
const MainHomepage =()=> {
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
