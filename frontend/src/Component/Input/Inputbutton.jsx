import React from 'react'
import "./Inputbutton.css"


const Inputbutton = ({text, i, w, onClick}) => {

  const handleClick = () => {
    if (onClick) {
      onClick();
    }
  };

  return (
    <>
    <button className= {`${w} ${i ? 'inputbutton' : 'outputbutton'}`} onClick={handleClick}>
      {text}
    </button> 
    </>
);}
export default Inputbutton;