package com.example.jobportal.mapper;

import com.example.jobportal.Dto.CandidateProfileDto;
import com.example.jobportal.model.CandidateProfile;

public class CandidateProfileMapper {

    public static CandidateProfileDto toDto(CandidateProfile entity) {

        if (entity == null) return null;

        return new CandidateProfileDto(
                entity.getFullName(),
                entity.getPhone(),
                entity.getLocation(),
                entity.getSkills(),
                entity.getEducation(),
                entity.getExperience(),
                entity.getProfileImage()
        );
    }

    public static CandidateProfile toEntity(CandidateProfileDto dto) {

        if (dto == null) return null;

        CandidateProfile entity = new CandidateProfile();

        entity.setFullName(dto.getFullName());
        entity.setPhone(dto.getPhone());
        entity.setLocation(dto.getLocation());
        entity.setSkills(dto.getSkills());
        entity.setEducation(dto.getEducation());
        entity.setExperience(dto.getExperience());
        entity.setProfileImage(dto.getProfileImage());

        return entity;
    }
}