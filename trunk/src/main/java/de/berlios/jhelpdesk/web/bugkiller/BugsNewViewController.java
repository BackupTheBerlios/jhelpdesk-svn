package de.berlios.jhelpdesk.web.bugkiller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import de.berlios.jhelpdesk.dao.BugCategoryDAO;
import de.berlios.jhelpdesk.dao.BugDAO;
import de.berlios.jhelpdesk.dao.BugPriorityDAO;
import de.berlios.jhelpdesk.dao.UserDAO;
import de.berlios.jhelpdesk.model.BugStatus;
import de.berlios.jhelpdesk.model.User;
import de.berlios.jhelpdesk.web.commons.PagingParamsEncoder;
import de.berlios.jhelpdesk.web.form.ShowBugsFilterForm;
import de.berlios.jhelpdesk.web.form.ShowBugsNewFilterForm;

public class BugsNewViewController extends SimpleFormController {

	private static Log log = LogFactory.getLog(BugsNewViewController.class);
	private static int PAGE_SIZE = 25;
	private Map<String, Object> refData;
	private SimpleDateFormat dateFormat;
	private ShowBugsNewFilterForm filterForm;

	private BugDAO bugDao;
	private BugCategoryDAO bugCategoryDAO;
	private BugPriorityDAO bugPriorityDAO;
	private UserDAO userDAO;

	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
		log.info("initBinder()->start");
		log.info("w new userId => " + ((User) (req.getSession()).getAttribute("user")).getUserId());
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		log.info(" initBinder()->end");
	}

	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) throws ServletException {
		if (refData == null) {
			refData = new HashMap();
			refData.put("categories", bugCategoryDAO.getAllCategoriesForView());
			refData.put("priorities", bugPriorityDAO.getAllPriorities());
			refData.put("users", userDAO.getAllUser());
			refData.put("bugs", bugDao.getBugsByStatus(BugStatus.NOTIFIED));

			if (filterForm != null) {
				ShowBugsFilterForm ff = new ShowBugsFilterForm();
				ff.setCategories(filterForm.getCategories());
				if (filterForm.getStartDate() != null)
					ff.setStartDate(dateFormat.format(filterForm.getStartDate()));
				if (filterForm.getEndDate() != null)
					ff.setEndDate(dateFormat.format(filterForm.getEndDate()));
				ff.setPriorities(filterForm.getPriorities());
				List<BugStatus> st = new ArrayList<BugStatus>();
				st.add(BugStatus.NOTIFIED);
				ff.setStatuses(st);
				PagingParamsEncoder enc = new PagingParamsEncoder("bugsIterator", "p_id", request, PAGE_SIZE);
				refData.put("bugsListSize", bugDao.countBugsWithFilter(ff));
				refData.put("bugs", bugDao.getBugsWithFilter(ff, PAGE_SIZE, enc.getOffset()));
			} else {
				refData.put("bugs", bugDao.getAllBugs());
			}
		}
		return refData;
	}

	protected void onBind(HttpServletRequest req, Object command) {
		filterForm = (ShowBugsNewFilterForm) command;

		filterForm.setBugCategoryDAO(bugCategoryDAO);
		filterForm.setBugPriorityDAO(bugPriorityDAO);
		filterForm.setUserDAO(userDAO);
		// prepareDate( req );
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setNotifyiersFromRequest(req);
	}

	protected Object formBackingObject(HttpServletRequest req) throws ServletException {
		if (filterForm == null) {
			filterForm = new ShowBugsNewFilterForm();
			filterForm.setBugCategoryDAO(bugCategoryDAO);
			filterForm.setBugPriorityDAO(bugPriorityDAO);
			filterForm.setUserDAO(userDAO);
		}
		// prepareDate(req);
		filterForm.setPrioritiesFromRequest(req);
		filterForm.setCategoriesFromRequest(req);
		filterForm.setNotifyiersFromRequest(req);
		return filterForm;
	}

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Object command,
			BindException errors) throws Exception {
		ModelAndView mav = new ModelAndView("bugsNewList");

		ShowBugsFilterForm ff = new ShowBugsFilterForm();
		if (refData == null) {
			refData = new HashMap();
			refData.put("categories", bugCategoryDAO.getAllCategoriesForView());
			refData.put("priorities", bugPriorityDAO.getAllPriorities());
			refData.put("users", userDAO.getAllUser());
			if (filterForm != null) {
				ff.setCategories(filterForm.getCategories());
				if (filterForm.getStartDate() != null)
					ff.setStartDate(dateFormat.format(filterForm.getStartDate()));
				if (filterForm.getEndDate() != null)
					ff.setEndDate(dateFormat.format(filterForm.getEndDate()));
				ff.setPriorities(filterForm.getPriorities());
				ff.setNotifyiers(filterForm.getNotifyiers());
				List<BugStatus> st = new ArrayList<BugStatus>();
				st.add(BugStatus.NOTIFIED);
				ff.setStatuses(st);
				PagingParamsEncoder enc = new PagingParamsEncoder("bugsIterator", "p_id", req, PAGE_SIZE);
				refData.put("bugsListSize", bugDao.countBugsWithFilter(ff));
				refData.put("bugs", bugDao.getBugsWithFilter(ff, PAGE_SIZE, enc.getOffset()));
			} else {
				refData.put("bugs", bugDao.getAllBugs());
			}
		}
		refData.put("filterForm", command);
		mav.addAllObjects(refData);

		return mav;
	}

	/**
	 * @param bugCategoryDAO The bugCategoryDAO to set.
	 */
	public void setBugCategoryDAO(BugCategoryDAO bugCategoryDAO) {
		this.bugCategoryDAO = bugCategoryDAO;
	}

	/**
	 * @param bugPriorityDAO The bugPriorityDAO to set.
	 */
	public void setBugPriorityDAO(BugPriorityDAO bugPriorityDAO) {
		this.bugPriorityDAO = bugPriorityDAO;
	}

	/**
	 * @param userDAO The userDAO to set.
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @param bugDao The bugDao to set.
	 */
	public void setBugDao(BugDAO bugDao) {
		this.bugDao = bugDao;
	}
}
