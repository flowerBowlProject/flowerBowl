import React from 'react'
import "./Inputbutton.css"


const Inputbutton = ({text, i}) => {
  return (
    <>
    <button className={ i ? 'inputbutton' : 'outputbutton'} >
      {text}
    </button> 
    </>
);}
export default Inputbutton;

