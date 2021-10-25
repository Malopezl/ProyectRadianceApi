/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

import gt.com.api.radiance.entities.Tag;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author malopez
 */
public class TagPage  extends Page {

    private List<Tag> tags;

    public TagPage() {
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TagPage{" + "tags=" + tags + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.tags);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TagPage other = (TagPage) obj;
        return Objects.equals(this.tags, other.tags);
    }

}
