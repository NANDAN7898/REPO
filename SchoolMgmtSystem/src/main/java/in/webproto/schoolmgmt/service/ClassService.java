package in.webproto.schoolmgmt.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import in.webproto.schoolmgmt.db.DBManager;
import in.webproto.schoolmgmt.to.ClassMasterTO;
import in.webproto.schoolmgmt.util.ApplicationConstants;
import in.webproto.schoolmgmt.util.CommonUtil;
import in.webproto.schoolmgmt.vo.ClassDetailVO;

@Service
public class ClassService
{
	private static final Logger	OUT				= LoggerFactory.getLogger(ClassService.class);

	private static final String	GET_ALL			= "ClassMaster.getAll";

	private static final String	INSERT			= "ClassMaster.insert";

	private static final String	DELETE_BY_ID	= "ClassMaster.deleteById";

	private static final String	GET_BY_ID		= "ClassMaster.getById";

	private static final String	UPDATE			= "ClassMaster.update";

	/**
	 * @return
	 */
	public List<ClassDetailVO> getSummary()
	{
		List<ClassDetailVO> classListVO = new ArrayList<>();

		try
		{
			List<ClassMasterTO> classTO = DBManager.getInstance().getResultAsList(GET_ALL, new ClassMasterTO());

			if (classTO != null)
			{
				classTO.forEach(list -> {
					ClassDetailVO classVO = new ClassDetailVO();
					classVO.setClassName(list.getClassName());
					classVO.setMaxStudent(list.getMaxStudent());
					classVO.setNoOfSection(list.getSections().split(",").length);
					classVO.setId(list.getId());
					classListVO.add(classVO);
				});
			}

		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return classListVO;
	}

	/**
	 * @param detailVO
	 * @return
	 */
	public Boolean addNewClass(ClassDetailVO detailVO)
	{
		Integer isAdded = 0;
		try
		{
			ClassMasterTO classTO = new ClassMasterTO();
			BeanUtils.copyProperties(detailVO, classTO);
			classTO.setSections(getSections(detailVO.getNoOfSection()));
			isAdded = DBManager.getInstance().insert(INSERT, classTO);
		}
		catch (Exception e)
		{
			OUT.error(ApplicationConstants.EXCEPTION, e);
		}
		return isAdded > 0 ? true : false;
	}

	/**
	 * @param noOfSection
	 * @return
	 */
	private String getSections(Integer noOfSection)
	{
		Character[] sectionArr = new Character[noOfSection];
		int section = 65;

		for (int i = 0; i < noOfSection; i++)
		{
			sectionArr[i] = (char) section;
			section++;
		}
		return CommonUtil.convertCharArrayToCommaString(sectionArr);
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteClass(int id) throws Exception
	{
		int isDelete = DBManager.getInstance().deleteObjectById(DELETE_BY_ID, id);
		return isDelete > 0 ? true : false;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getById(int id) throws Exception
	{
		JSONObject detailVO = new JSONObject();

		ClassMasterTO detailTO = (ClassMasterTO) DBManager.getInstance().getResultAsObjectById(GET_BY_ID, id);

		detailVO.put("id", detailTO.getId());
		detailVO.put("className", detailTO.getClassName());
		detailVO.put("maxStudent", detailTO.getMaxStudent());
		detailVO.put("noOfSection", detailTO.getSections().split(",").length);

		return detailVO.toString();
	}
	
	/**
	 * @param detailVO
	 * @return
	 * @throws Exception
	 */
	public Boolean updateClass(ClassDetailVO detailVO) throws Exception
	{
		ClassMasterTO detailTO = new ClassMasterTO();
		int isUpdated = 0;
		BeanUtils.copyProperties(detailVO, detailTO);
		detailTO.setSections(getSections(detailVO.getNoOfSection()));
		isUpdated = DBManager.getInstance().update(UPDATE, detailTO);
		return isUpdated > 0 ? true : false;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getSectionByClass(int id) throws Exception
	{
		JSONObject detailVO = new JSONObject();

		ClassMasterTO detailTO = (ClassMasterTO) DBManager.getInstance().getResultAsObjectById("ClassMaster.getSectionByClass", id);

		for (String sections : detailTO.getSections().split(","))
		{
			sections = sections.trim();
			detailVO.put(sections, sections);
		}

		return detailVO.toString();
	}
}
