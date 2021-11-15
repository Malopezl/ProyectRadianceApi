/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

import java.util.List;

/**
 *
 * @author malopez
 */
public class TagPage  extends Page {

    private List<TagModel> tags;

    public TagPage() {
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TagPage{" + "tags=" + tags + '}';
    }

}
