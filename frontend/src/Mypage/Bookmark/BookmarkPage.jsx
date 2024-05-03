import { Outlet,Link ,useNavigate} from "react-router-dom"
import ButtonContain from "../../Component/ButtonContain"
import ButtonOutlined from "../../Component/ButtonOutlined"
import { Grid } from "@mui/material"
import { useState,useEffect } from "react"
const BookmarkPage=()=>{
    const navigate=useNavigate();
    const [buttonClick,setButtonClick]=useState([false,false]);
    const handleClickRecipe=()=>{
        setButtonClick([true,false])
    }
    const handleClickClass=()=>{
        setButtonClick([false,true])
    }
    useEffect(()=>{
        handleClickRecipe();
        navigate('/mypage/bookmark/recipe')
    },[])
    return(
    <Grid container direction='row' mt='3vw'>
        <Grid item xs={0.9}>
            <Link to="/mypage/bookmark/recipe" >
                {buttonClick[0]?(<ButtonContain size="medium" text="레시피" handleClick={handleClickRecipe}/>):(<ButtonOutlined size="medium" text="레시피" handleClick={handleClickRecipe}/>)}
            </Link>
        </Grid>
        <Grid item xs={0.9}>
            <Link to="/mypage/bookmark/class">
                {buttonClick[1]?(<ButtonContain size="medium" text="클래스" handleClick={handleClickClass}/>):(<ButtonOutlined size="medium" text="클래스" handleClick={handleClickClass}/>)}
            </Link>
        </Grid>
        <Grid item xs={12} mt='1vw'>
            <Outlet/>
        </Grid>
    
    </Grid>
    )
}
export default BookmarkPage;