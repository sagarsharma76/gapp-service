package com.app.gportal.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1272960155462601526L;
	/**
	 * 
	 */
	private Long id;
	private String name;
	private List<MasterResponseTransactions> transactions;
	private String lastSaved;
	private String currentDate;
	private String totalBalance;
	private String pnlTotal;
	private String profitLossTotal;

}
