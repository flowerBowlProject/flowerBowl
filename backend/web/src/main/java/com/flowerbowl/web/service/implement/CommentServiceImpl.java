package com.flowerbowl.web.service.implement;

import com.flowerbowl.web.common.ResponseCode;
import com.flowerbowl.web.common.ResponseMessage;
import com.flowerbowl.web.domain.Comment;
import com.flowerbowl.web.domain.Community;
import com.flowerbowl.web.domain.Recipe;
import com.flowerbowl.web.domain.User;
import com.flowerbowl.web.dto.object.comment.CreateCommentDto;
import com.flowerbowl.web.dto.request.comment.CrCommentReqDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
                    Comment comment = commentRepository.findByCommentNo(request.getParent_no()).orElseThrow(CommentNotFoundException::new);

                    // 부모 댓글 또한 게시판 종류가 레시피여야 한다. 즉 부모 댓글의 community column은 null이어야 한다. null이 아닐 경우 Exception throw
                    if (comment.getCommunity() != null) {
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
                    Comment comment = commentRepository.findByCommentNo(request.getParent_no()).orElseThrow(CommentNotFoundException::new);

                    // 부모 댓글 또한 게시판 종류가 커뮤니티여야 한다. 즉 부모 댓글의 recipe column은 null이어야 한다. null이 아닐 경우 Exception throw
                    if (comment.getRecipe() != null) {
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

    private void logPrint(Exception e) {
        log.error("Exception [Err_Msg]: {}", e.getMessage());
        log.error("Exception [Err_Where]: {}", e.getStackTrace()[0]);
    }

}
