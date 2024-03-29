import React from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { increment,decrement,incrementByAmount} from '../Example/CounterSlice'
import { ChangeByName} from '../Example/apple'
const Header =()=>{
    const name= useSelector(state=>state.fruit.name)
    const dispatch=useDispatch()
    return(
        <div>
            <div className='text-gr font-ft text-8xl font-bd'>
                {name}
                <hr/>
                <button onClick={()=>dispatch(ChangeByName('kakao'))}>
                    asdf
                </button>
            </div>    
            <li>
                <ul  className='text-gr font-ft text-4xl'>레시피 </ul>
                <ul  className='text-gr font-ft text-4xl'>클래스</ul> 
                <ul  className='text-gr font-ft text-4xl'>커뮤니티</ul>
            </li>
            <div>

            </div>
            <div>
                
            </div>
        </div>
    )
}
export default Header;