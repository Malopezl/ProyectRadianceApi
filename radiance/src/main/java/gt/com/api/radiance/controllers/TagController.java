/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.dtos.TagModel;
import gt.com.api.radiance.entities.Tag;
import gt.com.api.radiance.queries.TagQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author malopez
 */
public class TagController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);

    public TagController() {
    }

    public List<TagModel> getTags(String filter) {
        Pattern regexp = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        List<Tag> tagList = TagQuery.getTagList(regexp);
        if (tagList == null) {
            return null;
        }
        List<TagModel> tagsModel = new ArrayList();
        tagList.stream().map(tag -> {
            TagModel tagModel = new TagModel();
            tagModel.setTagId(tag.getId().toString());
            tagModel.setName(tag.getName());
            tagModel.setColor(tag.getColor());
            tagModel.setIcon(tag.getIcon());
            return tagModel;
        }).forEachOrdered(tagModel -> {
            tagsModel.add(tagModel);
        });
//        tags.setTags(tagsModel);
//        tags.setIsFirstPage(page == 1);
//        Long elements = TagQuery.totalPages();
//        Long pages;
//        if (elements > size) {
//            pages = elements / size;
//        } else {
//            pages = 1L;
//        }
//        tags.setIsLastPage(page.longValue() == pages.longValue());
//        tags.setNumber(page);
//        tags.setNumberOfElements(Long.valueOf(tagList.size()));
//        tags.setIsPreviousPageAvailable(page > 1);
//        tags.setSize(size);
//        tags.setTotalElements(elements);
//        tags.setTotalPages(pages);
        return tagsModel;
    }

    public TagModel getTag(String id) {
        ObjectId tagId = new ObjectId(id);
        Tag tag = TagQuery.findTag(tagId);
        if (tag == null) {
            return null;
        }
        TagModel tagModel = new TagModel();
        tagModel.setTagId(tag.getId().toString());
        tagModel.setName(tag.getName());
        tagModel.setColor(tag.getColor());
        tagModel.setIcon(tag.getIcon());
        return tagModel;
    }

    public TagModel saveTag(TagModel tagModel) {
        Tag tag = new Tag();
        tag.setName(tagModel.getName());
        tag.setIcon(tagModel.getIcon());
        tag.setColor(tagModel.getColor());
        tag.setIsDelete(Boolean.FALSE);
        ObjectId tagId = TagQuery.saveTag(tag);
        if (tagId == null) {
            LOGGER.error("Failed to save tag");
            return null;
        }
        tagModel.setTagId(tagId.toString());
        return tagModel;
    }

    public boolean verifyTagExists(String id) {
        ObjectId taId = new ObjectId(id);
        return TagQuery.verifyTagExists(taId);
    }

    public TagModel updateTag(String id, TagModel tagModel) {
        ObjectId tagId = new ObjectId(id);
        Tag tag = new Tag();
        tag.setName(tagModel.getName());
        tag.setIcon(tagModel.getIcon());
        tag.setColor(tagModel.getColor());
        tag = TagQuery.updateTag(tag, tagId);
        if (tag == null) {
            LOGGER.error("Unable to update the tag");
            return null;
        }
        tagModel.setTagId(id);
        return tagModel;
    }

    public Boolean deleteTag(String id) {
        ObjectId tagId = new ObjectId(id);
        return TagQuery.deleteTag(tagId);
    }
}
