package com.job.coverletter.model.skill.dao;

import java.util.List;
import java.util.Map;

import com.job.coverletter.model.skill.dto.SkillDto;

import net.sf.json.JSONArray;

public interface SkillDao {
	String NAMESPACE = "com.job.coverletter.Skill.";
	
	// IT역량차트
	public JSONArray selectItSkill();
	
	// 스킬차트
	public JSONArray selectMySkill();
	
}
