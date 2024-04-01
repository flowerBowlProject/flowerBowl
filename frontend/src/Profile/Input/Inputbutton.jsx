import React from 'react'
import "./Inputbutton.css"


const Inputbutton = ({text, i, w}) => {
  return (
    <>
    <button className= {`${w} ${i ? 'inputbutton' : 'outputbutton'}`} >
      {text}
    </button> 
    </>
);}
export default Inputbutton;