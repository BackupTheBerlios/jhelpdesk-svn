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
package de.berlios.jhelpdesk.web.bugkiller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.model.AdditionalFile;
import de.berlios.jhelpdesk.model.Bug;
import de.berlios.jhelpdesk.model.BugComment;
import de.berlios.jhelpdesk.model.User;

import javax.activation.MimetypesFileTypeMap;

public class BugViewController implements Controller {
	
	private static Log log = LogFactory.getLog(BugViewController.class);

    @Autowired
	private BugDAO bugDao;
	private String fileRepositoryPath;

	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
		log.info("BugViewController.handleRequest()");

		ModelAndView mav = null;

		if (req.getParameter("bugId") == null)
			throw new NullArgumentException("Argument \"bugId\" nie moze byc null'em.");

		if (req.getParameter("format") != null && req.getParameter("format").equalsIgnoreCase("pdf")) {
			mav = new ModelAndView("one-pdf");
		} else {
			mav = new ModelAndView("bugDetails");
		}

		if (req.getMethod().equalsIgnoreCase("post")) {
			// dorzucamy komentarz do błędu
			BugComment comm = new BugComment();
			comm.setBugId(Long.parseLong(req.getParameter("bugId")));
			comm.setCommentDate(new Date(System.currentTimeMillis()));
			comm.setNotForPlainUser(false);
			comm.setCommentAuthor((User) req.getSession().getAttribute("user"));
			comm.setCommentText(req.getParameter("addComm"));
			bugDao.addComment(comm);
			res.sendRedirect(req.getContextPath() + "/bugDetails.html?bugId=" + req.getParameter("bugId"));
			res.flushBuffer();
		}

		Bug bug = bugDao.getBugById(Long.parseLong(req.getParameter("bugId").toString()));
		// for( Object evt : bug.getEvents() ) {
		// evt = ( HDBugEvent ) evt;
		// log.info( evt );
		// }
		List<AdditionalFile> addFiles = new ArrayList<AdditionalFile>();
		File repDir = new File(new StringBuffer(fileRepositoryPath).append(File.separatorChar).append(bug.getBugId())
				.toString());

		if (repDir.exists() && repDir.isDirectory()) {
			for (File f : repDir.listFiles()) {
				AdditionalFile addFile = new AdditionalFile();
				addFile.setOriginalFileName(f.getName());
				String mimeType = new MimetypesFileTypeMap().getContentType(f.getName());
				addFile.setContentType((mimeType != null) ? mimeType : "application/octet-strem");
				addFile.setFileSize(f.length());
				addFile.setHashedFileName(FileUtils.byteCountToDisplaySize(f.length()));
				addFiles.add(addFile);
			}
		}

		mav.addObject("bug", bug);
		mav.addObject("files", addFiles);
		return mav;
	}

	public void setFileRepositoryPath(String fileRepositoryPath) {
		this.fileRepositoryPath = fileRepositoryPath;
	}
}
