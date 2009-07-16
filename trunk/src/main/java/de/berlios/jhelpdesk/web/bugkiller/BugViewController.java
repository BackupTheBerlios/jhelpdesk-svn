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

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;
import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.model.AdditionalFile;
import de.berlios.jhelpdesk.model.Bug;
import de.berlios.jhelpdesk.model.BugComment;
import de.berlios.jhelpdesk.model.BugPriority;
import de.berlios.jhelpdesk.model.BugStatus;
import de.berlios.jhelpdesk.model.User;
import org.springframework.ui.ModelMap;

@Controller
@SessionAttributes("user")
public class BugViewController {

    @Autowired
    private BugDAO bugDao;
    
    @Autowired
    private BugCategoryDAO bugCategoryDao;

    private final String repositoryPath = "c:\\helpdesk\\files"; //TODO: wypad z tym stąd

    @RequestMapping(value = "/bugDetails.html", method = RequestMethod.POST)
    public String processAddComment(
        @RequestParam("bugId") Long bugId,
        @RequestParam("addComm") String addComm,
        @ModelAttribute("user") User user) {
        BugComment comm = new BugComment();
        comm.setBugId(bugId);
        comm.setCommentDate(new Date(System.currentTimeMillis()));
        comm.setNotForPlainUser(false);
        comm.setCommentAuthor(user);
        comm.setCommentText(addComm);
        bugDao.addComment(comm);
        return "redirect:/bugDetails.html?bugId=" + bugId;
    }

    // TODO: zamienis ModelAndView na String
    // TODO: uzyć ModelMap
    @RequestMapping(value="/bugDetails.html",method=RequestMethod.GET)
	public String showTicket(
                        @RequestParam("bugId") Long bugId,
                        @RequestParam(value = "format", required = false) String format,
                        ModelMap mav) throws Exception {

        Bug bug = bugDao.getBugById(bugId);

        List<AdditionalFile> addFiles = new ArrayList<AdditionalFile>();
        File repDir = new File(new StringBuffer(repositoryPath).append(File.separatorChar).append(bugId).toString());

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

        mav.addAttribute("bug", bug);
        mav.addAttribute("files", addFiles);
        mav.addAttribute("bugPriorities", BugPriority.values());
        mav.addAttribute("bugStatuses", BugStatus.values());
        mav.addAttribute("bugCategories", bugCategoryDao.getAllCategories());

        return (format != null && format.equalsIgnoreCase("pdf")) ? "one-pdf" : "bugDetails";
    }
}
