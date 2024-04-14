import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import store from './persistStore';
import { Provider } from 'react-redux'
import { theme } from "./theme";
import { ThemeProvider } from '@mui/material/styles';
import {BrowserRouter} from 'react-router-dom'
import {PersistGate} from 'redux-persist/integration/react';
import {persistStore} from 'redux-persist';

export const persistor = persistStore(store);
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <Provider store={store}>
    <BrowserRouter>
    <PersistGate loading={null} persistor={persistor}>
    <ThemeProvider theme={theme}>
        <App />
      </ThemeProvider>
    </PersistGate>
    </BrowserRouter>
  </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
