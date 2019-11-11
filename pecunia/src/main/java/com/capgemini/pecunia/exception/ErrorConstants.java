package com.capgemini.pecunia.exception;
public class ErrorConstants {

	public static final String TECH_ERROR = "Technical problem occured.";

	public static final String UPDATE_PASSBOOK_ERROR = "Passbook not updated. There is some technical error.";
	
	public static final String DB_CONNECTION_ERROR = "Error in closing database connection.";

	public static final String UPDATE_ACCOUNT_ERROR = "Update unsuccessful.";
	public static final String DELETE_ACCOUNT_ERROR = "Deletion unsuccessful.";
	public static final String ACCOUNT_CREATION_ERROR = "Account not Created.";
	public static final String LOGIN_ERROR = "Either Password or Email is incorrect";
	public static final String DELETE_ADDRESS_ERROR = "Error in deleting address details";
	public static final String DELETE_CUSTOMER_ERROR = "Error in deleting customer details";
	public static final String DELETE_DETAILS_ERROR = "Error in deleting address and customer details";

	public static final String LOAN_ADD_ERROR = "Error in adding Loan Details.";
	public static final String FETCH_ERROR = "Error in fetching account Id";

	public static final String NO_SUCH_ACCOUNT = "Account doesn't exist.";
	public static final String CLOSED_ACCOUNT = "Account already closed";
	public static final String ERROR_VALIDATION = "Error in validating account";
	public static final String ACCOUNT_CLOSED = "Account already closed. Cannot perform any operation";
	public static final String ADD_DETAILS_ERROR = "Addition unsuccessful.";

	public static final String BALANCE_RETRIEVAL_ERROR = "Failed to retrieve balance";
	public static final String BALANCE_UPDATE_ERROR = "Failed to update balance";
	public static final String CHEQUE_INSERTION_ERROR = "Failed to add cheque details";
	public static final String TRANSACTION_INSERTION_ERROR = "Failed to add transaction details";

	public static final String CONNECTION_FAILURE = "Connection problem. Cannot connect to the database";
	public static final String FILE_CLOSING_FAILURE = "Files cannot be closed";
	public static final String NO_LOAN_REQUESTS = "Loan request retrieval is unsuccessful";
	public static final String NO_LOAN_DISBURSAL_DATA = "Loan disbursal data retrieval is unsuccessful";
	public static final String NO_BALANCE_UPDATE = "Balance updation is unsuccessful";
	public static final String NO_LOAN_STATUS_UPDATE = "Loan status updation is unsuccessful";
	public static final String NO_TOTAL_EMI = "Retrieval of total EMI is unsuccessful";
	public static final String NO_UNIQUE_IDS = "No unique IDs has been fetched";

	public static final String INVALID_ACCOUNT_EXCEPTION = "Invalid Account Number";
	public static final String INVALID_BANK_EXCEPTION = "Bank doesn't exist. ";
	public static final String INVALID_CHEQUE_EXCEPTION = "Invalid cheque";
	public static final String EXCEPTION_DURING_TRANSACTION = "Error occured while transaction";
	public static final String INSUFFICIENT_BALANCE_EXCEPTION = "Insufficient balance";
	public static final String CHEQUE_BOUNCE_EXCEPTION = "Cheque bounce due to insufficient balance";
	public static final String TRANSACTION_AMOUNT_ERROR = "The transaction could not take place";
	public static final String AMOUNT_EXCEEDS_EXCEPTION = "Maximum amount is 100000";
	public static final String AMOUNT_LESS_EXCEPTION = "Minimum amount is 100";
	public static final String FETCH_ACCOUNT_DETAILS= "Error in fetching account details";

}
