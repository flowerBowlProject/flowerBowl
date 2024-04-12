import React from 'react'
import './App.css';
import { Header, Footer, MainHomepage } from './MainPage/';
import { ViewList, RegisterRecipe, RecipeDetail } from './Recipe/';
import { CommunityDetail, RegisterCommunity, CommunityList, ModifyCommunity } from './Community/';
import { Routes, Route } from 'react-router-dom';
import SearchList from './Search/SearchList';
import { ClassDetail, RegisterClass, ModifyClass } from './Class/'
import Profile from './Mypage/Profile/Profile'
import { AdmissionChef, RegisterBanner } from './Mypage/Admin/'
import CheckClassList from './Mypage/ApplyClass/CheckClassList';
import { BookmarkClass, BookmarkRecipe } from './Mypage/Bookmark/';
import { Checkmakingclass, Checkteachingclass } from './Mypage/Myclass/';
import Checkmakingrecipe from './Mypage/Myrecipe/Checkmakingrecipe';
import { CheckPaidList, CheckReview, RegisterReview } from './Mypage/PaidList/';
const App = () => {
  return (
    <>
      <Header />
      <Routes>
        <Route path='/' element={<MainHomepage />} />
        <Route path='/viewList' element={<ViewList />} />
        <Route path='/registerRecipe' element={<RegisterRecipe />} />
        <Route path='/recipeDetail' element={<RecipeDetail />} />
        <Route path='/communityDetail/:community_no' element={<CommunityDetail />} />
        <Route path='/registerCommunity' element={<RegisterCommunity />} />
        <Route path='/modifyCommunity/:community_no' element={<ModifyCommunity />} />
        <Route path='/communityList' element={<CommunityList />} />
        <Route path='/searchList' element={<SearchList />} />
        <Route path='/classDetail/:lesson_no' element={<ClassDetail />} />
        <Route path='/modifyClass/:lesson_no' element={<ModifyClass />} />
        <Route path='/registerClass' element={<RegisterClass />} />
        <Route path='/profile' element={<Profile />} />
        <Route path='/admissionChef' element={<AdmissionChef />} />
        <Route path='/registerBanner' element={<RegisterBanner />} />
        <Route path='/checkClassList' element={<CheckClassList />} />
        <Route path='/bookmarkClass' element={<BookmarkClass />} />
        <Route path='/bookmarkRecipe' element={<BookmarkRecipe />} />
        <Route path='/checkmakingclass' element={<Checkmakingclass />} />
        <Route path='/checkteachingclass' element={<Checkteachingclass />} />
        <Route path='/checkmakingrecipe' element={<Checkmakingrecipe />} />
        <Route path='/checkPaidList' element={<CheckPaidList />} />
        <Route path='/checkReview' element={<CheckReview />} />
        <Route path='/registerReview' element={<RegisterReview />} />
    </Routes >
      <Footer />
    </>
  );
};
export default App;
