package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	
	AccountRepository accountRepository;
	
	
	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}


	@Override
	public Account createAccount(int accountNumber,int amount) throws InsufficientInitialAmountException
	{
		int a,b,c;
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		
		account.setAmount(amount);
		 
		if(accountRepository.save(account))
		{
			return account;
		}
	     
		return null;
		
	}


	@Override
	public Account doWithdrawal(int accountNumber, int amount) throws InsufficientBalanceException, InvalidAccountNumberException {
		// TODO Auto-generated method stub
		
		Account account  = accountRepository.searchAccount(accountNumber);
		
		if(account == null)
		{
			throw new InvalidAccountNumberException();
		}
		else if (account.getAmount() < amount)
		{
			throw new InsufficientBalanceException();
		}
		else
		{
			account.setAmount(account.getAmount() - amount);
			if(accountRepository.update(account)) {
				return account;
			}
		}
		
		return null;
	}


	@Override
	public Account doDeposit(int accountNumber, int amount) throws InvalidAccountNumberException {
		// TODO Auto-generated method stub
		Account account  = accountRepository.searchAccount(accountNumber);
		
		if(account == null)
		{
			throw new InvalidAccountNumberException();
		}
		else
		{
			account.setAmount(account.getAmount() + amount);
			if(accountRepository.update(account)) {
				return account;
			}
		}
		
		return null;
	}

}
