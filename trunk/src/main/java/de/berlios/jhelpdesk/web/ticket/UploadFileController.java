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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Queue;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import de.berlios.jhelpdesk.utils.FileUtils;
import de.berlios.jhelpdesk.web.tools.FileUploadBean;

/**
 *
 * @author jjhop
 */
@Controller
@RequestMapping("/tickets/uploadFile.html")
public class UploadFileController {

    private final static Logger log = LoggerFactory.getLogger(UploadFileController.class);

    private final static String TICKETS_UPLOAD_VIEW = "tickets/upload";

    @Value("${tickets.attachments.tmpdir}")
    private String attachmentsTmpDir;

    @Autowired
    private FileUtils fileUtils;

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
            File targetDir = fileUtils.createTmpDirForTicketstamp(ticketstamp);
            File targetFile = new File(targetDir, file.getOriginalFilename());
            addPathToSession(session, targetDir.getAbsolutePath());

            try {
                BufferedOutputStream buff =
                        new BufferedOutputStream(new FileOutputStream(targetFile));
                buff.write(uploadedFile.getFile().getBytes());
                buff.flush();
                buff.close();
            } catch (Exception e) {
                log.error("Wystąpił problem z przetworzeniem pliku.", e);
                // obsługa wyjątku?
            }
        }
        map.addAttribute("uploaded", Boolean.TRUE);
        return TICKETS_UPLOAD_VIEW;
    }

    private synchronized void addPathToSession(HttpSession session, String path) {
        Queue<String> paths = (Queue<String>) session.getAttribute("paths");
        if (!paths.contains(path)) {
            paths.add(path);
        }
    }
}
