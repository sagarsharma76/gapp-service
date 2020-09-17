package com.app.gportal.service;

import java.util.List;

import com.app.gportal.response.StatusResponseDTO;

public interface IStatusService {

	public List<StatusResponseDTO> getAllStatus();
}
