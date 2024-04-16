package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.domain.*;
import com.flowerbowl.web.dto.object.recipe.*;
import com.flowerbowl.web.dto.request.recipe.CrRecipeReqDto;
import com.flowerbowl.web.dto.request.recipe.UpRecipeReqDto;
import com.flowerbowl.web.dto.response.recipe.*;
import com.flowerbowl.web.handler.RecipeNotFoundException;
import com.flowerbowl.web.handler.UserNotFoundException;
import com.flowerbowl.web.repository.*;
import com.flowerbowl.web.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeFileRepository recipeFileRepository;
    private final RecipeLikeRepository recipeLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? extends RecipeResponseDto> createRecipe(CrRecipeReqDto request) throws Exception {
        // 아직 token으로부터 사용자 정보를 얻어오는 부분이 없음
        try {
            User user = userRepository.findByUserNo(2L).orElseThrow(UserNotFoundException::new);

            // request의 값으로 recipe 생성
            CreateRecipeDto createRecipeDto = new CreateRecipeDto(
                    request.getRecipeTitle(),
                    LocalDate.now(ZoneId.of("Asia/Seoul")),
                    request.getRecipeStuff(),
                    request.getRecipeOname(),
                    request.getRecipeSname(),
                    request.getRecipeContent(),
                    request.getRecipeCategory(),
                    "홍길동",
                    0L,
                    user
            );

            // 생성된 recipe를 db에 저장후 객체 반환
            Recipe recipe = recipeRepository.save(createRecipeDto.toEntity());

            // request의 값으로 recipe file 생성 후 db에 저장
            CreateRecipeFileDto createRecipeFileDto = new CreateRecipeFileDto(request.getRecipeFileOname(), request.getRecipeFileSname(), recipe);
            recipeFileRepository.save(createRecipeFileDto.toEntity());

            CrRecipeSuResDto responseBody = new CrRecipeSuResDto(ResponseCode.CREATED, ResponseMessage.CREATED, recipe.getRecipeNo());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            CrRecipeFaResDto responseBody = new CrRecipeFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends RecipeResponseDto> updateRecipe(UpRecipeReqDto request, Long recipe_no) throws Exception {
        // 해당 레시피의 작성자가 token으로부터 얻은 사용자와 일치하는지 검증하는 코드 필요
        try {
            // 레시피 번호로 레시피 찾기
            Recipe recipe = recipeRepository.findByRecipeNo(recipe_no).orElseThrow(RecipeNotFoundException::new);
            // 레시피 번호로 레시피 파일 찾기
            RecipeFile recipeFile = recipeFileRepository.findByRecipe_RecipeNo(recipe_no);

            // request의 값이 비어있는지 체크가 필요할까...?고민 중
            // 찾은 레시피의 데이터을 수정
            recipe.updateTitle(request.getRecipeTitle());
            recipe.updateCategory(request.getRecipeCategory());
//            recipe.updateStuff(request.getRecipeStuff());
            recipe.updateContent(request.getRecipeContent());
            recipe.updateOname(request.getRecipeOname());
            recipe.updateSname(request.getRecipeSname());

            // 찾은 레시피 파일의 데이터를 수정
            recipeFile.updateFileOname(request.getRecipeFileOname());
            recipeFile.updateFileSname(request.getRecipeFileSname());

            // 수정된 레시피 데이터 저장
            Recipe result = recipeRepository.save(recipe);
            // 수정된 레시피 파일 데이터 저장
            recipeFileRepository.save(recipeFile);

            UpRecipeSuResDto responseBody = new UpRecipeSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, result.getRecipeNo());
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            UpRecipeFaResDto responseBody = new UpRecipeFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends RecipeResponseDto> deleteRecipe(Long recipe_no) throws Exception {
        // 해당 레시피의 작성자가 token으로부터 얻은 사용자와 일치하는지 검증하는 코드 필요
        try {
            Recipe recipe = recipeRepository.findByRecipeNo(recipe_no).orElseThrow(RecipeNotFoundException::new);
            recipeRepository.delete(recipe);

            DelRecipeResDto responseBody = new DelRecipeResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            DelRecipeResDto responseBody = new DelRecipeResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends RecipeResponseDto> getAllRecipesGuest() throws Exception {
        try {
            List<Recipe> recipes = recipeRepository.findAll();

            List<GetAllRecipesDto> posts = recipes.stream().map((recipe -> {
                Long recipeNo = recipe.getRecipeNo();

                List<RecipeLike> recipeLikes = recipeLikeRepository.findAllByRecipe_RecipeNo(recipeNo);
                List<Comment> comments = commentRepository.findAllByRecipe_RecipeNo(recipeNo);

                return GetAllRecipesDto.builder()
                        .recipeNo(recipe.getRecipeNo())
                        .recipeSname(recipe.getRecipeSname())
                        .recipeTitle(recipe.getRecipeTitle())
                        .recipeWriter(recipe.getRecipeWriter())
                        .recipeDate(recipe.getRecipeDate())
                        .recipeLikeCount(Long.valueOf(recipeLikes.size()))
                        .recipeCommentCount(Long.valueOf(comments.size()))
                        .recipeLikeStatus(false)
                        .build();
            })).toList();

            GetAllRecipesSuResDto responseBody = new GetAllRecipesSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, posts);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            GetAllRecipesFaResDto responseBody = new GetAllRecipesFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends RecipeResponseDto> getAllRecipes() throws Exception {
        // 아직 token으로부터 사용자 정보를 얻어오는 부분이 없음 user_no을 받아와야 함
        try {
            Long userNo = 1L;

            List<Recipe> recipes = recipeRepository.findAll();

            List<GetAllRecipesDto> posts = recipes.stream().map((recipe -> {
                Long recipeNo = recipe.getRecipeNo();

                List<RecipeLike> recipeLikes = recipeLikeRepository.findAllByRecipe_RecipeNo(recipeNo);
                List<Comment> comments = commentRepository.findAllByRecipe_RecipeNo(recipeNo);

                // recipeNo과 userNo으로 RecipeLike 테이블 조회
                Specification<RecipeLike> spec = (root, query, criteriaBuilder) -> null;
                if (Objects.nonNull(recipeNo) && Objects.nonNull(userNo)) {
                    spec = RecipeLikeSpecification.equalRecipeNo(recipeNo).and(RecipeLikeSpecification.equalUserNo(userNo));
                }

                return GetAllRecipesDto.builder()
                        .recipeNo(recipe.getRecipeNo())
                        .recipeSname(recipe.getRecipeSname())
                        .recipeTitle(recipe.getRecipeTitle())
                        .recipeWriter(recipe.getRecipeWriter())
                        .recipeDate(recipe.getRecipeDate())
                        .recipeLikeCount(Long.valueOf(recipeLikes.size()))
                        .recipeCommentCount(Long.valueOf(comments.size()))
                        // 다중 조건 검색에 따라 즐겨찾기 여부를 지정
                        .recipeLikeStatus(recipeLikeRepository.findOne(spec).isPresent())
                        .build();
            })).toList();

            GetAllRecipesSuResDto responseBody = new GetAllRecipesSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, posts);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            GetAllRecipesFaResDto responseBody = new GetAllRecipesFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends RecipeResponseDto> getRecipeGuest(Long recipe_no) throws Exception {
        try {
            Recipe recipe = recipeRepository.findByRecipeNo(recipe_no).orElseThrow(RecipeNotFoundException::new);
            RecipeFile recipeFile = recipeFileRepository.findByRecipe_RecipeNo(recipe_no);

            // 조회 수 증가
            recipe.updateView(recipe.getRecipeViews() + 1);
            recipeRepository.save(recipe);

            GetRecipeDto newDto = GetRecipeDto.builder()
                    .recipeNo(recipe.getRecipeNo())
                    .recipeOname(recipe.getRecipeOname())
                    .recipeSname(recipe.getRecipeSname())
                    .recipeFileOname(recipeFile.getRecipeFileOname())
                    .recipeFileSname(recipeFile.getRecipeFileSname())
                    .recipeTitle(recipe.getRecipeTitle())
                    .recipeWriter(recipe.getRecipeWriter())
                    .recipeDate(recipe.getRecipeDate())
                    .recipeStuff(recipe.getRecipeStuff())
                    .recipeCategory(recipe.getRecipeCategory())
                    .recipeContent(recipe.getRecipeContent())
                    .recipeLikeStatus(false)
                    .build();

            GetRecipeSuResDto responseBody = new GetRecipeSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS,newDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            GetRecipeFaResDto responseBody = new GetRecipeFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends RecipeResponseDto> getRecipe(Long recipe_no) throws Exception {
        // 아직 token으로부터 사용자 정보를 얻어오는 부분이 없음 user_no을 받아와야 함
        try {
            Long userNo = 1L;

            Recipe recipe = recipeRepository.findByRecipeNo(recipe_no).orElseThrow(RecipeNotFoundException::new);
            RecipeFile recipeFile = recipeFileRepository.findByRecipe_RecipeNo(recipe_no);

            // 조회 수 증가
            recipe.updateView(recipe.getRecipeViews() + 1);
            recipeRepository.save(recipe);

            Long recipeNo = recipe.getRecipeNo();

            Specification<RecipeLike> spec = (root, query, criteriaBuilder) -> null;
            if (Objects.nonNull(recipeNo) && Objects.nonNull(userNo)) {
                spec = RecipeLikeSpecification.equalRecipeNo(recipeNo).and(RecipeLikeSpecification.equalUserNo(userNo));
            }

            GetRecipeDto newDto = GetRecipeDto.builder()
                    .recipeNo(recipeNo)
                    .recipeOname(recipe.getRecipeOname())
                    .recipeSname(recipe.getRecipeSname())
                    .recipeFileOname(recipeFile.getRecipeFileOname())
                    .recipeFileSname(recipeFile.getRecipeFileSname())
                    .recipeTitle(recipe.getRecipeTitle())
                    .recipeWriter(recipe.getRecipeWriter())
                    .recipeDate(recipe.getRecipeDate())
                    .recipeStuff(recipe.getRecipeStuff())
                    .recipeCategory(recipe.getRecipeCategory())
                    .recipeContent(recipe.getRecipeContent())
                    // 다중 조건 검색에 따라 즐겨찾기 여부를 지정
                    .recipeLikeStatus(recipeLikeRepository.findOne(spec).isPresent())
                    .build();

            GetRecipeSuResDto responseBody = new GetRecipeSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS,newDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            GetRecipeFaResDto responseBody = new GetRecipeFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends RecipeResponseDto> updateRecipeLike(Long recipe_no) throws Exception {
        // 아직 token으로부터 사용자 정보를 얻어오는 부분이 없음 user_no을 받아와야 함
        try {
            Long userNo = 1L;

            Specification<RecipeLike> spec = (root, query, criteriaBuilder) -> null;
            if (Objects.nonNull(recipe_no) && Objects.nonNull(userNo)) {
                spec = RecipeLikeSpecification.equalRecipeNo(recipe_no).and(RecipeLikeSpecification.equalUserNo(userNo));
            }

            // recipe_like 테이블에 데이터가 있다면 그 데이터를 없다면 Optional.empty를 반환
            Optional<RecipeLike> recipeLike = recipeLikeRepository.findOne(spec);

            if (recipeLike.isPresent()) {
                // 이미 즐겨찾기가 되어있으므로 즐겨찾기 해제(삭제)
                recipeLikeRepository.delete(recipeLike.get());

                DelRecipeLikeResDto responseBody = new DelRecipeLikeResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            }
            // 즐겨찾기가 되어있지 않으므로 즐겨찾기 등록(생성)
            User user = userRepository.findByUserNo(userNo).orElseThrow(UserNotFoundException::new);
            Recipe recipe = recipeRepository.findByRecipeNo(recipe_no).orElseThrow(RecipeNotFoundException::new);

            CreateRecipeLikeDto createRecipeLikeDto = new CreateRecipeLikeDto(recipe, user);

            RecipeLike savedRecipeLike = recipeLikeRepository.save(createRecipeLikeDto.toEntity());

            UpRecipeLikeSuResDto responseBody = new UpRecipeLikeSuResDto(ResponseCode.CREATED, ResponseMessage.CREATED, savedRecipeLike.getRecipeLikeNo());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (Exception e) {
            log.error("Exception [Err_Msg]: {}", e.getMessage());
            log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);

            UpRecipeLikeFaResDto responseBody = new UpRecipeLikeFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

}
