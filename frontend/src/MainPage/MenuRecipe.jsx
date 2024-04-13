import React from 'react';
import {Grid,Typography,Avatar } from '@mui/material';
import {useState} from 'react';
const Menu=({menuName})=>{
    return(
        <Grid xs={12} container columnGap='2vw' direction='row'>
               {menuName.map((item,idx)=>(
                    <Grid xs key={idx} container direction='column' alignContent='center'>
                        <Grid  xs  item ml='1.2vw'>
                            <Avatar src={`../../images/${item[1]}.png`} />
                        </Grid>
                        <Grid item xs>
                            <Typography align='center'  width='4.6vw'>{item[0]}</Typography>
                        </Grid>
                    </Grid>
               ))}
        </Grid>    
        
    );
}
const MenuRecipe=()=>{
    const [menuName,setMenuName]=useState([
        ['콩/견과류','jelly-beans'],
        ['김치/젓갈','kimchi'],
        ['튀김류','fried_potatoes'],
        ['면류','noodles'],
        ['퓨전','salad'],
        ['고기류','meat']
    ]);
    const [menuName_2,setMenuName_2]=useState([
        ['디저트','dessert'],
        ['찌개&국','stew'],
        ['밥','rice'],
        ['음료/술','drink'],
        ['기타류','chef'],
        ['과일류','fruits'],
    ]);
    return(
        <Grid container sx={{backgroundColor:'main.or'}}>
            <Grid xs={12} container columnGap='2vw' direction='row' mt='4vw'>
                <Menu menuName={menuName}/>
            </Grid>
            <Grid xs={12} container columnGap='2vw' direction='row' my='4vw'>
                <Menu menuName={menuName_2}/>
            </Grid>
        </Grid>
        
    );
}
export default MenuRecipe;
