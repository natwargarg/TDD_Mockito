package com.capgemini.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
public class AccountTest {

	AccountService accountService;
	
	@Mock
	AccountRepository accountRepository;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		accountService = new AccountServiceImpl(accountRepository);
	}

	/*
	 * create account
	 * 1.when the amount is less than 500 then system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialAmountException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(102);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(102, 5000));
	}
	
	/*
	 * Cash withdrawal 
	 * 3.when the account balance is less than withdrawal amount than then system should throw exception
	 * 4.when the invalid accountId is passed then system should throw exception
	 * 5.when valid accountNumber and withdrawal amount is passed then withdrawal should be successfully
	 */
	
	@Test(expected = com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenTheInvalidAccountNumberIsPassedThenSystemShouldThrowException() throws InvalidAccountNumberException, InsufficientBalanceException {
		when(accountRepository.searchAccount(103)).thenReturn(null);
		accountService.doWithdrawal(103, 1000);
		
	}
	
	@Test(expected = com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenTheAccountBalanceIsLessThanWithdrawalAmountThenSystemShouldThrowException() throws InsufficientBalanceException, InvalidAccountNumberException {
		
		Account account =new Account();
		account.setAccountNumber(104);
		account.setAmount(5000);
		when(accountRepository.searchAccount(104)).thenReturn(account);
		
		accountService.doWithdrawal(104, 10000);

	}
	
	@Test
	public void whenValidAccountNumberAndWithdrawalAmountIsPassedThenWithdrawalShouldBeSuccessfully() throws InsufficientBalanceException, InvalidAccountNumberException {
		
		Account account =new Account();
		account.setAccountNumber(105);
		account.setAmount(15000);
		when(accountRepository.update(account)).thenReturn(true);
		when(accountRepository.searchAccount(105)).thenReturn(account);

		assertEquals(account, accountService.doWithdrawal(105, 10000));
	}
	
	
	/*
	 * Cash deposit 
	 * 6.when the invalid accountId is passed then system should throw exception
	 * 7.when valid accountNumber and deposit amount is passed then deposit should be successfully
	 */
	
	@Test(expected = com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenTheInvalidAccountNumberIsPassedForDepositThenSystemShouldThrowException() throws InvalidAccountNumberException {
		when(accountRepository.searchAccount(106)).thenReturn(null);
		accountService.doDeposit(106, 1000);
		
	}
	
	@Test
	public void whenValidAccountNumberAndDepositAmountIsPassedForDepositThenDepositShouldBeSuccessfully() throws InvalidAccountNumberException {
		
		Account account = new Account();
		account.setAccountNumber(107);
		account.setAmount(15000);
		when(accountRepository.update(account)).thenReturn(true);
		when(accountRepository.searchAccount(107)).thenReturn(account);

		assertEquals(account, accountService.doDeposit(107, 5000));
	}
	
}
