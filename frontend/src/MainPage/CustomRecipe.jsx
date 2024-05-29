import React, { useEffect,useState } from 'react'
import {Grid,Typography,Skeleton,Pagination } from '@mui/material';
import axios from 'axios';
import { url } from '../url';
import "./CustomRecipe.css";
import { Viewer } from "@toast-ui/react-editor";
import { useNavigate } from "react-router-dom";
const  CustomRecipe=()=>{
    const [adminData,setAdminData]=useState(Array(5).fill({ recipe_title: '', recipe_content: '' ,recipe_no:'',recipe_sname:''}));
    const [number,setNumber]=useState(0);
    const navigator = useNavigate();
    const handleMoveLink = () => {
        navigator(`/recipeDetail/${adminData[number].recipe_no}`);
      };
    useEffect(()=>{
        const dot=document.querySelectorAll('.dot')
        dot[0].focus()
        
        const fetchData=async()=>{
            try{
                const response=await axios.get(`${url}/api/recipes/admin`)
                setAdminData(response.data.posts)
                if(response.data.posts.length<5){
                    setAdminData(data=>[...data,...Array(5).fill({ recipe_title: '', recipe_content: '',recipe_no:'',recipe_sname:'' })]
                    
                    )
                }
                console.log(typeof response.data.posts.recipe_content)
            }catch(error){
                console.log(error.response.data)
            }
        }
        fetchData();
       
    },[])
    const handleClick=(event)=>{
        const name=event.currentTarget.id;
        switch(name){
            case 'button1':
                setNumber(0)
                break;
            case 'button2':
                setNumber(1)
                break;
            case 'button3':
                setNumber(2)
                break;
            case 'button4':
                setNumber(3)
                break;
            case 'button5':
                setNumber(4)
                break;

        }
        
    }
    
    return(
        <Grid container sx={{backgroundColor:'main.br'}} pt='5vw' >
            <Grid xs={5.1} item mt='3vw' ml='3vw'>
                <Typography  variant='h4' mb='3vw' >  
                    {adminData?(adminData[number].recipe_title.length >= 20 ? adminData[number].recipe_title.substr(0, 19)+'...0' : adminData[number].recipe_title):null}
                </Typography>
                    {adminData[number].recipe_content?(adminData[number].recipe_content.length>=150 ? <Viewer initialValue={adminData[number].recipe_content.substr(0, 149)+'...'} /> 
                        : <Viewer initialValue={adminData[number].recipe_content} />):null}
            </Grid>
            <Grid xs={6.5}   item container direction='column' alignItems='center'>
                <Grid item>
                {adminData[number].recipe_sname ? (
                <img src={adminData[number].recipe_sname} alt="Image" style={{ width: '40vw', height: '20vw', cursor: 'pointer'}} onClick={handleMoveLink} />
                ) : (
                <Skeleton variant="rectangular" width="40vw" height="20vw" />
                )}
                </Grid>
                <Grid item my='2vw'>
                        <div class="pager">
                            <button id='button1' className='dot' onClick={handleClick}><span></span></button>
                            <button id='button2' className='dot' onClick={handleClick}><span></span></button>
                            <button id='button3' className='dot' onClick={handleClick}><span></span></button>
                            <button id='button4' className='dot' onClick={handleClick}><span></span></button>
                            <button id='button5' className='dot' onClick={handleClick}><span></span></button>
                        </div>
                </Grid>
            </Grid>
        </Grid>
    
    );
}
export default CustomRecipe;

