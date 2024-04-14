import ButtonContainStyle from "./ButtonContainStyle";

const ButtonContain = ({text,size,handleClick})=>{
    const handleSize=(size)=>{
        if(size==='verySmall'){
            return{
                width:'2vw',
                height:'1vw',
                size:'small'
            };
        }   
        else if(size==='small'){
            return{
                width:'5vw',
                height:'1vw',
                size:'small'               
            };

        }else if(size==='medium'){
            return{
                width:'5vw',
                height:'1.5vw'
            };

        }else if(size==='large'){
            return{
                width:'5vw',
                height:'2vw'                
            };

        }else if(size==='veryLarge'){
            return{
                width:'15vw'
            };

        }

    }
    const sizeStyle=handleSize(size)
    return(
        <ButtonContainStyle onClick={handleClick} width={sizeStyle.width} variant='contained' size={sizeStyle.size} sx={{height:sizeStyle.height}} >
            {text}
        </ButtonContainStyle>
    )
}
export default ButtonContain;