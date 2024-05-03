package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.domain.*;
import com.flowerbowl.web.dto.object.recipe.*;
import com.flowerbowl.web.dto.request.recipe.CrRecipeReqDto;
import com.flowerbowl.web.dto.request.recipe.UpRecipeReqDto;
import com.flowerbowl.web.dto.response.recipe.*;
import com.flowerbowl.web.handler.CategoryNotFoundException;
import com.flowerbowl.web.handler.DoesNotMatchException;
import com.flowerbowl.web.handler.RecipeNotFoundException;
import com.flowerbowl.web.handler.UserNotFoundException;
import com.flowerbowl.web.repository.*;
import com.flowerbowl.web.service.ImageService;
import com.flowerbowl.web.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
    private final ImageService imageService;

    @Override
    public ResponseEntity<? extends RecipeResponseDto> createRecipe(CrRecipeReqDto request, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }

            String requestRecipeOname = request.getRecipe_oname();
            if (requestRecipeOname.trim().isEmpty()) {
                requestRecipeOname = null;
            }
            String requestRecipeSname = request.getRecipe_sname();
            if (requestRecipeSname.trim().isEmpty()) {
                requestRecipeSname = null;
            }

            if (requestRecipeOname != null && requestRecipeSname != null) {
                // requestRecipeOname => temp/thumbnail/파일명
                // newRecipeOname => recipeThumbnail/파일명
                String newRecipeOname = "recipeThumbnail/" + requestRecipeOname.split("/")[2];

                // temp/thumbnail에 있는 파일을 recipeThumbnail로 복사
                imageService.copyS3(requestRecipeOname, newRecipeOname);

                // DB에 저장할 requestRecipeOname과 requestRecipeSname을 새로운 이미지에 대한 정보로 변경
                requestRecipeOname = newRecipeOname;
                requestRecipeSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newRecipeOname;
            }

            List<String> fileOname = null;
            List<String> fileSname = null;
            String content = request.getRecipe_content();

            // request의 recipe_file_oname과 recipe_file_sname이 null이 아닐 때 해당 값으로 recipe file 생성 후 DB에 저장
            if (!CollectionUtils.isEmpty(request.getRecipe_file_oname()) && !CollectionUtils.isEmpty(request.getRecipe_file_sname())) {
                fileOname = new ArrayList<>();
                fileSname = new ArrayList<>();

                for (String source : request.getRecipe_file_sname()) {
                    source = source.trim();
                    if (source.isEmpty()) {
                        continue;
                    }

                    // request의 file_sname 리스트를 순회하며 업로드된 이미지가 실제로 사용됐는지 확인한다.
                    if (content.contains(source)) {
                        // file_sname에서 파일명을 가져오기 위해 "/"로 나누고 마지막 인덱스를 가져온다.
                        int lastIdx = source.split("/").length - 1;
                        String fileName = source.split("/")[lastIdx];

                        // oldFileOname => temp/content/파일명
                        // newFileOname => recipe/파일명
                        String oldFileOname = "temp/content/" + fileName;
                        String newFileOname = "recipe/" + fileName;

                        // temp/content/에 있는 파일을 recipe/로 복사
                        imageService.copyS3(oldFileOname, newFileOname);

                        // 복사된 새 파일명을 가지고 새 url을 생성
                        String newFileSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newFileOname;

                        // content에 포함된 기존 url을 새로운 url로 대체
                        content = content.replace(source, newFileSname);

                        // DB에 저장할 리스트에 새로운 file_oname, file_sname을 push
                        fileOname.add(newFileOname);
                        fileSname.add(newFileSname);
                    }
                }
            }

            // request의 값으로 recipe 생성
            CreateRecipeDto createRecipeDto = new CreateRecipeDto(
                    request.getRecipe_title(),
                    LocalDate.now(ZoneId.of("Asia/Seoul")),
                    request.getRecipe_stuff(),
                    requestRecipeOname,
                    requestRecipeSname,
                    content,
                    request.getRecipe_category(),
                    user.getUserNickname(),
                    0L,
                    user
            );

            // 생성된 recipe를 db에 저장후 객체 반환
            Recipe result = recipeRepository.save(createRecipeDto.toEntity());

            if (fileOname != null) {
                CreateRecipeFileDto createRecipeFileDto = new CreateRecipeFileDto(fileOname, fileSname, result);
                recipeFileRepository.save(createRecipeFileDto.toEntity());
            }

            CrRecipeSuResDto responseBody = new CrRecipeSuResDto(ResponseCode.CREATED, ResponseMessage.CREATED, result.getRecipeNo());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            CrRecipeFaResDto responseBody = new CrRecipeFaResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            CrRecipeFaResDto responseBody = new CrRecipeFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends RecipeResponseDto> updateRecipe(UpRecipeReqDto request, Long recipe_no, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }
            // 레시피 번호로 레시피 찾기
            Recipe recipe = recipeRepository.findByRecipeNo(recipe_no).orElseThrow(RecipeNotFoundException::new);
            // 레시피 번호로 레시피 파일 찾기
            RecipeFile recipeFile = recipeFileRepository.findByRecipe_RecipeNo(recipe_no);

            if (!user.getUserNo().equals(recipe.getUser().getUserNo())) {
                throw new DoesNotMatchException();
            }

            String requestRecipeOname = request.getRecipe_oname();
            if (requestRecipeOname.trim().isEmpty()) {
                requestRecipeOname = null;
            }
            String requestRecipeSname = request.getRecipe_sname();
            if (requestRecipeSname.trim().isEmpty()) {
                requestRecipeSname = null;
            }

            String recipeOname = recipe.getRecipeOname();
            String recipeSname = recipe.getRecipeSname();

            if (requestRecipeOname == null || requestRecipeSname == null) { // request의 값이 null인 경우
                if (recipeOname != null && recipeSname != null) {
                    // S3에 있는 기존 이미지 삭제
                    imageService.deleteS3(recipeOname);
                }
            } else { // request의 값이 null이 아닌 경우
                // 새로운 썸네일 이미지인 경우
                if (!Objects.equals(recipeOname, requestRecipeOname)) {
                    if (recipeOname != null && recipeSname != null) {
                        // S3에 있는 기존 이미지 삭제
                        imageService.deleteS3(recipeOname);
                    }

                    // requestRecipeOname => temp/thumbnail/파일명
                    // newRecipeOname => recipeThumbnail/파일명
                    String newRecipeOname = "recipeThumbnail/" + requestRecipeOname.split("/")[2];

                    // temp/thumbnail에 있는 파일을 recipeThumbnail로 복사
                    imageService.copyS3(requestRecipeOname, newRecipeOname);

                    // DB에 저장할 requestRecipeOname과 requestRecipeSname을 새로운 이미지에 대한 정보로 변경
                    requestRecipeOname = newRecipeOname;
                    requestRecipeSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newRecipeOname;
                }
            }

            List<String> fileOname = null;
            List<String> fileSname = null;
            String content = request.getRecipe_content();

            if (recipeFile != null) {
                // 기존 recipe_file data가 있고 request의 recipe_file_oname과 recipe_file_sname이 null이 아니라면 해당 값으로 recipe file 수정 후 DB에 저장
                if (!CollectionUtils.isEmpty(request.getRecipe_file_oname()) && !CollectionUtils.isEmpty(request.getRecipe_file_sname())) {
                    fileOname = new ArrayList<>();
                    fileSname = new ArrayList<>();

                    List<String> recipeFileSnameList = recipeFile.getRecipeFileSname();

                    for (String source : request.getRecipe_file_sname()) {
                        source = source.trim();
                        if (source.isEmpty()) {
                            continue;
                        }

                        int lastIdx = source.split("/").length - 1;
                        String fileName = source.split("/")[lastIdx];

                        if (recipeFileSnameList.contains(source)) { // 해당 이미지 정보가 기존 file 리스트에 있는 경우. 즉 기존에 있던 이미지인 경우
                            String recipeFileOname = "recipe/" + fileName;

                            if (content.contains(source)) { // content에 있는 경우. 즉 여전히 사용하는 이미지인 경우
                                fileOname.add(recipeFileOname);
                                fileSname.add(source);
                            } else { // content에 없는 경우. 즉 내용에서 삭제된 사용하지 않는 이미지인 경우. S3에서 해당 이미지를 삭제한다.
                                imageService.deleteS3(recipeFileOname);
                            }
                        } else { // 해당 이미지 정보가 기존 file 리스트에 없는 경우. 즉 새로 추가된 이미지를 뜻함
                            if (content.contains(source)) {
                                // oldFileOname => temp/content/파일명
                                // newFileOname => recipe/파일명
                                String oldFileOname = "temp/content/" + fileName;
                                String newFileOname = "recipe/" + fileName;

                                // temp/content/에 있는 파일을 recipe/로 복사
                                imageService.copyS3(oldFileOname, newFileOname);

                                // 복사된 새 파일명을 가지고 새 url을 생성
                                String newFileSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newFileOname;

                                // content에 포함된 기존 url을 새로운 url로 대체
                                content = content.replace(source, newFileSname);

                                fileOname.add(newFileOname);
                                fileSname.add(newFileSname);
                            }
                        }
                    }

                    if (CollectionUtils.isEmpty(fileOname) || CollectionUtils.isEmpty(fileSname)) { // for문을 돌고도 fileOname과 fileSname이 빈 리스트라면 모두 사용되지 않기 때문에 data를 DB에서 삭제
                        recipeFileRepository.delete(recipeFile);
                    }
                } else { // 기존 recipe_file data가 있지만 request의 recipe_file_oname 또는 recipe_file_sname이 null이라면 잘못된 요청이다.
                    throw new IllegalArgumentException();
                }
            } else {
                // 기존 recipe_file data가 없고 request의 recipe_file_oname과 recipe_file_sname이 null이 아니라면 해당 값으로 새로운 recipe_file data 생성
                // 모두 새로운 이미지라는 것을 의미
                if (!CollectionUtils.isEmpty(request.getRecipe_file_oname()) && !CollectionUtils.isEmpty(request.getRecipe_file_sname())) {
                    fileOname = new ArrayList<>();
                    fileSname = new ArrayList<>();

                    for (String source : request.getRecipe_file_sname()) {
                        source = source.trim();
                        if (source.isEmpty()) {
                            continue;
                        }

                        if (content.contains(source)) {
                            int lastIdx = source.split("/").length - 1;
                            String fileName = source.split("/")[lastIdx];

                            String oldFileOname = "temp/content/" + fileName;
                            String newFileOname = "recipe/" + fileName;

                            imageService.copyS3(oldFileOname, newFileOname);

                            String newFileSname = "https://flowerbowl.s3.ap-northeast-2.amazonaws.com/" + newFileOname;

                            content = content.replace(source, newFileSname);

                            fileOname.add(newFileOname);
                            fileSname.add(newFileSname);
                        }
                    }
                }
            }

            // 찾은 레시피의 데이터를 수정
            recipe.updateTitle(request.getRecipe_title());
            recipe.updateCategory(request.getRecipe_category());
            recipe.updateStuff(request.getRecipe_stuff());
            recipe.updateContent(content);
            recipe.updateOname(requestRecipeOname);
            recipe.updateSname(requestRecipeSname);

            // 수정된 레시피 데이터 DB에 저장
            Recipe result = recipeRepository.save(recipe);

            if (fileOname != null) {
                if (recipeFile != null) {
                    recipeFile.updateFileOname(fileOname);
                    recipeFile.updateFileSname(fileSname);

                    recipeFileRepository.save(recipeFile);
                } else {
                    CreateRecipeFileDto createRecipeFileDto = new CreateRecipeFileDto(fileOname, fileSname, result);
                    recipeFileRepository.save(createRecipeFileDto.toEntity());
                }
            }

            UpRecipeSuResDto responseBody = new UpRecipeSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, result.getRecipeNo());
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            UpRecipeFaResDto responseBody = new UpRecipeFaResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (RecipeNotFoundException e) {
            logPrint(e);

            UpRecipeFaResDto responseBody = new UpRecipeFaResDto(ResponseCode.NOT_EXIST_RECIPE, ResponseMessage.NOT_EXIST_RECIPE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (DoesNotMatchException e) {
            logPrint(e);

            UpRecipeFaResDto responseBody = new UpRecipeFaResDto(ResponseCode.DOES_NOT_MATCH, ResponseMessage.DOES_NOT_MATCH);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            UpRecipeFaResDto responseBody = new UpRecipeFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends RecipeResponseDto> deleteRecipe(Long recipe_no, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }
            Recipe recipe = recipeRepository.findByRecipeNo(recipe_no).orElseThrow(RecipeNotFoundException::new);
            RecipeFile recipeFile = recipeFileRepository.findByRecipe_RecipeNo(recipe_no);

            if (!user.getUserNo().equals(recipe.getUser().getUserNo())) {
                throw new DoesNotMatchException();
            }

            if (recipe.getRecipeOname() != null && recipe.getRecipeSname() != null) {
                imageService.deleteS3(recipe.getRecipeOname());
            }

            if (recipeFile != null) {
                for (String source : recipeFile.getRecipeFileOname()) {
                    imageService.deleteS3(source);
                }
            }

            recipeRepository.delete(recipe);

            DelRecipeResDto responseBody = new DelRecipeResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            DelRecipeResDto responseBody = new DelRecipeResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (RecipeNotFoundException e) {
            logPrint(e);

            DelRecipeResDto responseBody = new DelRecipeResDto(ResponseCode.NOT_EXIST_RECIPE, ResponseMessage.NOT_EXIST_RECIPE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (DoesNotMatchException e) {
            logPrint(e);

            DelRecipeResDto responseBody = new DelRecipeResDto(ResponseCode.DOES_NOT_MATCH, ResponseMessage.DOES_NOT_MATCH);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            DelRecipeResDto responseBody = new DelRecipeResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends RecipeResponseDto> getAllRecipesGuest() throws Exception {
        try {
            List<Recipe> recipes = recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "recipeNo"));

            List<GetAllRecipesDto> posts = ListUtils.emptyIfNull(recipes).stream().map((recipe -> {
                Long recipeNo = recipe.getRecipeNo();

                List<RecipeLike> recipeLikes = recipeLikeRepository.findAllByRecipe_RecipeNo(recipeNo);
                List<Comment> comments = commentRepository.findAllByRecipe_RecipeNo(recipeNo);

                return GetAllRecipesDto.builder()
                        .recipe_no(recipeNo)
                        .recipe_sname(recipe.getRecipeSname())
                        .recipe_title(recipe.getRecipeTitle())
                        .recipe_writer(recipe.getRecipeWriter())
                        .recipe_date(recipe.getRecipeDate())
                        .recipe_like_cnt(Long.valueOf(recipeLikes.size()))
                        .comment_cnt(Long.valueOf(comments.size()))
                        .recipe_like_status(false)
                        .build();
            })).toList();

            GetAllRecipesSuResDto responseBody = new GetAllRecipesSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, posts);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            GetAllRecipesFaResDto responseBody = new GetAllRecipesFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends RecipeResponseDto> getAllRecipes(String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }
            Long userNo = user.getUserNo();

            List<Recipe> recipes = recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "recipeNo"));

            List<GetAllRecipesDto> posts = ListUtils.emptyIfNull(recipes).stream().map((recipe -> {
                Long recipeNo = recipe.getRecipeNo();

                List<RecipeLike> recipeLikes = recipeLikeRepository.findAllByRecipe_RecipeNo(recipeNo);
                List<Comment> comments = commentRepository.findAllByRecipe_RecipeNo(recipeNo);

                // recipeNo과 userNo으로 recipe_like 테이블 조회
                Specification<RecipeLike> spec = (root, query, criteriaBuilder) -> null;
                if (Objects.nonNull(recipeNo) && Objects.nonNull(userNo)) {
                    spec = RecipeLikeSpecification.equalRecipeNo(recipeNo).and(RecipeLikeSpecification.equalUserNo(userNo));
                }

                return GetAllRecipesDto.builder()
                        .recipe_no(recipeNo)
                        .recipe_sname(recipe.getRecipeSname())
                        .recipe_title(recipe.getRecipeTitle())
                        .recipe_writer(recipe.getRecipeWriter())
                        .recipe_date(recipe.getRecipeDate())
                        .recipe_like_cnt(Long.valueOf(recipeLikes.size()))
                        .comment_cnt(Long.valueOf(comments.size()))
                        // 다중 조건 검색에 따라 즐겨찾기 여부를 지정
                        .recipe_like_status(recipeLikeRepository.findOne(spec).isPresent())
                        .build();
            })).toList();

            GetAllRecipesSuResDto responseBody = new GetAllRecipesSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, posts);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            GetAllRecipesFaResDto responseBody = new GetAllRecipesFaResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

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

            // recipeFile이 null일 경우
            List<String> recipeFileOname = null;
            List<String> recipeFileSname = null;
            if (recipeFile != null) {
                recipeFileOname = recipeFile.getRecipeFileOname();
                recipeFileSname = recipeFile.getRecipeFileSname();
            }

            // 조회 수 증가
            recipe.updateView(recipe.getRecipeViews() + 1);
            recipeRepository.save(recipe);

            GetRecipeDto newDto = GetRecipeDto.builder()
                    .recipe_no(recipe.getRecipeNo())
                    .recipe_oname(recipe.getRecipeOname())
                    .recipe_sname(recipe.getRecipeSname())
                    .recipe_file_oname(recipeFileOname)
                    .recipe_file_sname(recipeFileSname)
                    .recipe_title(recipe.getRecipeTitle())
                    .recipe_writer(recipe.getRecipeWriter())
                    .recipe_date(recipe.getRecipeDate())
                    .recipe_stuff(recipe.getRecipeStuff())
                    .recipe_category(recipe.getRecipeCategory())
                    .recipe_content(recipe.getRecipeContent())
                    .recipe_like_status(false)
                    .build();

            GetRecipeSuResDto responseBody = new GetRecipeSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, newDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (RecipeNotFoundException e) {
            logPrint(e);

            GetRecipeFaResDto responseBody = new GetRecipeFaResDto(ResponseCode.NOT_EXIST_RECIPE, ResponseMessage.NOT_EXIST_RECIPE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            GetRecipeFaResDto responseBody = new GetRecipeFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends RecipeResponseDto> getRecipe(Long recipe_no, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }
            Long userNo = user.getUserNo();

            Recipe recipe = recipeRepository.findByRecipeNo(recipe_no).orElseThrow(RecipeNotFoundException::new);
            RecipeFile recipeFile = recipeFileRepository.findByRecipe_RecipeNo(recipe_no);

            // recipeFile이 null일 경우
            List<String> recipeFileOname = null;
            List<String> recipeFileSname = null;
            if (recipeFile != null) {
                recipeFileOname = recipeFile.getRecipeFileOname();
                recipeFileSname = recipeFile.getRecipeFileSname();
            }

            // 조회 수 증가
            recipe.updateView(recipe.getRecipeViews() + 1);
            recipeRepository.save(recipe);

            Long recipeNo = recipe.getRecipeNo();

            Specification<RecipeLike> spec = (root, query, criteriaBuilder) -> null;
            if (Objects.nonNull(recipeNo) && Objects.nonNull(userNo)) {
                spec = RecipeLikeSpecification.equalRecipeNo(recipeNo).and(RecipeLikeSpecification.equalUserNo(userNo));
            }

            GetRecipeDto newDto = GetRecipeDto.builder()
                    .recipe_no(recipeNo)
                    .recipe_oname(recipe.getRecipeOname())
                    .recipe_sname(recipe.getRecipeSname())
                    .recipe_file_oname(recipeFileOname)
                    .recipe_file_sname(recipeFileSname)
                    .recipe_title(recipe.getRecipeTitle())
                    .recipe_writer(recipe.getRecipeWriter())
                    .recipe_date(recipe.getRecipeDate())
                    .recipe_stuff(recipe.getRecipeStuff())
                    .recipe_category(recipe.getRecipeCategory())
                    .recipe_content(recipe.getRecipeContent())
                    // 다중 조건 검색에 따라 즐겨찾기 여부를 지정
                    .recipe_like_status(recipeLikeRepository.findOne(spec).isPresent())
                    .build();

            GetRecipeSuResDto responseBody = new GetRecipeSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, newDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            GetRecipeFaResDto responseBody = new GetRecipeFaResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (RecipeNotFoundException e) {
            logPrint(e);

            GetRecipeFaResDto responseBody = new GetRecipeFaResDto(ResponseCode.NOT_EXIST_RECIPE, ResponseMessage.NOT_EXIST_RECIPE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            GetRecipeFaResDto responseBody = new GetRecipeFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends RecipeResponseDto> getRecipesCategoryGuest(String koreanName) throws Exception {
        try {
            Category category = Category.parsing(koreanName);
            if (category == null) {
                throw new CategoryNotFoundException();
            }

            List<Recipe> recipes = recipeRepository.findAllByRecipeCategory(category);

            List<GetRecipesCategoryDto> posts = ListUtils.emptyIfNull(recipes).stream().map((recipe -> {
                Long recipeNo = recipe.getRecipeNo();

                List<RecipeLike> recipeLikes = recipeLikeRepository.findAllByRecipe_RecipeNo(recipeNo);
                List<Comment> comments = commentRepository.findAllByRecipe_RecipeNo(recipeNo);

                return GetRecipesCategoryDto.builder()
                        .recipe_no(recipeNo)
                        .recipe_sname(recipe.getRecipeSname())
                        .recipe_title(recipe.getRecipeTitle())
                        .recipe_writer(recipe.getRecipeWriter())
                        .recipe_date(recipe.getRecipeDate())
                        .recipe_like_cnt(Long.valueOf(recipeLikes.size()))
                        .comment_cnt(Long.valueOf(comments.size()))
                        .recipe_like_status(false)
                        .build();
            })).toList();

            GetRecipesCategorySuResDto responseBody = new GetRecipesCategorySuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, posts);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (CategoryNotFoundException e) {
            logPrint(e);

            GetRecipesCategoryFaResDto responseBody = new GetRecipesCategoryFaResDto(ResponseCode.NOT_EXIST_CATEGORY, ResponseMessage.NOT_EXIST_CATEGORY);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            GetRecipesCategoryFaResDto responseBody = new GetRecipesCategoryFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends RecipeResponseDto> getRecipesCategory(String koreanName, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }
            Long userNo = user.getUserNo();

            Category category = Category.parsing(koreanName);
            if (category == null) {
                throw new CategoryNotFoundException();
            }

            List<Recipe> recipes = recipeRepository.findAllByRecipeCategory(category);

            List<GetRecipesCategoryDto> posts = ListUtils.emptyIfNull(recipes).stream().map((recipe -> {
                Long recipeNo = recipe.getRecipeNo();

                List<RecipeLike> recipeLikes = recipeLikeRepository.findAllByRecipe_RecipeNo(recipeNo);
                List<Comment> comments = commentRepository.findAllByRecipe_RecipeNo(recipeNo);

                // recipeNo와 userNo로 recipe_like 테이블 조회
                Specification<RecipeLike> spec = (root, query, criteriaBuilder) -> null;
                if (Objects.nonNull(recipeNo) && Objects.nonNull(userNo)) {
                    spec = RecipeLikeSpecification.equalRecipeNo(recipeNo).and(RecipeLikeSpecification.equalUserNo(userNo));
                }

                return GetRecipesCategoryDto.builder()
                        .recipe_no(recipeNo)
                        .recipe_sname(recipe.getRecipeSname())
                        .recipe_title(recipe.getRecipeTitle())
                        .recipe_writer(recipe.getRecipeWriter())
                        .recipe_date(recipe.getRecipeDate())
                        .recipe_like_cnt(Long.valueOf(recipeLikes.size()))
                        .comment_cnt(Long.valueOf(comments.size()))
                        // 다중 조건 검색에 따라 즐겨찾기 여부를 지정
                        .recipe_like_status(recipeLikeRepository.findOne(spec).isPresent())
                        .build();
            })).toList();

            GetRecipesCategorySuResDto responseBody = new GetRecipesCategorySuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, posts);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            GetRecipesCategoryFaResDto responseBody = new GetRecipesCategoryFaResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (CategoryNotFoundException e) {
            logPrint(e);

            GetRecipesCategoryFaResDto responseBody = new GetRecipesCategoryFaResDto(ResponseCode.NOT_EXIST_CATEGORY, ResponseMessage.NOT_EXIST_CATEGORY);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            GetRecipesCategoryFaResDto responseBody = new GetRecipesCategoryFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends RecipeResponseDto> getRecipesAdmin() throws Exception {
        try {
            List<Recipe> recipes = recipeRepository.findREcipesByRoleAdmin();

            List<GetRecipesAdminDto> posts = ListUtils.emptyIfNull(recipes).stream().map((recipe -> {
                return GetRecipesAdminDto.builder()
                        .recipe_no(recipe.getRecipeNo())
                        .recipe_sname(recipe.getRecipeSname())
                        .recipe_title(recipe.getRecipeTitle())
                        .recipe_content(recipe.getRecipeContent())
                        .build();
            })).toList();

            GetRecipesAdminSuResDto responseBody = new GetRecipesAdminSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, posts);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            GetRecipesAdminFaResDto responseBody = new GetRecipesAdminFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends RecipeResponseDto> updateRecipeLike(Long recipe_no, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }
            Long userNo = user.getUserNo();

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
            Recipe recipe = recipeRepository.findByRecipeNo(recipe_no).orElseThrow(RecipeNotFoundException::new);

            CreateRecipeLikeDto createRecipeLikeDto = new CreateRecipeLikeDto(recipe, user);

            RecipeLike savedRecipeLike = recipeLikeRepository.save(createRecipeLikeDto.toEntity());

            UpRecipeLikeSuResDto responseBody = new UpRecipeLikeSuResDto(ResponseCode.CREATED, ResponseMessage.CREATED, savedRecipeLike.getRecipeLikeNo());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            UpRecipeLikeFaResDto responseBody = new UpRecipeLikeFaResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (RecipeNotFoundException e) {
            logPrint(e);

            UpRecipeLikeFaResDto responseBody = new UpRecipeLikeFaResDto(ResponseCode.NOT_EXIST_RECIPE, ResponseMessage.NOT_EXIST_RECIPE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            UpRecipeLikeFaResDto responseBody = new UpRecipeLikeFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    private void logPrint(Exception e) {
        log.error("Exception [Err_Msg]: {}", e.getMessage());
        log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);
    }

}
