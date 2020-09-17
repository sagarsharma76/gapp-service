package com.app.gportal.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MasterResponseTransactions implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7913269791774472650L;
	/**
	 * 
	 */
	private Long id;
	private String accountName;
	private String holderName;
	private Double oBalance;
	private Double rate;
	private Double base;
	private Double balance;
	private Double pointPnl;
	private Double profitLoss;
	private Boolean profit;
	private String lastSaved;
}
