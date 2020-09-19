package com.app.gportal.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.util.CollectionUtils;

import com.app.gportal.model.AccountNameMaster;
import com.app.gportal.response.MasterResponseTransactions;
import com.app.gportal.response.TransactionResponseDTO;

public class CommonUtils {
	
	
	public static Double getValue(Double dbValue) {
		if(dbValue != null) {
			return dbValue;
		}
		return new Double(0);
	}
	
	public static List<MasterResponseTransactions> getTransactionList(List<AccountNameMaster> accountNameMasters) {
		List<MasterResponseTransactions> transactions = new ArrayList<>();
		for (AccountNameMaster accountNameMaster : accountNameMasters) {
			transactions.add(getTransaction(accountNameMaster));
		}
		return transactions;
	}

	public static MasterResponseTransactions getTransaction(
			AccountNameMaster accountNameMaster) {
		Double base = getValue(accountNameMaster.getBaseAmount());
		Double balance = accountNameMaster.getBalance() == null ? base : accountNameMaster.getBalance();
		Double pointPnl = balance - base;
		Double profitLoss = pointPnl * getValue(accountNameMaster.getRate());
		return MasterResponseTransactions.builder()
				.id(accountNameMaster.getId())
				.accountName(accountNameMaster.getName())
				.holderName(accountNameMaster.getAccountHolderMaster().getName())
				.oBalance(getValue(accountNameMaster.getOBalance()))
				.rate(getValue(accountNameMaster.getRate()))
				.base(base)
				.balance(balance)
				.pointPnl(pointPnl)
				.profitLoss(profitLoss)
				.lastSaved(getDateFromTimeInMillisec(accountNameMaster.getUpdatedAt()))
				.build();
	}
	
	public static void calculateTotalBalance(List<MasterResponseTransactions> transactions, TransactionResponseDTO transactionResponseDTO) {
		Double totalBalance = new Double(0);
		Double pnlTotal = new Double(0);
		Double profitLossTotal = new Double(0);
		if(!CollectionUtils.isEmpty(transactions)) {
			for(MasterResponseTransactions txn : transactions) {
				totalBalance = totalBalance + getValue(txn.getBalance());
				pnlTotal = pnlTotal + getValue(txn.getPointPnl());
				profitLossTotal = profitLossTotal + getValue(txn.getProfitLoss());
			}
		}
		transactionResponseDTO.setTotalBalance(new BigDecimal(totalBalance).toPlainString());
		transactionResponseDTO.setPnlTotal(new BigDecimal(pnlTotal).toPlainString());
		transactionResponseDTO.setProfitLossTotal(new BigDecimal(profitLossTotal).toPlainString());
	}
	
	public static AccountNameMaster updateAccountNameMaster(AccountNameMaster accountNameMaster, Double balance) {
		balance = getValue(balance);
		accountNameMaster.setBalance(balance);
		Double base = getValue(accountNameMaster.getBaseAmount());
		accountNameMaster.setPointPnl(balance - base);
		accountNameMaster.setProfitLoss(accountNameMaster.getPointPnl() * getValue(accountNameMaster.getRate()));
		accountNameMaster.setUpdatedAt(Instant.now(Clock.systemUTC()));
		return accountNameMaster;
	}
	
	public static String getLastSavedDate(List<AccountNameMaster> transactions) {
		long latestTimeStamp = 0l;
		if(!CollectionUtils.isEmpty(transactions)) {
			for(AccountNameMaster txn : transactions) {
				if(latestTimeStamp < txn.getUpdatedAt().toEpochMilli()) {
					latestTimeStamp = txn.getUpdatedAt().toEpochMilli();
				}
			}
			return getDateFromTimeInMillisec(Instant.ofEpochMilli(latestTimeStamp));
		}
		else {
			return null;
		}
	}
	
	public static String getDateFromTimeInMillisec(Instant updateAt) {
		ZonedDateTime zdt = Instant.ofEpochMilli(updateAt.toEpochMilli()).atZone(ZoneId.of("Asia/Kolkata"));
		return zdt.format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss"));
	}

}
