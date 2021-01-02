package in.webproto.schoolmgmt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import in.webproto.schoolmgmt.db.DBManager;
import in.webproto.schoolmgmt.to.TeacherMasterTO;
import in.webproto.schoolmgmt.util.CommonUtil;
import in.webproto.schoolmgmt.vo.TeacherDetailVO;

@Service
public class TeacherService
{
	private static final String	GET_ALL	= "TeacherMaster.getAll";

	private static final String	INSERT	= "TeacherMaster.insert";

	/**
	 * @return
	 * @throws Exception
	 */
	public List<TeacherDetailVO> getAll() throws Exception
	{
		List<TeacherDetailVO> voList = new ArrayList<>();
		List<TeacherMasterTO> teacherList = DBManager.getInstance().getResultList(GET_ALL, new TeacherMasterTO());
		teacherList.forEach(to -> {
			TeacherDetailVO detailVO = new TeacherDetailVO();
			BeanUtils.copyProperties(to, detailVO);
			detailVO.setDob(CommonUtil.getDisplayDateFormat(to.getDob()));
			detailVO.setJoiningDate(CommonUtil.getDisplayDateFormat(to.getJoiningDate()));
			voList.add(detailVO);
		});
		return voList;
	}

	/**
	 * @param detailVO
	 * @return
	 * @throws Exception
	 */
	public Integer addNewTeacher(TeacherDetailVO detailVO) throws Exception
	{
		TeacherMasterTO teacherTO = new TeacherMasterTO();
		BeanUtils.copyProperties(detailVO, teacherTO);
		teacherTO.setDob(CommonUtil.convertIntoSQLDate(detailVO.getDob()));
		teacherTO.setJoiningDate(CommonUtil.getCurrentSQLDate());

		DBManager.getInstance().insert(INSERT, teacherTO);
		return teacherTO.getId();
	}

}
