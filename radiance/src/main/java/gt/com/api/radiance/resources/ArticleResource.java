/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.resources;

import gt.com.api.radiance.controllers.ArticleController;
import gt.com.api.radiance.dtos.ArticleModel;
import gt.com.api.radiance.dtos.UserLoad;
import gt.com.api.radiance.verify.ApiVersionValidator;
import gt.com.api.radiance.verify.Authenticator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
@Api("Article")
@Path("/api/article")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleResource.class);
    private static final ArticleController ARTICLE_CONTROLLER = new ArticleController();

    @ApiOperation(value = "Get a article list", notes = "Get a list of articles")
    @GET
    public List<ArticleModel> getArticles(@QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("filterTag") @DefaultValue("") String filterTag, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
        UserLoad userLoad = Authenticator.tokenValidation(request);
        List<ArticleModel> articles = ARTICLE_CONTROLLER.getArticles(filter, filterTag);
        if (articles == null) {
            LOGGER.error("Time of not GET article list: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST + " " + userLoad.toString());
            throw new WebApplicationException("Cannot get article list ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to GET article list: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK + " " + userLoad.toString());
        return articles;
    }

    @ApiOperation(value = "Get specific article", notes = "Get specific article by id")
    @GET
    @Path("/{id}")
    public ArticleModel getArticle(@PathParam("id") String id, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
        UserLoad userLoad = Authenticator.tokenValidation(request);
        ArticleModel articleModel;
        try {
            articleModel = ARTICLE_CONTROLLER.getArticle(id);
            if (articleModel == null) {
                LOGGER.error("Time of not GET article: " + (System.currentTimeMillis() - startTime)
                        + " milliseconds, statusCode:" + Response.Status.NOT_FOUND + " " + userLoad.toString());
                throw new WebApplicationException("Cannot get article ", Response.Status.NOT_FOUND);
            }
        } catch (Exception ex) {
            LOGGER.error("Time of not GET article: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_FOUND + " " + userLoad.toString());
            throw new WebApplicationException("Cannot get tag list ", Response.Status.NOT_FOUND);
        }
        LOGGER.info("Time to GET article: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK + " " + userLoad.toString());
        return articleModel;
    }

    @ApiOperation(value = "Create article", notes = "Insert new article")
    @POST
    public ArticleModel postArticle(ArticleModel articleModel, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
        UserLoad userLoad = Authenticator.tokenValidation(request);
        //verification of required fields
        if (articleModel.getTittle().equals("") || articleModel.getContent().equals("")
                || articleModel.getTags() == null || articleModel.getTags().size() < 1) {
            LOGGER.error("Time of not save article: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode()
                    + " " + userLoad.toString());
            throw new WebApplicationException("Fields are missing ", Response.Status.NOT_ACCEPTABLE);
        }
        ArticleModel newArticle = ARTICLE_CONTROLLER.saveArticle(articleModel, userLoad);
        if (newArticle == null) {
            LOGGER.error("Time of not save new article: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST + " " + userLoad.toString());
            throw new WebApplicationException("Cannot post article ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to POST article: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK + " " + userLoad.toString());
        return newArticle;
    }

    @ApiOperation(value = "Update specific article", notes = "Modify specific article")
    @PUT
    @Path("/{id}")
    public ArticleModel putArticle(ArticleModel articleModel, @PathParam("id") String id,
            @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
        UserLoad userLoad = Authenticator.tokenValidation(request);
        //verification of required fields
        if (articleModel.getTittle().equals("") || articleModel.getContent().equals("")
                || articleModel.getTags() == null || articleModel.getTags().size() < 1) {
            LOGGER.error("Time of not update article: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode()
                    + " " + userLoad.toString());
            throw new WebApplicationException("Fields are missing ", Response.Status.NOT_ACCEPTABLE);
        }
        //verificate article exists
        if (!ARTICLE_CONTROLLER.verifyArticleExists(id, userLoad)) {
            LOGGER.error("Time of not update article: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_FOUND.getStatusCode()
                    + " " + userLoad.toString());
            throw new WebApplicationException("Article not found for user: " + userLoad.getUser(),
                    Response.Status.NOT_FOUND);
        }
        ArticleModel updateArticle = ARTICLE_CONTROLLER.updateArticle(id, articleModel);
        if (updateArticle == null) {
            LOGGER.error("Time of not update article: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST + " " + userLoad.toString());
            throw new WebApplicationException("Cannot put article ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to PUT article: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK + " " + userLoad.toString());
        return updateArticle;
    }

    @ApiOperation(value = "Delete article", notes = "Soft delete specific article")
    @DELETE
    @Path("/{id}")
    public Response deleteArticle(@PathParam("id") String id, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
        UserLoad userLoad = Authenticator.tokenValidation(request);
        //verificate article exists
        if (!ARTICLE_CONTROLLER.verifyArticleExists(id, userLoad)) {
            LOGGER.error("Time of not delete article: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_FOUND.getStatusCode()
                    + " " + userLoad.toString());
            throw new WebApplicationException("article not found for user: " + userLoad.getUser(),
                    Response.Status.NOT_FOUND);
        }
        if (!ARTICLE_CONTROLLER.deleteArticle(id)) {
            LOGGER.error("Time of not DELETE article: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST + " " + userLoad.toString());
            throw new WebApplicationException("Cannot delete article ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to DELETE article: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK + " " + userLoad.toString());
        return Response.status(Response.Status.OK).entity("OK").build();
    }

}
