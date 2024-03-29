import { configureStore } from '@reduxjs/toolkit'
import counterReducer from './CounterSlice'
import apple from './apple'
import storage from 'redux-persist/lib/storage';
import { combineReducers } from 'redux';
import {persistReducer} from 'redux-persist';

const reducers=combineReducers({
  counter: counterReducer,
  fruit: apple
});
const persistConfig={
  key: 'root',
  storage,
};
const persistedReducer=persistReducer(persistConfig,reducers);

export default configureStore({
  reducer: persistedReducer
})