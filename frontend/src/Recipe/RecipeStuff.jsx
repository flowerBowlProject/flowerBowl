import React, { useEffect, useState } from "react";
import './RecipeStuffStyle.css';

const RecipeStuff = (props) => {
    const initialTags = [];

    const [tags, setTags] = useState(initialTags);

    const removeTags = (indexToRemove) => {
      const filter = tags.filter((el,index) => index !== indexToRemove);
      setTags(filter);
      
    };
  
    const addTags = (event) => {
      const inputVal = event.target.value;

      if(event.key === "Enter" && inputVal !== '' && !tags.includes(inputVal)){
        setTags([...tags,inputVal]);
         event.target.value = '';
      }
    };

    useEffect(() => {
        props.getStuff(tags);
    }, [tags]);


    return (
        <>
        <ul id="tags">
          {tags.map((tag, index) => (
            <li key={index} className="tag">
              <span className="tag-title">{tag}</span>
              <span className="tag-close-icon" onClick={ () => removeTags(index)}>
              x</span>
            </li>
          ))}
          <input className="tagInput" type='text' placeholder="#재료 등록"
            onKeyUp={(e) => {
                { addTags(e)
                }
              }}
            style={{ border:'none'}}/>
        </ul>
        
      </>
        
    );

}

export default RecipeStuff;