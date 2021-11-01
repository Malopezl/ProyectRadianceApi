/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.resources;

import gt.com.api.radiance.controllers.TagController;
import gt.com.api.radiance.dtos.TagModel;
import gt.com.api.radiance.dtos.TagPage;
import gt.com.api.radiance.verify.ApiVersionValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("Tag")
@Path("/api/tag")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagResource.class);
    private static final TagController TAG_CONTROLLER = new TagController();

    @ApiOperation(value = "Get a tag list", notes = "Get a list of tags")
    @GET
    public TagPage getTags(@QueryParam("page") @DefaultValue("1") Long page,
            @QueryParam("size") @DefaultValue("20") Long size, @QueryParam("filter") @DefaultValue("") String filter,
            @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        TagPage tags = TAG_CONTROLLER.getTagPage(size, page, filter);
        if (tags == null) {
            LOGGER.error("Time of not GET tag list: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot get tag list ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to GET tag list: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return tags;
    }

    @ApiOperation(value = "Get specific tag", notes = "Get specific tag by id")
    @GET
    @Path("/{id}")
    public TagModel getTag(@PathParam("id") String id, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        TagModel tag = TAG_CONTROLLER.getTag(id);
        if (tag == null) {
            LOGGER.error("Time of not GET tag: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot get tag ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to GET tag: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return tag;
    }

    @ApiOperation(value = "Create tag", notes = "Insert new tag")
    @POST
    public TagModel postTag(TagModel tag, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        //verification of required fields
        if (tag.getName().equals("") || tag.getColor().equals("") || tag.getIcon().equals("")) {
            LOGGER.error("Time of not save tag: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode());
            throw new WebApplicationException("Fields are missing ", Response.Status.NOT_ACCEPTABLE);
        }
        TagModel newTag = TAG_CONTROLLER.saveTag(tag);
        if (newTag == null) {
            LOGGER.error("Time of not save new tag: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot post tag ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to POST tag: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return newTag;
    }

    @ApiOperation(value = "Update specific tag", notes = "Modify specific tag")
    @PUT
    @Path("/{id}")
    public TagModel putTag(TagModel tag, @PathParam("id") String id, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        //verification of required fields
        if (tag.getName().equals("") || tag.getColor().equals("") || tag.getIcon().equals("")) {
            LOGGER.error("Time of not save tag: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_ACCEPTABLE.getStatusCode());
            throw new WebApplicationException("Fields are missing ", Response.Status.NOT_ACCEPTABLE);
        }
        //verificate tag exists
        if (!TAG_CONTROLLER.verifyTagExists(id)) {
            LOGGER.error("Time of not update tag: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_FOUND.getStatusCode());
            throw new WebApplicationException("Tag not found, tagID: " + id,
                    Response.Status.NOT_FOUND);
        }
        TagModel updateTag = TAG_CONTROLLER.updateTag(id, tag);
        if (updateTag == null) {
            LOGGER.error("Time of not update tag: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot put subscription type ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to PUT tag: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return updateTag;
    }

    @ApiOperation(value = "Delete tag", notes = "Soft delete specific tag")
    @DELETE
    @Path("/{id}")
    public Response deleteTag(@PathParam("id") String id, @Context HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiVersionValidator.validate(request);
//        UserLoad userLoad = Authenticator.tokenValidation(request);
        //verificate tag exists
        if (!TAG_CONTROLLER.verifyTagExists(id)) {
            LOGGER.error("Time of not delete tag: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.NOT_FOUND.getStatusCode());
            throw new WebApplicationException("Tag not found, tagID: " + id,
                    Response.Status.NOT_FOUND);
        }
        if (!TAG_CONTROLLER.deleteTag(id)) {
            LOGGER.error("Time of not delete tag: " + (System.currentTimeMillis() - startTime)
                    + " milliseconds, statusCode:" + Response.Status.BAD_REQUEST);
            throw new WebApplicationException("Cannot delete tag ", Response.Status.BAD_REQUEST);
        }
        LOGGER.info("Time to DELETE tag: " + (System.currentTimeMillis() - startTime)
                + " milliseconds, statusCode:" + Response.Status.OK);
        return Response.status(Response.Status.OK).entity("OK").build();
    }

}
