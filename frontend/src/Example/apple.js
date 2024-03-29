import { createSlice } from '@reduxjs/toolkit'

export const apple= createSlice({
    name:'apple',
    initialState:{
        name: 'banana'
    },
    reducers:{
        ChangeByName: (state,action)=>{
            state.name=action.payload
            
        }
    }
})
export const {ChangeByName} = apple.actions

export default apple.reducer