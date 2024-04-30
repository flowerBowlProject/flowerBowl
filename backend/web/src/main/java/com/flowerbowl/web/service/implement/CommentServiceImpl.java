package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.domain.Comment;
import com.flowerbowl.web.domain.Community;
import com.flowerbowl.web.domain.Recipe;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.object.comment.CommentPageInfo;
import com.flowerbowl.web.dto.object.comment.CreateCommentDto;
import com.flowerbowl.web.dto.object.comment.GetCommentsDto;
import com.flowerbowl.web.dto.request.comment.CrCommentReqDto;
import com.flowerbowl.web.dto.request.comment.GetCommentReqParam;
import com.flowerbowl.web.dto.request.comment.UpCommentReqDto;
import com.flowerbowl.web.dto.response.comment.*;
import com.flowerbowl.web.handler.*;
import com.flowerbowl.web.repository.CommentRepository;
import com.flowerbowl.web.repository.CommunityRepository;
import com.flowerbowl.web.repository.RecipeRepository;
import com.flowerbowl.web.repository.UserRepository;
import com.flowerbowl.web.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final CommunityRepository communityRepository;

    @Override
    public ResponseEntity<? extends CommentResponseDto> createComment(CrCommentReqDto request, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }

            Long postType = request.getType();
            Long postNo = request.getPost_no();

            CreateCommentDto createCommentDto = null;

            if (Objects.equals(postType, 1L)) { // 게시판 종류가 레시피인 경우
                Recipe recipe = recipeRepository.findByRecipeNo(postNo).orElseThrow(RecipeNotFoundException::new);

                // 부모 댓글이 있는 경우
                if (request.getParent_no() != null) {
                    // 부모 댓글 조회
                    Comment parentComment = commentRepository.findByCommentNo(request.getParent_no()).orElseThrow(CommentNotFoundException::new);

                    // 부모 댓글 또한 게시판 종류가 레시피여야 한다. 즉 부모 댓글의 community column은 null이어야 한다. null이 아닐 경우 Exception throw
                    // 또한 작성하려는 대댓글과 부모 댓글의 게시글 번호가 같아야 한다. 같지 않은 경우 Exception throw
                    if (parentComment.getCommunity() != null || !postNo.equals(parentComment.getRecipe().getRecipeNo())) {
                        throw new WrongBoardTypeException();
                    }
                }

                createCommentDto = new CreateCommentDto(
                        request.getComment_content(),
                        LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                        request.getParent_no(),
                        user,
                        recipe,
                        null
                );

            } else if (Objects.equals(postType, 2L)) { // 게시판 종류가 커뮤니티인 경우
                Community community = communityRepository.findByCommunityNo(postNo).orElseThrow(CommunityNotFoundException::new);

                // 부모 댓글이 있는 경우
                if (request.getParent_no() != null) {
                    // 부모 댓글 조회
                    Comment parentComment = commentRepository.findByCommentNo(request.getParent_no()).orElseThrow(CommentNotFoundException::new);

                    // 부모 댓글 또한 게시판 종류가 커뮤니티여야 한다. 즉 부모 댓글의 recipe column은 null이어야 한다. null이 아닐 경우 Exception throw
                    // 또한 작성하려는 대댓글과 부모 댓글의 게시글 번호가 같아야 한다. 같지 않은 경우 Exception throw
                    if (parentComment.getRecipe() != null || !postNo.equals(parentComment.getCommunity().getCommunityNo())) {
                        throw new WrongBoardTypeException();
                    }
                }

                createCommentDto = new CreateCommentDto(
                        request.getComment_content(),
                        LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                        request.getParent_no(),
                        user,
                        null,
                        community
                );
            } else {
                throw new WrongBoardTypeException();
            }

            Comment comment = commentRepository.save(Objects.requireNonNull(createCommentDto).toEntity());

            CrCommentSuResDto responseBody = new CrCommentSuResDto(ResponseCode.CREATED, ResponseMessage.CREATED, comment.getCommentNo());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            CrCommentFaResDto responseBody = new CrCommentFaResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (CommentNotFoundException e) {
            logPrint(e);

            CrCommentFaResDto responseBody = new CrCommentFaResDto(ResponseCode.NOT_EXIST_COMMENT, ResponseMessage.NOT_EXIST_COMMENT);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (RecipeNotFoundException e) {
            logPrint(e);

            CrCommentFaResDto responseBody = new CrCommentFaResDto(ResponseCode.NOT_EXIST_RECIPE, ResponseMessage.NOT_EXIST_RECIPE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (CommunityNotFoundException e) {
            logPrint(e);

            CrCommentFaResDto responseBody = new CrCommentFaResDto(ResponseCode.NOT_EXIST_COMMUNITY, ResponseMessage.NOT_EXIST_COMMUNITY);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (WrongBoardTypeException e) {
            logPrint(e);

            CrCommentFaResDto responseBody = new CrCommentFaResDto(ResponseCode.WRONG_BOARD_TYPE, ResponseMessage.WRONG_BOARD_TYPE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            CrCommentFaResDto responseBody = new CrCommentFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends CommentResponseDto> updateComment(UpCommentReqDto request, Long comment_no, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }

            Comment comment = commentRepository.findByCommentNo(comment_no).orElseThrow(CommentNotFoundException::new);

            // 수정하려는 User와 comment의 작성자가 일치하는지 검증
            if (!user.getUserNo().equals(comment.getUser().getUserNo())) {
                throw new DoesNotMatchException();
            }

            comment.updateContent(request.getComment_content());

            Comment result = commentRepository.save(comment);

            UpCommentSuResDto responseBody = new UpCommentSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, result.getCommentNo());
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            UpCommentFaResDto responseBody = new UpCommentFaResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (CommentNotFoundException e) {
            logPrint(e);

            UpCommentFaResDto responseBody = new UpCommentFaResDto(ResponseCode.NOT_EXIST_COMMENT, ResponseMessage.NOT_EXIST_COMMENT);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (DoesNotMatchException e) {
            logPrint(e);

            UpCommentFaResDto responseBody = new UpCommentFaResDto(ResponseCode.DOES_NOT_MATCH, ResponseMessage.DOES_NOT_MATCH);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            UpCommentFaResDto responseBody = new UpCommentFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? extends CommentResponseDto> deleteComment(Long comment_no, String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }

            Comment comment = commentRepository.findByCommentNo(comment_no).orElseThrow(CommentNotFoundException::new);

            // 삭제하려는 User와 comment의 작성자가 일치하는지 검증
            if (!user.getUserNo().equals(comment.getUser().getUserNo())) {
                throw new DoesNotMatchException();
            }

            // 삭제하려는 댓글을 부모 댓글로 가지는 자식 댓글들을 모두 삭제
            List<Comment> childComments = commentRepository.findAllByParentNo(comment_no);
            if (!childComments.isEmpty()) {
                commentRepository.deleteAll(childComments);
            }

            // 삭제하려는 댓글 삭제
            commentRepository.delete(comment);

            DelCommentResDto responseBody = new DelCommentResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UserNotFoundException e) {
            logPrint(e);

            DelCommentResDto responseBody = new DelCommentResDto(ResponseCode.NOT_EXIST_USER, ResponseMessage.NOT_EXIST_USER);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (CommentNotFoundException e) {
            logPrint(e);

            DelCommentResDto responseBody = new DelCommentResDto(ResponseCode.NOT_EXIST_COMMENT, ResponseMessage.NOT_EXIST_COMMENT);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (DoesNotMatchException e) {
            logPrint(e);

            DelCommentResDto responseBody = new DelCommentResDto(ResponseCode.DOES_NOT_MATCH, ResponseMessage.DOES_NOT_MATCH);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            DelCommentResDto responseBody = new DelCommentResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @Override
    public ResponseEntity<? extends CommentResponseDto> getComments(GetCommentReqParam request) throws Exception {
        try {
            // 날짜 포맷 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            int page = request.getPage() - 1;
            int size = request.getSize();

            // parameter로 받은 page(페이지 번호 - 1), size(페이지 당 데이터 개수)로 PageRequest 생성
            PageRequest pageRequest = PageRequest.of(page, size);

            Long postType = request.getType();
            Long postNo = request.getPost_no();

            Page<Comment> commentPage;

            if (Objects.equals(postType, 1L)) { // 게시판 종류가 레시피인 경우
                List<Comment> commentList = commentRepository.findCommentsByRecipeNo(postNo);

                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), commentList.size());

                commentPage = new PageImpl<>(commentList.subList(start, end), pageRequest, commentList.size());
            } else if (Objects.equals(postType, 2L)) { // 게시판 종류가 커뮤니티인 경우
                List<Comment> commentList = commentRepository.findCommentsByCommunityNo(postNo);

                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), commentList.size());

                commentPage = new PageImpl<>(commentList.subList(start, end), pageRequest, commentList.size());
            } else {
                throw new WrongBoardTypeException();
            }

            // 댓글이 하나도 없을 때 Exception throw
            if (commentPage.getTotalElements() == 0) {
                throw new CommentNotFoundException();
            }

            // 요청한 페이지 번호가 존재하는 페이지 개수를 넘을 때 Exception throw
            if (page >= commentPage.getTotalPages()) {
                throw new PageNotFoundException();
            }

            // 조회한 댓글 목록을 순회하며 각각의 댓글 정보로 comments를 빌드
            List<GetCommentsDto> comments = commentPage.stream().map((comment -> {
                return GetCommentsDto.builder()
                        .comment_no(comment.getCommentNo())
                        .comment_writer(comment.getUser().getUserNickname())
                        .comment_date(comment.getCommentDate().format(formatter))
                        .comment_content(comment.getCommentContent())
                        .user_file_sname(comment.getUser().getUserFileSname())
                        .parent_no(comment.getParentNo())
                        .build();
            })).toList();

            // 조회한 댓글 목록에 대한 정보로 페이지 정보 빌드
            CommentPageInfo commentPageInfo = CommentPageInfo.builder()
                    .page(page + 1)
                    .size(size)
                    .totalPage(commentPage.getTotalPages())
                    .totalElement(commentPage.getTotalElements())
                    .build();

            GetCommentsSuResDto responseBody = new GetCommentsSuResDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, comments, commentPageInfo);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (WrongBoardTypeException e) {
            logPrint(e);

            GetCommentsFaResDto responseBody = new GetCommentsFaResDto(ResponseCode.WRONG_BOARD_TYPE, ResponseMessage.WRONG_BOARD_TYPE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } catch (CommentNotFoundException e) {
            logPrint(e);

            GetCommentsFaResDto responseBody = new GetCommentsFaResDto(ResponseCode.NOT_EXIST_COMMENT, ResponseMessage.NOT_EXIST_COMMENT);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (PageNotFoundException e) {
            logPrint(e);

            GetCommentsFaResDto responseBody = new GetCommentsFaResDto(ResponseCode.NOT_EXIST_PAGE, ResponseMessage.NOT_EXIST_PAGE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            logPrint(e);

            GetCommentsFaResDto responseBody = new GetCommentsFaResDto(ResponseCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    private void logPrint(Exception e) {
        log.error("Exception [Err_Msg]: {}", e.getMessage());
        log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);
    }

}
