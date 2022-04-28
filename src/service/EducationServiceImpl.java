package service;

import dao.EducationDao;
import dao.EducationDaoImpl;
import entity.Education;

import java.util.List;

public class EducationServiceImpl implements EducationService {
    EducationDao educationDao = new EducationDaoImpl();
    @Override
    public List<Education> findAllEducation() {

        return educationDao.selectAllEducation();
    }
}
