package com.app.gportal.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.util.CollectionUtils;

import com.app.gportal.model.AccountNameMaster;
import com.app.gportal.response.MasterResponseTransactions;

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
				.lastSaved(getDateFromTimeInMillisec(accountNameMaster.getUpdatedAt().toEpochMilli()))
				.build();
	}
	
	public static Double calculateTotalBalance(List<MasterResponseTransactions> transactions) {
		Double totalBalace = null;
		if(!CollectionUtils.isEmpty(transactions)) {
			totalBalace = new Double(0);
			for(MasterResponseTransactions txn : transactions) {
				totalBalace = totalBalace + getValue(txn.getBalance());
			}
		}
		return totalBalace;
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
			return getDateFromTimeInMillisec(latestTimeStamp);
		}
		else {
			return null;
		}
	}
	
	public static String getDateFromTimeInMillisec(long timeInMillisec) {
		DateFormat simple = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
		c.setTimeInMillis(timeInMillisec);
		return simple.format(c.getTime());
	}

}
