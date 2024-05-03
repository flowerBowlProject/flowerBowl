import React from "react";
import "./App.css";
import { Header, Footer, MainHomepage } from "./MainPage/";
import {
  RecipeList,
  RegisterRecipe,
  RecipeDetail,
  ModifyRecipe,
} from "./Recipe/";
import {
  CommunityDetail,
  RegisterCommunity,
  CommunityList,
  ModifyCommunity,
} from "./Community/";
import { Routes, Route } from "react-router-dom";
import SearchList from "./Search/SearchList";
import { ClassDetail, RegisterClass, ModifyClass, ClassList } from "./Class/";
import Profilepage from "./Mypage/Profile/Profilepage";
import { AdmissionChef, RegisterBanner } from "./Mypage/Admin/";
import CheckClassList from "./Mypage/ApplyClass/CheckClassList";
import { BookmarkClass, BookmarkRecipe } from "./Mypage/Bookmark/";
import { Checkmakingclass, Checkteachingclass } from "./Mypage/Myclass/";
import Checkmakingrecipe from "./Mypage/Myrecipe/Checkmakingrecipe";
import { CheckPaidList, CheckReview, RegisterReview } from "./Mypage/PaidList/";
import Sidebar from "./Mypage/Sidebar";
import AdminSidebar from "./Mypage/AdminSidebar";
import BookmarkPage from "./Mypage/Bookmark/BookmarkPage";
import CheckClass from "./Mypage/Myclass/CheckClass";
import PaymentDetail from "./Mypage/PaidList/PaymentDetail";
import CategoryList from './Category/CategoryList';

const App = () => {
  return (
    <>
      <Header />
      <Routes>
      
        <Route path="/" element={<MainHomepage />} />
        <Route path="/categoryList" element={<CategoryList />} />
        <Route path="/recipeList" element={<RecipeList/>} />
        <Route path="/registerRecipe" element={<RegisterRecipe />} />
        <Route path="/modifyRecipe/:recipe_no" element={<ModifyRecipe />} />
        <Route path="/recipeDetail/:recipe_no" element={<RecipeDetail />} />
        <Route
          path="/communityDetail/:community_no"
          element={<CommunityDetail />}
        />
        <Route path="/registerCommunity" element={<RegisterCommunity />} />
        <Route
          path="/modifyCommunity/:community_no"
          element={<ModifyCommunity />}
        />
        <Route path="/communityList" element={<CommunityList />} />
        <Route path="/searchList/" element={<SearchList />} />
        <Route path="/classDetail/:lesson_no" element={<ClassDetail />} />
        <Route path="/modifyClass/:lesson_no" element={<ModifyClass />} />
        <Route path="/registerClass" element={<RegisterClass />} />
        <Route path="/classList" element={<ClassList/>}/>

        <Route path="/Mypage" element={<Sidebar />}>
          <Route path="profile" element={<Profilepage />} />
          <Route path="checkClassList" element={<CheckClassList />} />
          <Route path="bookmark" element={<BookmarkPage/>} >
            <Route path="class" element={<BookmarkClass />} />
            <Route path="recipe" element={<BookmarkRecipe />} />  
          </Route>
          <Route path="checkClass" element={<CheckClass/>}>
            <Route path="making" element={<Checkmakingclass />} />
            <Route path="teaching" element={<Checkteachingclass />} />
          </Route>
          
        <Route path="checkmakingrecipe" element={<Checkmakingrecipe />} />
          <Route path="paymentDetail" element={<PaymentDetail/>} >
            <Route path="checkPaidList" element={<CheckPaidList />} />
            <Route path="checkReview" element={<CheckReview />} />
            <Route path="registerReview" element={<RegisterReview />} />
          </Route>
        </Route>

        <Route path="/Admin" element={<AdminSidebar />}>
          <Route path="admissionChef" element={<AdmissionChef />} />
          <Route path="registerBanner" element={<RegisterBanner />} />
        </Route>
      </Routes>
      <Footer />
    </>
  );
};
export default App;
