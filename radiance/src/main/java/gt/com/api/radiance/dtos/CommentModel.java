/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

import gt.com.api.radiance.entities.User;

/**
 *
 * @author malopez
 */
public class CommentModel {

    private String commentId;
    private String comment;
    private String creationDate;
    private User userId;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CommentModel{" + "commentId=" + commentId + ", comment=" + comment + ", creationDate=" + creationDate
                + ", userId=" + userId + '}';
    }

}
