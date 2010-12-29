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
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import de.berlios.jhelpdesk.web.tools.FileUploadBean;

/**
 *
 * @author jjhop
 */
@Controller
@RequestMapping("/tickets/uploadFile.html")
public class UploadFileController {

    private static final String TICKETS_UPLOAD_VIEW = "tickets/upload";

    @RequestMapping(method = RequestMethod.GET)
    protected String prepareForm(ModelMap map) {
        map.addAttribute("fileBean", new FileUploadBean());
        return TICKETS_UPLOAD_VIEW;
    }

    // Wrzuca wszystkie załączniki do katalogu tymczasowego, będą tam odnalezione
    // jeśli użytkownik zdecyduje się zapisać ticket, jeśli kliknie ANULUJ to zostaną
    // usunięte, jeśli jednak gość nie zapisze ticketu i nie kliknie ANULUJ to zostaną
    // usunięte przez mechanizm sprzątający
    @RequestMapping(method = RequestMethod.POST)
    protected String processSubmit(@ModelAttribute("fileBean") FileUploadBean uploadedFile,
                                   @RequestParam("ticketstamp") String ticketstamp,
                                   ModelMap map, HttpSession session) {

        MultipartFile file = uploadedFile.getFile();

        if (file != null) {
            System.out.println("file.getOriginalFilename() => " + file.getOriginalFilename());
            System.out.println("file.getContentType() => " + file.getContentType());
            System.out.println("file.getSize() => " + file.getSize());
            System.out.println("ticketstamp => " + ticketstamp);

            addTicketstampToSession(session, ticketstamp);

            String path = session.getServletContext().getRealPath("./ticket_attachements/");
            File f = new File(new File(path, ticketstamp), file.getOriginalFilename());
            System.out.println("path => " + path);
            System.out.println("abs => " + f.getAbsolutePath());
        }
        map.addAttribute("uploaded", Boolean.TRUE);
        return TICKETS_UPLOAD_VIEW;
    }

    private void addTicketstampToSession(HttpSession session, String ticketStamp) {
        Map<String, Boolean> stampsList = (Map<String, Boolean>) session.getAttribute("stampsList");

        stampsList.put(ticketStamp, Boolean.TRUE);
        session.setAttribute("stampsList", stampsList);
    }
}
