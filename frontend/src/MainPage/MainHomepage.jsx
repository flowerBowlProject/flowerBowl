import React from 'react'
import PopularRecipe from './PopularRecipe'
import CustomRecipe from './CustomRecipe';
import MenuRecipe from './MenuRecipe';
import PopularClass from './PopularClass';
import Advertisement from './Advertisement';
const MainHomepage =()=> {
  return (  
    <>
    <CustomRecipe/>
    <PopularRecipe/>
    <MenuRecipe/>
    <PopularClass/>
    <Advertisement/>
    </> 
  );
}
export default MainHomepage;
