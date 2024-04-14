import React, { useState } from "react";
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';


const Category = (props) => {

  const [category, setCategory] = useState(props.category);
  const handleChange = (e) => {
    setCategory(e.target.value);
    props.getCategory(e.target.value);
  };

  return (
    <Box sx={{ width: "15vw", backgroundColor: "main.yl", borderColor: "main.br" }}>
      <FormControl fullWidth>
        <InputLabel sx={{
          '&.MuiInputLabel-root': {
            color: 'main.br', // 라벨 초기 글씨색
          },
          '&.MuiInputLabel-root.Mui-focused': {
            color: 'main.or', // 선택 시 라벨 글씨색
          },
        }}>Category
        </InputLabel>
        <Select
          value={category}
          label="category"
          onChange={handleChange}
          sx={{
            '&.MuiOutlinedInput-root': {
              '& fieldset': {
                borderColor: 'main.br', // 초기 테두리 색
              },
              '&:hover fieldset': {
                borderColor: 'main.br', // 호버 시 테두리 색
              },
              '&.Mui-focused fieldset': {
                borderColor: 'main.or', // 클릭 시 테두리 색
              },
            },
          }}
        >
          <MenuItem value={"디저트"}>디저트</MenuItem>
          <MenuItem value={"밥"}>밥</MenuItem>
          <MenuItem value={"찌개/국"}>찌개/국</MenuItem>
          <MenuItem value={"고기류"}>고기류</MenuItem>
          <MenuItem value={"기타류"}>기타류</MenuItem>
          <MenuItem value={"과일류"}>과일류</MenuItem>
          <MenuItem value={"콩/견과류"}>콩/견과류</MenuItem>
          <MenuItem value={"면류"}>면류</MenuItem>
          <MenuItem value={"김치/젓갈"}>김치/젓갈</MenuItem>
          <MenuItem value={"퓨전"}>퓨전</MenuItem>
          <MenuItem value={"튀김류"}>튀김류</MenuItem>
          <MenuItem value={"음료/술"}>음료/술</MenuItem>

        </Select>
      </FormControl>
    </Box>
  );
}

export default Category;