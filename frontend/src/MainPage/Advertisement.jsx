import React, { useEffect ,useState} from 'react'; 
import {Grid,Typography,Skeleton} from '@mui/material';
import { url } from '../url';
import axios from 'axios';
const Advertisement=()=>{
    const [banner,setBanner]=useState();

    useEffect(()=>{
        const fetchData=async()=>{
            try{
                const response=await axios.get(`${url}/api/banners`)
                setBanner(response.data.banner_sname)
            }catch(error){
                console.log(error)
            }

        }
        fetchData();
    },[]);

    return(
        <Grid container>
            {banner?<img src={banner} style={{width:'100vw' ,height:'30vw',marginTop:'17vw',marginBottom:'10vw'}} />:<Skeleton width='100vw' height='70vw' /> }
            
            
        </Grid>
    );
}
export default Advertisement;