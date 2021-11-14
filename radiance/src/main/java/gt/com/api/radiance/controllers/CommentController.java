/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.dtos.CommentModel;
import gt.com.api.radiance.dtos.UserLoad;
import gt.com.api.radiance.dtos.UserModel;
import gt.com.api.radiance.entities.Comment;
import gt.com.api.radiance.helper.FormatDate;
import gt.com.api.radiance.queries.CommentQuery;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    public CommentController() {
    }

    public List<CommentModel> getComments(String articleId) {
        List<Comment> commentList = CommentQuery.getCommentList(new ObjectId(articleId));
        if (commentList == null) {
            LOGGER.error("Cannot GET comment list");
            return null;
        }
        List<CommentModel> comments = new ArrayList();
        commentList.stream().map(comment -> {
            CommentModel commentModel = new CommentModel();
            commentModel.setCommentId(comment.getId().toString());
            commentModel.setComment(comment.getComment());
            commentModel.setCreationDate(FormatDate.convertTime(comment.getCreationDate()));
            commentModel.setUser(comment.getUser().getName());
            return commentModel;
        }).forEachOrdered(commentModel -> {
            comments.add(commentModel);
        });
        return comments;
    }

    public CommentModel saveComment(CommentModel commentModel, UserLoad userLoad) {
        UserController userController = new UserController();
        Comment comment = new Comment();
        comment.setComment(commentModel.getComment());
        comment.setCreationDate(String.valueOf(System.currentTimeMillis()));
        comment.setArticleId(new ObjectId(commentModel.getArticleId()));
        UserModel user = userController.findUsername(userLoad.getUser());
        comment.setUserId(new ObjectId(user.getUserId()));
        comment.setIsDelete(Boolean.FALSE);
        ObjectId commentId = CommentQuery.saveComment(comment);
        if (commentId == null) {
            LOGGER.error("Failed to save comment");
            return null;
        }
        commentModel.setCommentId(commentId.toString());
        return commentModel;
    }

}
