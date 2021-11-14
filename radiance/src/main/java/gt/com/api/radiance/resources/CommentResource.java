/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.resources;

import gt.com.api.radiance.controllers.CommentController;
import gt.com.api.radiance.dtos.CommentModel;
import gt.com.api.radiance.dtos.UserLoad;
import gt.com.api.radiance.verify.ApiVersionValidator;
import gt.com.api.radiance.verify.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
@Api("Comment")
@Path("/api/comment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentResource.class);
    private static final CommentController COMMENT_CONTROLLER = new CommentController();

    @GET
    @Path("/{articleId}")
    @ApiOperation(value = "Get a comment list", notes = "Get a specific list of comment from an article")
    public List<CommentModel> getComments(@PathParam("articleId") String articleId,
            @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
        UserLoad userLoad = Authenticator.tokenValidation(request);
        List<CommentModel> comments = COMMENT_CONTROLLER.getComments(articleId);
        if (comments == null) {
            LOGGER.error("Time of not GET comment list: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST + " " + userLoad.toString());
            throw new WebApplicationException("Cannot get comment list ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to GET comment list: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK + " " + userLoad.toString());
        return comments;
    }

    @POST
    @ApiOperation(value = "Create comment", notes = "Insert new comment")
    public CommentModel postComment(CommentModel commentModel, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
        UserLoad userLoad = Authenticator.tokenValidation(request);
        //verification of required fields
        if (commentModel.getComment().equals("")) {
            LOGGER.error("Time of not save article: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode()
                    + " " + userLoad.toString());
            throw new WebApplicationException("Fields are missing ", Response.Status.NOT_ACCEPTABLE);
        }
        CommentModel newComment = COMMENT_CONTROLLER.saveComment(commentModel, userLoad);
        if (newComment == null) {
            LOGGER.error("Time of not save new comment: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST + " " + userLoad.toString());
            throw new WebApplicationException("Cannot post comment ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to POST comment: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK + " " + userLoad.toString());
        return newComment;
    }

}
