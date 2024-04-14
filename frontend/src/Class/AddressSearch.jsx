import React, { useEffect, useState } from "react";
import axios from "axios";
import "./RegisterClassStyle.css";
import { Button, TextField } from "@mui/material";
import Inputbutton from "../Component/Input/Inputbutton";
import { address_key } from "../config";

const AddressSearch = (props) => {
  const [address, setAddress] = useState({
    lesson_address: "",
    lesson_longitude: 0.0,
    lesson_latitude: 0.0,
  });

  const handleAddressClick = () => {
    new window.daum.Postcode({
      oncomplete: (data) => {
        document.getElementById("main_address").value = data.address;
        setAddress((address) => ({ ...address, lesson_address: data.address }));
      },
    }).open();
  };

  const searchAddress = () => {
    console.log(address.lesson_address);
    axios
      .get("https://dapi.kakao.com/v2/local/search/address.json", {
        params: {
          query: address.lesson_address,
        },
        headers: {
          Authorization: "KakaoAK " + address_key,
        },
      })
      .then((res) => {
        const data = res.data.documents[0];
        setAddress((address) => ({
          ...address,
          lesson_longitude: data.address.x,
        }));
        setAddress((address) => ({
          ...address,
          lesson_latitude: data.address.y,
        }));

        props.getAddress(address);
      })
      .catch((err) => {
        console.log(err);
        {
          /* 주소 오류 관련 alert창 띄우기 */
        }
      });
  };

  useEffect(() => {
    document
      .getElementById("main_address")
      .addEventListener("click", handleAddressClick);

    return () => {
      document
        .getElementById("main_address")
        .removeEventListener("click", handleAddressClick);
    };
  }, []);

  return (
    <div>
      <TextField
        className="main_address"
        id="main_address"
        type="text"
        readOnly
        sx={{ marginRight: "0.5vw" }}
        placeholder="클릭해 주소를 입력하세요."
        onChange={(newValue) =>
          setAddress((address) => ({ ...address, lesson_address: newValue }))
        }
      />
      <Button
        className="address_register"
        variant="outlined"
        onClick={searchAddress}
      >
        {" "}
        주소 등록{" "}
      </Button>
    </div>
  );
};

export default AddressSearch;
