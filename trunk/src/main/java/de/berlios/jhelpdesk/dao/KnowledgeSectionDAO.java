/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright: (C) 2006 jHelpdesk Developers Team
 */
package de.berlios.jhelpdesk.dao;

import java.util.List;

import de.berlios.jhelpdesk.model.KnowledgeSection;

public interface KnowledgeSectionDAO {
	public List<KnowledgeSection> getAllSections();
	public KnowledgeSection getById( Long sectionId );
	public void delete( Long sectionId );
	public void moveUp( Long sectionId );
	public void moveDown( Long sectionId );
	public void save( KnowledgeSection section );
	public List<KnowledgeSection> getAllShortSections();
}
