import React, { useEffect, useState } from "react";
import "./RecipeDetailStyle.css";
import TurnedInNotIcon from "@mui/icons-material/TurnedInNot";
import TurnedInIcon from "@mui/icons-material/TurnedIn";
import Comment from "../Component/Comment/Comment";
import ButtonContain from "../Component/ButtonContain";
import { useDispatch, useSelector } from "react-redux";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { url } from "../url";
import ButtonOutlined from "../Component/ButtonOutlined";
import { Viewer } from "@toast-ui/react-editor";
import { editErrorType, openError } from "../persistStore";
import ErrorConfirm from "../Hook/ErrorConfirm";
import DeleteModal from "../Hook/DeleteModal";

const RecipeDetail = () => {
  const [recipeData, setRecipeData] = useState({});
  const navigator = useNavigate();
  const accessToken = useSelector((state) => state.accessToken);
  const writer = useSelector((state) => state.nickname);
  const { recipe_no } = useParams();
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response =
          accessToken === ""
            ? await axios.get(`${url}/api/recipes/guest/${recipe_no}`)
            : await axios.get(`${url}/api/recipes/${recipe_no}`, {
                headers: { Authorization: `Bearer ${accessToken}` },
              });
        setRecipeData(response.data.data);
      } catch (err) {
        console.log(err);
        dispatch(editErrorType(err.response.data.code));
        dispatch(openError());
      }
    };

    fetchData();
  }, [recipe_no, accessToken, dispatch]);

  const clickModify = () => {
    navigator(`/modifyRecipe/${recipe_no}`);
  };

  const clickDelete = () => {
    // Implement delete functionality here
  };

  const clickBookmark = async () => {
    if (accessToken === "") {
      dispatch(editErrorType("NT"));
      dispatch(openError());
    } else {
      try {
        const response = await axios.post(
          `${url}/api/recipes/like/${recipe_no}`,
          null,
          {
            headers: { Authorization: `Bearer ${accessToken}` },
          }
        );
        const check = response.data.code === "SU" ? false : true;
        setRecipeData((prevData) => ({
          ...prevData,
          recipe_like_status: check,
        }));
      } catch (err) {
        console.log(err);
        dispatch(editErrorType(err.response.data.code));
        dispatch(openError());
      }
    }
  };

  return (
    <>
      <div className="recipeDetail-Box">
        <ErrorConfirm error={useSelector((state) => state.errorType)} />
        <img
          className="recipe-Img"
          src={recipeData.recipe_sname}
          alt="Recipe"
        />
        <div className="recipe-element">
          <div style={{ float: "left", textAlign: "center" }}>
            <div className="recipe-title">{recipeData.recipe_title}</div>
            <div className="class-category" style={{ color: "#B9835C" }}>
              {" "}
              category : {recipeData.recipe_category}
            </div>
          </div>
          <div className="recipe-writerDate" style={{ textAlign: "right" }}>
            <div className="recipe-writer"> by {recipeData.recipe_writer} </div>
            <div className="recipe-date">{recipeData.recipe_date}</div>
          </div>
        </div>
        <div className="recipe-stuff">
          재료 : {recipeData.recipe_stuff && recipeData.recipe_stuff.join(", ")}
        </div>
        <div className="recipe-body">
          {recipeData.recipe_content && (
            <Viewer initialValue={recipeData.recipe_content} />
          )}
        </div>
        <div
          className="recipe-bookmark"
          onClick={clickBookmark}
          style={{ cursor: "pointer" }}
        >
          {recipeData.recipe_like_status ? (
            <TurnedInIcon sx={{ fontSize: "60px", color: "main.or" }} />
          ) : (
            <TurnedInNotIcon sx={{ fontSize: "60px", color: "main.or" }} />
          )}{" "}
          스크랩
        </div>
        <div className="recipe-change">
          {writer === recipeData.recipe_writer && (
            <ButtonOutlined
              size="large"
              text="수정"
              handleClick={clickModify}
            />
          )}
          {writer === recipeData.recipe_writer && (
            <DeleteModal checkType={"recipe"} no={recipe_no} />
          )}
        </div>
      </div>
      <div>
        <Comment typeString={1} no={recipe_no} />
      </div>
    </>
  );
};

export default RecipeDetail;
