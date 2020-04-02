package com.job.coverletter.model.jobcalendar.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.job.coverletter.model.coverletter.dto.CoverLetterDto;
import com.job.coverletter.model.jobcalendar.dto.JobCalendarDto;

@Repository
public class JobCalendarDaoImpl implements JobCalendarDao {
   
   @Autowired  @Qualifier("sqlSessionTemPlate")
   private SqlSessionTemplate sqlSession;

   @Override
   public int boardJobListCount(JobCalendarDto dto) {
      int res = 0;
      try {
         res = sqlSession.selectOne(NAMESPACE + "boardJobListCount", dto); 
      } catch (Exception e) {
         System.out.println("[error] : boardJobListCount");
         e.printStackTrace();
      }
      System.out.println("JobCalendar DaoImpl : res :"+ res);
      return res;
   }

   @Override
   public List<JobCalendarDto> boardJobList(JobCalendarDto dto) {
      List<JobCalendarDto> list = new ArrayList<JobCalendarDto>();
      try {
         list = sqlSession.selectList(NAMESPACE + "boardJobList", dto);
      } catch (Exception e) {
         System.out.println("[error] : boardJobList");
         e.printStackTrace();
      }
      return list;
   }
}