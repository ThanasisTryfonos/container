/*******************************************************************************
 * Copyright 2017 University of Stuttgart
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *******************************************************************************/
package org.opentosca.container.api.resource.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourceSupport {
	
	private final List<Link> links = new ArrayList<>();
	
	
	public ResourceSupport() {
		
	}
	
	/**
	 * Returns all {@link Link}s contained in this resource.
	 *
	 * @return
	 */
	@XmlElement(name = "link")
	@XmlElementWrapper(name = "links")
	@XmlJavaTypeAdapter(Link.JaxbAdapter.class)
	@JsonProperty("_links")
	public List<Link> getLinks() {
		return this.links;
	}

	/**
	 * Adds the given link to the resource.
	 *
	 * @param link
	 */
	public void add(final Link link) {
		Objects.requireNonNull(link, "Link must not be null!");
		this.links.add(link);
	}

	/**
	 * Adds all given {@link Link}s to the resource.
	 *
	 * @param links
	 */
	public void add(final Iterable<Link> links) {
		Objects.requireNonNull(links, "Given links must not be null!");
		for (final Link candidate : links) {
			this.add(candidate);
		}
	}

	/**
	 * Adds all given {@link Link}s to the resource.
	 *
	 * @param links must not be {@literal null}.
	 */
	public void add(final Link... links) {
		Objects.requireNonNull(links, "Given links must not be null!");
		this.add(Arrays.asList(links));
	}

	/**
	 * Returns whether the resource contains {@link Link}s at all.
	 *
	 * @return
	 */
	public boolean hasLinks() {
		return !this.links.isEmpty();
	}

	/**
	 * Returns whether the resource contains a {@link Link} with the given rel.
	 *
	 * @param rel
	 * @return
	 */
	public boolean hasLink(final String rel) {
		return this.getLink(rel) != null;
	}

	/**
	 * Removes all {@link Link}s added to the resource so far.
	 */
	public void removeLinks() {
		this.links.clear();
	}

	/**
	 * Returns the link with the given rel.
	 *
	 * @param rel
	 * @return the link with the given rel or {@literal null} if none found.
	 */
	public Link getLink(final String rel) {
		for (final Link link : this.links) {
			if (link.getRel().equals(rel)) {
				return link;
			}
		}
		return null;
	}

	/**
	 * Returns all {@link Link}s with the given relation type.
	 *
	 * @return the links in a {@link List}
	 */
	public List<Link> getLinks(final String rel) {
		final List<Link> relatedLinks = new ArrayList<>();
		for (final Link link : this.links) {
			if (link.getRel().equals(rel)) {
				relatedLinks.add(link);
			}
		}
		return relatedLinks;
	}

	@Override
	public String toString() {
		return String.format("links: %s", this.links.toString());
	}
}
