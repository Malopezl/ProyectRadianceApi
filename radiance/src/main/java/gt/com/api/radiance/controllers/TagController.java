/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.controllers;

import gt.com.api.radiance.dtos.TagPage;
import gt.com.api.radiance.entities.Tag;
import gt.com.api.radiance.queries.TagQuery;
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

    public TagPage getTagPage(Long size, Long page, String filter) {
        TagPage tags = new TagPage();
        Pattern regexp = Pattern.compile(filter, Pattern.CASE_INSENSITIVE);
        List<Tag> tagList = TagQuery.getTagList(size.intValue(), page.intValue(), regexp);
        if (tagList == null) {
            return null;
        }
        tags.setTags(tagList);
        tags.setIsFirstPage(page == 1);
        Long elements = TagQuery.totalPages();
        Long pages;
        if (elements > size) {
            pages = elements / size;
        } else {
            pages = 1L;
        }
        tags.setIsLastPage(page.longValue() == pages.longValue());
        tags.setNumber(page);
        tags.setNumberOfElements(Long.valueOf(tagList.size()));
        tags.setIsPreviousPageAvailable(page > 1);
        tags.setSize(size);
        tags.setTotalElements(elements);
        tags.setTotalPages(pages);
        return tags;
    }

    public Tag getTag(String id) {
        ObjectId tagId = new ObjectId(id);
        return TagQuery.findTag(tagId);
    }

    public Tag saveTag(Tag tag) {
        tag.setIsDelete(Boolean.FALSE);
        ObjectId tagId = TagQuery.saveTag(tag);
        if (tagId == null) {
            LOGGER.error("Failed to save tag");
            return null;
        }
        tag.setId(tagId);
        return tag;
    }

    public boolean verifyTagExists(String id) {
        ObjectId taId = new ObjectId(id);
        return TagQuery.verifyTagExists(taId);
    }

    public Tag updateTag(String id, Tag tag) {
        ObjectId tagId = new ObjectId(id);
        tag = TagQuery.updateTag(tag, tagId);
        if (tag == null) {
            LOGGER.error("Unable to update the tag");
            return null;
        }
        return tag;
    }

    public Boolean deleteTag(String id) {
        ObjectId tagId = new ObjectId(id);
        return TagQuery.deleteTag(tagId);
    }
}
