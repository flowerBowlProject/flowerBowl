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
        ['손님상','customers_face'],
        ['아이반찬','child'],
        ['김치장아찌','kimchi'],
        ['도시락','lunchbox'],
        ['튀김','fried_potatoes'],
        ['면요리','noodles'],
        ['샐러드','salad'],
        ['베이킹','bread']
    ]);
    const [menuName_2,setMenuName_2]=useState([
        ['디저트','dessert'],
        ['주스&음료수','drink'],
        ['술&칵테일','cocktail'],
        ['명절요리','jeon'],
        ['기타요리','chef'],
        ['밥요리','rice'],
        ['국&탕','hot_soup'],
        ['찌개&전골','stew']
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
