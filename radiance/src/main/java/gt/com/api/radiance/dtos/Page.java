/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gt.com.api.radiance.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;
import org.glassfish.jersey.linking.InjectLinks;

/**
 *
 * @author malopez
 */
public class Page {

    /**
     * The number of the current page. Is always non-negative and less that
     * {@code Page#getTotalPages()}.
     */
    private Long number;

    /**
     * The size of the page.
     */
    private Long size;

    /**
     * The number of total pages.
     */
    private Long totalPages;

    /**
     * The number of elements currently on this page.
     */
    private Long numberOfElements;

    /**
     * The total amount of elements.
     */
    private Long totalElements;

    /**
     * If there is a previous page.
     */
    private boolean isPreviousPageAvailable;

    /**
     * Whether the current page is the first one.
     */
    private boolean isFirstPage;

    /**
     * If there is a next page.
     */
    private boolean isNextPageAvailable;

    /**
     * Whether the current page is the last one.
     */
    private boolean isLastPage;

    @InjectLinks()
    private List<Link> links = new ArrayList();

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Long numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    @JsonIgnore
    public boolean isIsPreviousPageAvailable() {
        return isPreviousPageAvailable;
    }

    public void setIsPreviousPageAvailable(boolean isPreviousPageAvailable) {
        this.isPreviousPageAvailable = isPreviousPageAvailable;
    }

    @JsonIgnore
    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    @JsonIgnore
    public boolean isIsNextPageAvailable() {
        return isNextPageAvailable;
    }

    public void setIsNextPageAvailable(boolean isNextPageAvailable) {
        this.isNextPageAvailable = isNextPageAvailable;
    }

    @JsonIgnore
    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
