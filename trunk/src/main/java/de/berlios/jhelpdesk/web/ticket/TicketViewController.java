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
package de.berlios.jhelpdesk.web.ticket;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.berlios.jhelpdesk.dao.TicketCategoryDAO;
import de.berlios.jhelpdesk.dao.TicketDAO;
import de.berlios.jhelpdesk.model.AdditionalFile;
import de.berlios.jhelpdesk.model.Ticket;
import de.berlios.jhelpdesk.model.TicketComment;
import de.berlios.jhelpdesk.model.TicketPriority;
import de.berlios.jhelpdesk.model.TicketStatus;
import de.berlios.jhelpdesk.model.User;

/**
 *
 * @author jjhop
 */
@Controller
@SessionAttributes("user")
public class TicketViewController {

    @Autowired
    private TicketDAO ticketDao;
    
    @Autowired
    private TicketCategoryDAO ticketCategoryDao;

    /**
     * Ścieżka do katalogu z plikami dołączanymi do zgłoszeń.
     * TODO: ścieżka powinna być konfigurowana w jednym miejscu i nie w kodzie
     */
    private final String repositoryPath = "c:\\helpdesk\\files";

    /**
     *
     *
     * @param ticketId
     * @param addComm
     * @param user
     * @return
     */
    @RequestMapping(value = "/ticketDetails.html", method = RequestMethod.POST)
    public String processAddComment(
                  @RequestParam("ticketId") Long ticketId,
                  @RequestParam("addComm") String addComm,
                  @ModelAttribute("user") User user) throws Exception {

        TicketComment comm = new TicketComment();
        comm.setTicketId(ticketId);
        comm.setCommentDate(new Date(System.currentTimeMillis()));
        comm.setNotForPlainUser(false); // TODO: to musi pochodzi z formularza
        comm.setCommentAuthor(user);
        comm.setCommentText(addComm);
        ticketDao.addComment(comm);
        return "redirect:/ticketDetails.html?ticketId=" + ticketId;
    }

    /**
     *
     * 
     * @param ticketId
     * @param format
     * @param mav
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/ticketDetails.html", method = RequestMethod.GET)
    public String showTicket(
                  @RequestParam("ticketId") Long ticketId,
                  @RequestParam(value = "format", required = false) String format,
                  ModelMap mav) throws Exception {

        Ticket ticket = ticketDao.getTicketById(ticketId);

        List<AdditionalFile> addFiles = new ArrayList<AdditionalFile>();
        File repDir = new File(new StringBuffer(repositoryPath).append(File.separatorChar).append(ticketId).toString());

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

        mav.addAttribute("ticket", ticket);
        mav.addAttribute("files", addFiles);
        mav.addAttribute("ticketPriorities", TicketPriority.values());
        mav.addAttribute("ticketStatuses", TicketStatus.values());
        mav.addAttribute("ticketCategories", ticketCategoryDao.getAllCategories());

        return (format != null && format.equalsIgnoreCase("pdf")) ? "one-pdf" : "ticketDetails";
    }
}
