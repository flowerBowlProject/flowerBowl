import React from 'react'
import './App.css';
import CommonModal from './LoginModal/CommonModal';
import Header from './MainPage/Header';
import Footer from './MainPage/Footer';
import MainHomepage from './MainPage/MainHomepage';
import {TextField} from '@mui/material'
const App =()=> {
  return (  
    <>
    <Header/>
    <MainHomepage/>
    <Footer/> 
   
    </>
  );
}
export default App;
